package Lógica;

import Datos.DAsignatura;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

public class LCursosEstudiantes {

    private final LConexion conexion = new LConexion();

    public List<DAsignatura> listarAsignaturasPorEstudiante(int id_usuario) {
        List<DAsignatura> lista = new ArrayList<>();

        String sql = """
            SELECT a.id_asignatura, a.nombre, a.creditos
            FROM asignatura a
            INNER JOIN usuario u ON a.id_curso = u.id_curso
            WHERE u.id_usuario = ? AND u.tipo_usuario = 'estudiante'
            ORDER BY a.nombre
        """;

        try (Connection cn = conexion.getConnection(); PreparedStatement ps = cn.prepareStatement(sql)) {

            ps.setInt(1, id_usuario);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                DAsignatura asignatura = new DAsignatura();
                asignatura.setId_asignatura(rs.getInt("id_asignatura"));
                asignatura.setNombre(rs.getString("nombre"));
                asignatura.setCreditos(rs.getInt("creditos"));
                lista.add(asignatura);
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null,
                    "Error al listar asignaturas: " + e.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }

        return lista;
    }

    public String obtenerNombreCursoEstudiante(int id_usuario) {
        String sql = """
            SELECT c.nombre
            FROM curso c
            INNER JOIN usuario u ON c.id_curso = u.id_curso
            WHERE u.id_usuario = ? AND u.tipo_usuario = 'estudiante'
        """;

        try (Connection cn = conexion.getConnection(); PreparedStatement ps = cn.prepareStatement(sql)) {

            ps.setInt(1, id_usuario);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return rs.getString("nombre");
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null,
                    "Error al obtener curso: " + e.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }

        return null;
    }

    public String obtenerNombreEstudiante(int id_usuario) {
        String sql = "SELECT nombre FROM usuario WHERE id_usuario = ? AND tipo_usuario = 'estudiante'";

        try (Connection cn = conexion.getConnection(); PreparedStatement ps = cn.prepareStatement(sql)) {

            ps.setInt(1, id_usuario);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return rs.getString("nombre");
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null,
                    "Error al obtener nombre del estudiante: " + e.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }

        return null;
    }
}

