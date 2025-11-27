package Lógica;

import Datos.DAsignatura;
import Datos.DUsuarios;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class LGestionNotas {

    private final LConexion conexion = new LConexion();
    private static final Logger logger = Logger.getLogger(LGestionNotas.class.getName());

    public List<DUsuarios> listarEstudiantesActivos() {
        List<DUsuarios> lista = new ArrayList<>();
        String sql = "SELECT id_usuario, nombre FROM usuario WHERE tipo_usuario = 'estudiante' AND estado = 'activo' ORDER BY nombre";

        try (Connection cn = conexion.getConnection();
             PreparedStatement pst = cn.prepareStatement(sql);
             ResultSet rs = pst.executeQuery()) {

            while (rs.next()) {
                DUsuarios u = new DUsuarios();
                u.setId_usuario(rs.getInt("id_usuario"));
                u.setNombre(rs.getString("nombre"));
                lista.add(u);
            }

        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error al listar estudiantes", e);
            JOptionPane.showMessageDialog(null, "Error al cargar estudiantes: " + e.getMessage());
        }
        return lista;
    }

    public List<DAsignatura> listarAsignaturasPorAlumno(int idAlumno) {
        List<DAsignatura> lista = new ArrayList<>();
        // Relacionamos al alumno con su curso, y el curso con las asignaturas
        String sql = "SELECT a.id_asignatura, a.nombre " +
                     "FROM asignatura a " +
                     "INNER JOIN usuario u ON a.id_curso = u.id_curso " +
                     "WHERE u.id_usuario = ? " +
                     "ORDER BY a.nombre";

        try (Connection cn = conexion.getConnection();
             PreparedStatement pst = cn.prepareStatement(sql)) {

            pst.setInt(1, idAlumno);
            
            try (ResultSet rs = pst.executeQuery()) {
                while (rs.next()) {
                    DAsignatura a = new DAsignatura();
                    a.setId_asignatura(rs.getInt("id_asignatura"));
                    a.setNombre(rs.getString("nombre"));
                    lista.add(a);
                }
            }

        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error al listar asignaturas por alumno", e);
            JOptionPane.showMessageDialog(null, "Error al cargar asignaturas: " + e.getMessage());
        }
        return lista;
    }

    public DefaultTableModel mostrarNotasEstudiante(int idAlumno, int idAsignatura) {
        String[] titulos = {"ID Eval", "Evaluación", "Porcentaje", "Nota"};
        DefaultTableModel modelo = new DefaultTableModel(null, titulos) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; 
            }
        };

        String sql = "SELECT e.id_evaluacion, e.nombre, e.porcentaje, COALESCE(n.nota, 0) as nota " +
                     "FROM evaluacion e " +
                     "LEFT JOIN nota_evaluacion n ON e.id_evaluacion = n.id_evaluacion AND n.id_usuario = ? " +
                     "WHERE e.id_asignatura = ? " +
                     "ORDER BY e.id_evaluacion";

        try (Connection cn = conexion.getConnection();
             PreparedStatement pst = cn.prepareStatement(sql)) {

            pst.setInt(1, idAlumno);
            pst.setInt(2, idAsignatura);

            try (ResultSet rs = pst.executeQuery()) {
                while (rs.next()) {
                    Object[] fila = new Object[4];
                    fila[0] = rs.getInt("id_evaluacion");
                    fila[1] = rs.getString("nombre");
                    fila[2] = rs.getBigDecimal("porcentaje") + "%";
                    fila[3] = rs.getBigDecimal("nota");
                    modelo.addRow(fila);
                }
            }

        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error al mostrar notas", e);
            JOptionPane.showMessageDialog(null, "Error al cargar notas: " + e.getMessage());
        }
        return modelo;
    }


    public boolean actualizarNota(int idUsuario, int idEvaluacion, double nota) {

        String sql = "INSERT INTO nota_evaluacion (id_usuario, id_evaluacion, nota) " +
                     "VALUES (?, ?, ?) " +
                     "ON CONFLICT (id_usuario, id_evaluacion) " +
                     "DO UPDATE SET nota = EXCLUDED.nota";

        try (Connection cn = conexion.getConnection();
             PreparedStatement pst = cn.prepareStatement(sql)) {

            pst.setInt(1, idUsuario);
            pst.setInt(2, idEvaluacion);
            pst.setDouble(3, nota);

            int filas = pst.executeUpdate();
            return filas > 0;

        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error al actualizar nota", e);
            JOptionPane.showMessageDialog(null, "Error al guardar la nota: " + e.getMessage());
            return false;
        }
    }
}