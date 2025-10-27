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
    
    // Declaración sin inicialización para permitir la inicialización en el constructor
    private final LConexion conexion; 
    private String sSQL = "";

    // Logger para manejar errores
    private static final Logger logger = Logger.getLogger(LCursos.class.getName());

    public LCursos() {
        // Inicialización correcta de la variable final
        this.conexion = new LConexion();
        // Nota: NO DEBES GUARDAR LA CONEXIÓN EN UNA VARIABLE DE CLASE (this.cn)
        // La conexión debe obtenerse y cerrarse en cada método para evitar problemas de concurrencia.
    }

    // ========== LISTAR CURSOS POR CORREO ==========
    public List<DCursos> listarCursosPorCorreo(String correo) {
        List<DCursos> lista = new ArrayList<>();
        sSQL = """
            SELECT c.id_curso, c.nombre, c.descripcion
            FROM curso c
            INNER JOIN estudiante_curso ec ON ec.id_curso = c.id_curso
            INNER JOIN usuario u ON u.id_usuario = ec.id_usuario
            WHERE u.correo = ?
        """;

        // Usar try-with-resources para obtener la conexión y asegurar su cierre
        try (Connection cn = conexion.getConnection();
             PreparedStatement pst = cn.prepareStatement(sSQL)) {
                 
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
            logger.log(Level.SEVERE, "Error al listar cursos por correo", e);
            JOptionPane.showMessageDialog(null, "Error al listar cursos: " + e.getMessage());
        }
        return lista;
    }

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

    /**
     * Muestra todos los cursos en un DefaultTableModel para ser cargado en un JTable o JComboBox.
     * @return DefaultTableModel con ID y Nombre del curso.
     */
    public DefaultTableModel mostrarTodos() {
        DefaultTableModel modelo;
        String[] titulos = {"ID", "Nombre"};
        modelo = new DefaultTableModel(null, titulos);
        
        String sql = "SELECT id_curso, nombre FROM Curso ORDER BY nombre ASC"; 

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
            // Se devuelve el modelo vacío en caso de error para evitar NullPointerException en el formulario.
            return modelo; 
        }
        return modelo;
    }
    
    /**
     * Obtiene el ID del curso usando su nombre.
     * @param nombreCurso El nombre del curso a buscar.
     * @return El ID del curso, o -1 si no se encuentra.
     */
    public int obtenerIdCursoPorNombre(String nombreCurso) {
        sSQL = "SELECT id_curso FROM curso WHERE nombre = ?";
        int idCurso = -1;

        try (Connection cn = conexion.getConnection();
             PreparedStatement pst = cn.prepareStatement(sSQL)) {
                 
            pst.setString(1, nombreCurso);
            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                idCurso = rs.getInt("id_curso");
            }
            
            // No es necesario cerrar rs explícitamente ya que el try-with-resources en pst se encarga
            // de cerrar todos los recursos cuando se cierran pst y cn.

        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error al buscar ID de curso por nombre: " + nombreCurso, e);
        }
        return idCurso;
    }
}