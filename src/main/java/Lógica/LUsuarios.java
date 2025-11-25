package Lógica;

import Datos.DUsuarios;
import java.sql.*;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class LUsuarios {

    LConexion con = new LConexion();
    Connection cn = con.getConnection();
    ResultSet rs;

    public boolean validarLogin(DUsuarios usu) {
        String sql = """
        SELECT usuario, contrasena, estado, tipo_usuario
        FROM usuario
        WHERE TRIM(LOWER(usuario)) = TRIM(LOWER(?))
    """;

        try (PreparedStatement ps = cn.prepareStatement(sql)) {

            ps.setString(1, usu.getUsuario());
            rs = ps.executeQuery();

            if (rs.next()) {

                String hashBD = rs.getString("contrasena"); 
                String hashIngresado = LHash.hashPassword(usu.getContrasena()); 

                // 2. Comparar hash ingresado con hash de la BD
                if (!hashBD.equals(hashIngresado)) {
                    JOptionPane.showMessageDialog(null, "Contraseña incorrecta");
                    return false;
                }

                // 3. Validar estado
                String estado = rs.getString("estado");
                if (!estado.equalsIgnoreCase("activo")) {
                    JOptionPane.showMessageDialog(null, "El usuario está inactivo");
                    return false;
                }

                // 4. Validar tipo usuario
                String tipo = rs.getString("tipo_usuario");
                if (!tipo.equalsIgnoreCase(usu.getTipoUsuario().name().toLowerCase())) {
                    JOptionPane.showMessageDialog(null, "Tipo de usuario incorrecto");
                    return false;
                }

                DUsuarios.usuarioLogueado = rs.getString("usuario");
                return true;
            } else {
                JOptionPane.showMessageDialog(null, "Usuario no encontrado");
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error en validar login: " + e.getMessage());
        }

        return false;
    }


    public boolean crearUsuario(String usuario, String nombre, String correo, String contrasena, String telefono, String tipoUsuario) {
        String sql = "CALL CrearUsuario(?, ?, ?, ?, ?, ?)";

        try (CallableStatement cs = cn.prepareCall(sql)) {

            String hash = LHash.hashPassword(contrasena);

            cs.setString(1, usuario);
            cs.setString(2, nombre);
            cs.setString(3, correo);
            cs.setString(4, hash);       
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

    public String eliminarUsuarios(DUsuarios u) {
        try (CallableStatement cst = cn.prepareCall("call EliminarUsuario(?)")) {
            cst.setInt(1, u.getId_usuario());
            cst.executeUpdate();
            return "Usuario eliminado correctamente";
        } catch (SQLException ex) {
            return "Error al eliminar usuario: " + ex.getMessage();
        }
    }

    public int obtenerIdUsuario(String usuario) {
        int id = -1;
        String sql = "SELECT id_usuario FROM usuario WHERE LOWER(usuario) = LOWER(?)";
        try (PreparedStatement pst = cn.prepareStatement(sql)) {
            pst.setString(1, usuario);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                id = rs.getInt("id_usuario");
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al obtener ID del usuario: " + e.getMessage());
        }
        return id;
    }
    
}
