package Datos;

import Lógica.LConexion;
import java.sql.*;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class DCursos {

    private int idCurso;
    private String nombre;
    private String descripcion;

    public DCursos() {}

    public DCursos(int idCurso, String nombre, String descripcion) {
        this.idCurso = idCurso;
        this.nombre = nombre;
        this.descripcion = descripcion;
    }

    // Getters y Setters
    public int getIdCurso() { return idCurso; }
    public void setIdCurso(int idCurso) { this.idCurso = idCurso; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    // Mostrar cursos por estudiante
    public DefaultTableModel mostrarCursosPorCorreo(String correoEstudiante) {
        DefaultTableModel modelo;
        String[] titulos = {"ID", "Curso", "Descripción"};
        String[] registro = new String[3];
        modelo = new DefaultTableModel(null, titulos);
        LConexion conexion = new LConexion();

        String sql = """
            SELECT c.id_curso, c.nombre, c.descripcion
            FROM curso c
            JOIN usuario u ON u.id_curso = c.id_curso
            WHERE u.correo = ?
        """;

        try (Connection cn = conexion.getConnection();
             PreparedStatement pst = cn.prepareStatement(sql)) {

            pst.setString(1, correoEstudiante);
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                registro[0] = rs.getString("id_curso");
                registro[1] = rs.getString("nombre");
                registro[2] = rs.getString("descripcion");
                modelo.addRow(registro);
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al mostrar cursos: " + e.getMessage());
        }
        return modelo;
    }

    // CRUD básico
    public boolean insertar() {
        LConexion conexion = new LConexion();
        String sql = "INSERT INTO curso (nombre, descripcion) VALUES (?, ?)";
        try (Connection cn = conexion.getConnection();
             PreparedStatement pst = cn.prepareStatement(sql)) {

            pst.setString(1, nombre);
            pst.setString(2, descripcion);
            pst.executeUpdate();
            return true;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al insertar curso: " + e.getMessage());
            return false;
        }
    }

    public boolean editar() {
        LConexion conexion = new LConexion();
        String sql = "UPDATE curso SET nombre=?, descripcion=? WHERE id_curso=?";
        try (Connection cn = conexion.getConnection();
             PreparedStatement pst = cn.prepareStatement(sql)) {

            pst.setString(1, nombre);
            pst.setString(2, descripcion);
            pst.setInt(3, idCurso);
            pst.executeUpdate();
            return true;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al editar curso: " + e.getMessage());
            return false;
        }
    }

    public boolean eliminar() {
        LConexion conexion = new LConexion();
        String sql = "DELETE FROM curso WHERE id_curso=?";
        try (Connection cn = conexion.getConnection();
             PreparedStatement pst = cn.prepareStatement(sql)) {

            pst.setInt(1, idCurso);
            pst.executeUpdate();
            return true;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al eliminar curso: " + e.getMessage());
            return false;
        }
    }
}

