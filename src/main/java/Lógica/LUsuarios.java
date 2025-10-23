package Lógica;

import Datos.DUsuarios;
import java.sql.*;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class LUsuarios {

    LConexión con = new LConexión();
    Connection cn = con.getConnection();
    ResultSet rs;

    // ============================================
    // VALIDAR LOGIN
    // ============================================
    public boolean validarLogin(DUsuarios usu) {
        String sql = """
            SELECT usuario, contrasena, estado, tipo_usuario
            FROM usuario
            WHERE TRIM(LOWER(usuario)) = TRIM(LOWER(?))
            AND TRIM(contrasena) = TRIM(?)
        """;

        try (PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setString(1, usu.getUsuario());
            ps.setString(2, usu.getContrasena());
            rs = ps.executeQuery();

            if (rs.next()) {
                String estado = rs.getString("estado");
                String tipo = rs.getString("tipo_usuario");

                if (!estado.equalsIgnoreCase("activo")) {
                    JOptionPane.showMessageDialog(null, "El usuario está inactivo");
                    return false;
                }

                if (!tipo.equalsIgnoreCase(usu.getTipoUsuario().name().toLowerCase())) {
                    JOptionPane.showMessageDialog(null, "Tipo de usuario incorrecto");
                    return false;
                }

                DUsuarios.usuarioLogueado = rs.getString("usuario");
                return true;
            } else {
                JOptionPane.showMessageDialog(null, "Usuario o contraseña incorrectos");
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error en validar login: " + e.getMessage());
        }

        return false;
    }

    // ============================================
    // CREAR USUARIO
    // ============================================
    public boolean crearUsuario(String usuario, String nombre, String correo, String contrasena, String telefono, String tipoUsuario) {
        String sql = "CALL CrearUsuario(?, ?, ?, ?, ?, ?)";

        try (CallableStatement cs = cn.prepareCall(sql)) {
            cs.setString(1, usuario);
            cs.setString(2, nombre);
            cs.setString(3, correo);
            cs.setString(4, contrasena);
            cs.setString(5, telefono);
            cs.setString(6, tipoUsuario.toLowerCase());

            cs.execute();
            JOptionPane.showMessageDialog(null, "Usuario creado correctamente");
            return true;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al crear usuario: " + e.getMessage());
        }

        return false;
    }

    // ============================================
    // MOSTRAR USUARIOS
    // ============================================
    public DefaultTableModel mostrarUsuarios(DUsuarios dts) {
        String[] titulos = {"ID", "Nombre", "Telefono", "Correo", "Usuario", "Contraseña", "Tipo de Usuario", "Estado"};
        DefaultTableModel modelo = new DefaultTableModel(null, titulos);

        String sqlSinFiltro = "SELECT * FROM ObtenerTodosLosUsuarios()";
        String sqlConFiltro = """
            SELECT id_usuario, nombre, telefono, correo, usuario, contrasena, tipo_usuario, estado
            FROM usuario
            WHERE TRIM(LOWER(nombre)) ILIKE TRIM(LOWER(?))
            ORDER BY nombre
        """;

        try {
            PreparedStatement pst;

            if (dts.getUsuario() == null || dts.getUsuario().trim().isEmpty()) {
                pst = cn.prepareStatement(sqlSinFiltro);
            } else {
                pst = cn.prepareStatement(sqlConFiltro);
                pst.setString(1, "%" + dts.getUsuario() + "%");
            }

            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                String[] registro = new String[8];
                registro[0] = String.valueOf(rs.getInt("id_usuario"));
                registro[1] = rs.getString("nombre");
                registro[2] = rs.getString("telefono");
                registro[3] = rs.getString("correo");
                registro[4] = rs.getString("usuario");
                registro[5] = rs.getString("contrasena");
                registro[6] = rs.getString("tipo_usuario");
                registro[7] = rs.getString("estado");
                modelo.addRow(registro);
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al mostrar usuarios: " + e.getMessage());
        }

        return modelo;
    }

    // ============================================
    // EDITAR USUARIO
    // ============================================
    public String editarUsuarios(DUsuarios u) {
        String msg = null;
        try (CallableStatement cst = cn.prepareCall("call ActualizarUsuario(?,?,?,?,?,?,?,?)")) {
            cst.setInt(1, u.getId_usuario());
            cst.setString(2, u.getUsuario());
            cst.setString(3, u.getNombre());
            cst.setString(4, u.getCorreo());
            cst.setString(5, u.getTelefono());
            cst.setString(6, u.getTipoUsuario().name().toLowerCase());
            cst.setString(7, u.getEstado().name().toLowerCase());
            cst.setString(8, u.getContrasena());

            cst.executeUpdate();
            msg = "Usuario actualizado correctamente";
        } catch (SQLException ex) {
            msg = "Error al actualizar: " + ex.getMessage();
        }
        return msg;
    }

    // ============================================
    // ELIMINAR USUARIO
    // ============================================
    public String eliminarUsuarios(DUsuarios u) {
        try (CallableStatement cst = cn.prepareCall("call EliminarUsuario(?)")) {
            cst.setInt(1, u.getId_usuario());
            cst.executeUpdate();
            return "Usuario eliminado correctamente";
        } catch (SQLException ex) {
            return "Error al eliminar usuario: " + ex.getMessage();
        }
    }
}
