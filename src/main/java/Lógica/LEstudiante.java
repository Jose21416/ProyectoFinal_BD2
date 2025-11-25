package Lógica;

import Datos.DUsuarios;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;


public class LEstudiante {
    LConexion ocon = new LConexion();
    Connection cn = ocon.getConnection();
    DUsuarios us = new DUsuarios();
    ResultSet rs;
    
    public List<DUsuarios> listarSoloEstudiantes() {
        List<DUsuarios> lista = new ArrayList<>();

        String sql = "SELECT u.id_usuario, u.nombre, u.id_curso, c.nombre as nombre_curso "
                + "FROM usuario u "
                + "LEFT JOIN curso c ON u.id_curso = c.id_curso "
                + "WHERE u.tipo_usuario = 'estudiante' "
                + "ORDER BY u.nombre";

        try (Connection cn = ocon.getConnection(); PreparedStatement pst = cn.prepareStatement(sql); ResultSet rs = pst.executeQuery()) {

            while (rs.next()) {
                DUsuarios est = new DUsuarios();
                est.setId_usuario(rs.getInt("id_usuario"));
                est.setNombre(rs.getString("nombre"));

                // Guardar el id_curso
                int idCurso = rs.getInt("id_curso");
                est.setId_curso(idCurso);

                lista.add(est);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al listar estudiantes: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
        return lista;
    }
    
    public DUsuarios mostrarInformacionEstudiante(DUsuarios est){
        PreparedStatement ps = null;
        String sql = "SELECT nombre, correo, contrasena, telefono FROM usuario WHERE usuario = ? AND tipo_usuario = 'estudiante'";
        try {
            ps = cn.prepareStatement(sql);
            ps.setString(1, est.getUsuario());
            rs = ps.executeQuery();
            
            if(rs.next()){
                us.setNombre(rs.getString("nombre"));
                us.setCorreo(rs.getString("correo"));
                us.setContrasena(rs.getString("contrasena"));
                us.setTelefono(rs.getString("telefono")); 
                return us;
            }else{
                JOptionPane.showMessageDialog(null, "Error al mostrar los datos", "Aviso del sistema", 
                        JOptionPane.INFORMATION_MESSAGE);
                return null;
            }
            
        } catch (Exception e) {
           JOptionPane.showMessageDialog(null, "Error en: " +e.getMessage());
           return null;
        }
    }
    
    public void actualizarCamposEstudiante(DUsuarios est){
        PreparedStatement ps = null;
        String sql = "UPDATE usuario SET correo = ?, telefono = ? WHERE usuario = ? AND tipo_usuario = 'estudiante'";
        try {
            ps = cn.prepareStatement(sql);
            ps.setString(1, est.getCorreo());
            ps.setString(2, est.getTelefono());
            ps.setString(3, est.getUsuario());
            
            int registro = ps.executeUpdate();
            
            if (registro > 0) {
                JOptionPane.showMessageDialog(null, "Datos del usuario actualizados correctamente", "Aviso del sistema",
                        JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null, "No se pudo actualizar","Aviso del sistema",
                        JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception e) {
            JOptionPane.showConfirmDialog(null, "Error en: " +e.getMessage());
        }
    }
    
    public boolean asignarCursoAEstudiante(int id_usuario, int id_curso) {
        String sql = "UPDATE usuario SET id_curso = ? WHERE id_usuario = ?";

        try {
            PreparedStatement ps = cn.prepareStatement(sql);
            ps.setInt(1, id_curso);
            ps.setInt(2, id_usuario);

            int filasAfectadas = ps.executeUpdate();

            if (filasAfectadas > 0) {
                JOptionPane.showMessageDialog(null, "Curso asignado correctamente",
                        "Éxito", JOptionPane.INFORMATION_MESSAGE);
                return true;
            } else {
                JOptionPane.showMessageDialog(null, "No se pudo asignar el curso. Verifica que el estudiante existe.",
                        "Advertencia", JOptionPane.WARNING_MESSAGE);
                return false;
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error asignando curso: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }
}
