
package Lógica;

import Datos.DCursos;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LCursosEstudiantes {
    private final LConexion conexion = new LConexion();

    // 🔹 Listar los cursos que pertenecen a un estudiante específico
    public List<DCursos> listarCursosPorEstudiante(int idUsuario) {
        List<DCursos> lista = new ArrayList<>();

        String sql = """
            SELECT c.id_curso, c.nombre, c.descripcion
            FROM curso c
            INNER JOIN estudiante_curso ec ON c.id_curso = ec.id_curso
            WHERE ec.id_usuario = ?
        """;

        try (Connection cn = conexion.getConnection();
             PreparedStatement ps = cn.prepareStatement(sql)) {

            ps.setInt(1, idUsuario);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                DCursos curso = new DCursos();
                curso.setId_curso(rs.getInt("id_curso"));
                curso.setNombre(rs.getString("nombre"));
                curso.setDescripcion(rs.getString("descripcion"));
                lista.add(curso);
            }

        } catch (SQLException e) {
            System.err.println("❌ Error al listar cursos por estudiante: " + e.getMessage());
        }
        return lista;
    }
}

