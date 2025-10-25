
package Lógica;

import Datos.DUsuarios;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;

public class LRecuperación {
    LConexion ocon = new LConexion();
    Connection cn = ocon.getConnection();
    DUsuarios us = new DUsuarios();
    ResultSet rs;
    
    public Integer buscarIdUsuarioPorCorreo(String correo) {
        String sql = "SELECT id_usuario FROM usuario WHERE correo = ? AND estado = 'activo'";
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        try {
            ps = cn.prepareStatement(sql);
            ps.setString(1, correo);
            rs = ps.executeQuery();
            
            if (rs.next()) {
                return rs.getInt("id_usuario");
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al buscar usuario: " + e.getMessage());
        } finally {
            try {
                if (rs != null) rs.close();
                if (ps != null) ps.close();
            } catch (SQLException e) {
                System.err.println("Error al cerrar recursos: " + e.getMessage());
            }
        }
        return null;
    }
    
    public boolean guardarCodigoVerificacion(int id_usuario, String codigo) {
        String sql = "INSERT INTO codigo_verificacion (id_usuario, codigo, metodo_envio, fecha_expiracion) " +
                 "VALUES (?, ?, 'correo', NOW() + INTERVAL '1 minute')";
        
        try (PreparedStatement ps = cn.prepareStatement(sql)) { 
            ps.setInt(1, id_usuario);
            ps.setString(2, codigo);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al guardar el código: " + e.getMessage());
            return false;
        }
    }
    
    public boolean verificarYUsarCodigo(int id_usuario, String codigo) {
        String selectSql = "SELECT id_codigo FROM codigo_verificacion WHERE id_usuario = ? AND codigo = ? AND usado = 0 AND fecha_expiracion > NOW()";
        
        try (PreparedStatement psSelect = cn.prepareStatement(selectSql)) {
            psSelect.setInt(1, id_usuario);
            psSelect.setString(2, codigo);
            ResultSet rs = psSelect.executeQuery();

            if (rs.next()) {
                int id_codigo = rs.getInt("id_codigo");
                String updateSql = "UPDATE codigo_verificacion SET usado = 1 WHERE id_codigo = ?";
                try (PreparedStatement psUpdate = cn.prepareStatement(updateSql)) {
                    psUpdate.setInt(1, id_codigo);
                    psUpdate.executeUpdate();
                }
                return true;
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al verificar el código: " + e.getMessage());
        }
        return false;
    }
    
    public boolean actualizarContraseña(int id_usuario, String nuevaContraseña) {
        String sql = "UPDATE usuario SET contrasena = ? WHERE id_usuario = ?";
        
        try (PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setString(1, nuevaContraseña);
            ps.setInt(2, id_usuario);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al actualizar la contraseña: " + e.getMessage());
            return false;
        }
    }
    
    
}
