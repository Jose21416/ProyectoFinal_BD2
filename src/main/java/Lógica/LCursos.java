package Lógica;

import Datos.DCursos;
import java.sql.*;
import java.util.*;
import javax.swing.JOptionPane;

public class LCursos {

    private Connection cn;

    public LCursos() {
        // Usamos la conexión ya creada en tu clase LConexion
        LConexion conexion = new LConexion();
        cn = conexion.getConnection();
    }

    // ========== LISTAR CURSOS POR CORREO ==========
    public List<DCursos> listarCursosPorCorreo(String correo) {
        List<DCursos> lista = new ArrayList<>();
        String sql = """
            SELECT c.id_curso, c.nombre, c.descripcion
            FROM curso c
            INNER JOIN estudiante_curso ec ON ec.id_curso = c.id_curso
            INNER JOIN usuario u ON u.id_usuario = ec.id_usuario
            WHERE u.correo = ?
        """;

        try (PreparedStatement pst = cn.prepareStatement(sql)) {
            pst.setString(1, correo);
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                DCursos curso = new DCursos();
                curso.setId_curso(rs.getInt("id_curso"));
                curso.setNombre(rs.getString("nombre"));
                curso.setDescripcion(rs.getString("descripcion"));
                lista.add(curso);
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al listar cursos: " + e.getMessage());
        }
        return lista;
    }

    // ========== INSERTAR ==========
    public boolean insertarCurso(DCursos curso) {
        String sql = "INSERT INTO curso (nombre, descripcion) VALUES (?, ?)";
        try (PreparedStatement pst = cn.prepareStatement(sql)) {
            pst.setString(1, curso.getNombre());
            pst.setString(2, curso.getDescripcion());
            pst.executeUpdate();
            JOptionPane.showMessageDialog(null, "Curso agregado correctamente.");
            return true;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al agregar curso: " + e.getMessage());
            return false;
        }
    }

    // ========== ACTUALIZAR ==========
    public boolean actualizarCurso(DCursos curso) {
        String sql = "UPDATE curso SET nombre = ?, descripcion = ? WHERE id_curso = ?";
        try (PreparedStatement pst = cn.prepareStatement(sql)) {
            pst.setString(1, curso.getNombre());
            pst.setString(2, curso.getDescripcion());
            pst.setInt(3, curso.getId_curso());
            pst.executeUpdate();
            JOptionPane.showMessageDialog(null, "Curso actualizado correctamente.");
            return true;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al actualizar curso: " + e.getMessage());
            return false;
        }
    }

    // ========== ELIMINAR ==========
    public boolean eliminarCurso(int idCurso) {
        String sql = "DELETE FROM curso WHERE id_curso = ?";
        try (PreparedStatement pst = cn.prepareStatement(sql)) {
            pst.setInt(1, idCurso);
            pst.executeUpdate();
            JOptionPane.showMessageDialog(null, "Curso eliminado correctamente.");
            return true;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al eliminar curso: " + e.getMessage());
            return false;
        }
    }
}
