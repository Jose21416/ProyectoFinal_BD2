package Lógica;

import Datos.DCursos;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class LCursos {
    
    // Asumo que LConexion contiene el método getConnection() para abrir y cerrar la conexión en cada método.
    private final LConexion conexion; 
    private String sSQL = "";

    // Logger para manejar errores de forma centralizada
    private static final Logger logger = Logger.getLogger(LCursos.class.getName());

    public LCursos() {
        this.conexion = new LConexion();
    }

    // =========================================================================
    // CONSULTA - Obtener ID por Nombre (CRUD de Asignaturas)
    // =========================================================================
    
    /**
     * Obtiene el ID del curso usando su nombre. Este método es CRUCIAL para
     * establecer la clave foránea (FK) id_curso en la tabla Asignaturas.
     * * @param nombreCurso El nombre del curso seleccionado en el JComboBox.
     * @return El ID del curso (entero), o -1 si no se encuentra o hay error.
     */
    public int obtenerIdCursoPorNombre(String nombreCurso) {
        // Consulta: Obtener el ID donde el nombre coincida
        sSQL = "SELECT id_curso FROM curso WHERE nombre = ?";
        int idCurso = -1; // Valor predeterminado de error

        try (Connection cn = conexion.getConnection();
             PreparedStatement pst = cn.prepareStatement(sSQL)) {
                 
            pst.setString(1, nombreCurso);
            
            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    idCurso = rs.getInt("id_curso"); // Obtiene el valor
                }
            }

        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error al buscar ID de curso por nombre: " + nombreCurso, e);
            // No se muestra JOptionPane para este error, ya que el logger es suficiente 
            // y el formulario maneja el -1 devuelto.
        }
        return idCurso;
    }
    
    // =========================================================================
    // LISTADO - Para JTable/JComboBox (Todos los cursos)
    // =========================================================================

    /**
     * Muestra todos los cursos en un DefaultTableModel (usualmente para JTable o para cargar JComboBox).
     * @return DefaultTableModel con ID y Nombre del curso.
     */
    public DefaultTableModel mostrarTodos() {
        DefaultTableModel modelo;
        String[] titulos = {"ID", "Nombre"};
        modelo = new DefaultTableModel(null, titulos);
        
        // Consulta simple para listado
        String sql = "SELECT id_curso, nombre FROM curso ORDER BY nombre ASC"; 

        try (Connection cn = conexion.getConnection();
             Statement st = cn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
                
            while (rs.next()) {
                String[] registro = new String[2];
                registro[0] = rs.getString("id_curso");
                registro[1] = rs.getString("nombre");
                modelo.addRow(registro);
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error SQL al cargar todos los cursos", e);
            JOptionPane.showMessageDialog(null, "Error SQL al cargar cursos: " + e.getMessage());
            return modelo; // Se devuelve el modelo vacío
        }
        return modelo;
    }
    
    // =========================================================================
    // LISTADO - Cursos Asignados al Estudiante
    // =========================================================================

    public List<DCursos> listarCursosPorCorreo(String correo) {
        List<DCursos> lista = new ArrayList<>();
        sSQL = """
            SELECT c.id_curso, c.nombre, c.descripcion
            FROM curso c
            INNER JOIN estudiante_curso ec ON ec.id_curso = c.id_curso
            INNER JOIN usuario u ON u.id_usuario = ec.id_usuario
            WHERE u.correo = ?
        """;

        try (Connection cn = conexion.getConnection();
             PreparedStatement pst = cn.prepareStatement(sSQL)) {
                 
            pst.setString(1, correo);
            try (ResultSet rs = pst.executeQuery()) {
                while (rs.next()) {
                    DCursos curso = new DCursos();
                    curso.setId_curso(rs.getInt("id_curso"));
                    curso.setNombre(rs.getString("nombre"));
                    curso.setDescripcion(rs.getString("descripcion"));
                    lista.add(curso);
                }
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error al listar cursos por correo", e);
            JOptionPane.showMessageDialog(null, "Error al listar cursos: " + e.getMessage());
        }
        return lista;
    }

    // =========================================================================
    // MANTENIMIENTO (CRUD Básico)
    // =========================================================================

    // ========== INSERTAR ==========
    public boolean insertarCurso(DCursos curso) {
        sSQL = "INSERT INTO curso (nombre, descripcion) VALUES (?, ?)";
        try (Connection cn = conexion.getConnection();
             PreparedStatement pst = cn.prepareStatement(sSQL)) {
                 
            pst.setString(1, curso.getNombre());
            pst.setString(2, curso.getDescripcion());
            pst.executeUpdate();
            JOptionPane.showMessageDialog(null, "Curso agregado correctamente.");
            return true;
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error al insertar curso", e);
            JOptionPane.showMessageDialog(null, "Error al agregar curso: " + e.getMessage());
            return false;
        }
    }

    // ========== ACTUALIZAR ==========
    public boolean actualizarCurso(DCursos curso) {
        sSQL = "UPDATE curso SET nombre = ?, descripcion = ? WHERE id_curso = ?";
        try (Connection cn = conexion.getConnection();
             PreparedStatement pst = cn.prepareStatement(sSQL)) {
                 
            pst.setString(1, curso.getNombre());
            pst.setString(2, curso.getDescripcion());
            pst.setInt(3, curso.getId_curso());
            pst.executeUpdate();
            JOptionPane.showMessageDialog(null, "Curso actualizado correctamente.");
            return true;
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error al actualizar curso", e);
            JOptionPane.showMessageDialog(null, "Error al actualizar curso: " + e.getMessage());
            return false;
        }
    }

    // ========== ELIMINAR ==========
    public boolean eliminarCurso(int idCurso) {
        sSQL = "DELETE FROM curso WHERE id_curso = ?";
        try (Connection cn = conexion.getConnection();
             PreparedStatement pst = cn.prepareStatement(sSQL)) {
                 
            pst.setInt(1, idCurso);
            pst.executeUpdate();
            JOptionPane.showMessageDialog(null, "Curso eliminado correctamente.");
            return true;
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error al eliminar curso", e);
            JOptionPane.showMessageDialog(null, "Error al eliminar curso: " + e.getMessage());
            return false;
        }
    }
}