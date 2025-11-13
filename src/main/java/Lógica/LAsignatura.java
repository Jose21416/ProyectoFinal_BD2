package Lógica;

import Datos.DAsignatura;
import java.sql.Connection;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LAsignatura {
    
    private final LConexion conexion = new LConexion();
    private static final Logger logger = Logger.getLogger(LAsignatura.class.getName());
    
    // Títulos de la tabla (usados en mostrarTodas y buscarAsignaturas)
    private final String[] titulos = {"ID Asig.", "ID Curso", "Curso", "Asignatura", "Créditos"};
    
    // =========================================================================
    // 1. CREAR (CREATE)
    // =========================================================================
    /**
     * Inserta una nueva asignatura usando el stored procedure CrearAsignatura.
     * @param datos
     * @return 
     */
    // 1. CREAR (CREATE)
    public boolean insertar(DAsignatura datos) {
    // Usamos PreparedStatement en lugar de CallableStatement
        String sql = "INSERT INTO asignatura (id_curso, nombre, creditos) VALUES (?, ?, ?)";
    
    // Usamos PreparedStatement y no CallableStatement
        try (Connection cn = conexion.getConnection(); 
            PreparedStatement pst = cn.prepareStatement(sql)) { 
        
            pst.setInt(1, datos.getId_curso());
            pst.setString(2, datos.getNombre());
            pst.setInt(3, datos.getCreditos());
        
            pst.executeUpdate(); // Usamos executeUpdate para INSERT
            return true;
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error al insertar asignatura (PreparedStatement)", e);
            JOptionPane.showMessageDialog(null, "Error al insertar asignatura: " + e.getMessage(), "Error de BD", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

    // =========================================================================
    // 2. LEER (READ) - Muestra todas las asignaturas
    // =========================================================================
    /**
     * Muestra todas las asignaturas en un DefaultTableModel.
     * * NOTA IMPORTANTE: Esta función requiere que la función SQL 
     * 'ObtenerTodasLasAsignaturas()' devuelva las columnas exactas: 
     * id_asignatura, id_curso, nombre_curso, nombre_asignatura, creditos.
     * @return 
     */
    public DefaultTableModel mostrarTodas() {
        DefaultTableModel modelo;
        
        String[] titulos = {"ID Asig.", "ID Curso", "Curso", "Asignatura", "Créditos"};
        modelo = new DefaultTableModel(null, titulos);
        
        String sql = "SELECT * FROM ObtenerTodasLasAsignaturas()"; 

        try (Connection cn = conexion.getConnection();
             Statement st = cn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            
            while (rs.next()) {
                String[] registro = new String[5];
                
                // Los nombres deben coincidir con los aliases de la función SQL
                registro[0] = rs.getString("id_asignatura");
                registro[1] = rs.getString("id_curso");
                registro[2] = rs.getString("nombre_curso");
                registro[3] = rs.getString("nombre_asignatura"); 
                registro[4] = rs.getString("creditos");
                modelo.addRow(registro);
            }
            
        } catch (SQLException e) {
            // Este catch es CRÍTICO para el error de transacción abortada
            logger.log(Level.SEVERE, "Error al cargar asignaturas desde la función SQL.", e);
            
            JOptionPane.showMessageDialog(null, "Error al cargar asignaturas. Verifique si la función SQL 'ObtenerTodasLasAsignaturas()' existe y devuelve los campos correctos. Detalle: " + e.getMessage(), "Error Crítico", JOptionPane.ERROR_MESSAGE);
            
            return modelo; // Devuelve el modelo vacío
        }
        return modelo;
    }

    // =========================================================================
    // 3. ACTUALIZAR (UPDATE) - CORREGIDO con PreparedStatement
    // =========================================================================
    /**
     * Actualiza una asignatura usando PreparedStatement.
     * @param datos
     * @return 
     */
    public boolean editar(DAsignatura datos) {
        // Sentencia SQL directa para PostgreSQL
        String sql = "UPDATE asignatura SET id_curso = ?, nombre = ?, creditos = ? WHERE id_asignatura = ?"; 
        
        // Se utiliza PreparedStatement en lugar de CallableStatement
        try (Connection cn = conexion.getConnection(); 
             PreparedStatement pst = cn.prepareStatement(sql)) {
                
            pst.setInt(1, datos.getId_curso());
            pst.setString(2, datos.getNombre());
            pst.setInt(3, datos.getCreditos());
            pst.setInt(4, datos.getId_asignatura()); // ID en el WHERE
            
            pst.executeUpdate();
            return true;
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error al editar asignatura (PreparedStatement)", e);
            JOptionPane.showMessageDialog(null, "Error al editar asignatura: " + e.getMessage(), "Error de BD", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

    // =========================================================================
    // 4. ELIMINAR (DELETE) - CORREGIDO con PreparedStatement
    // =========================================================================
    /**
     * Elimina una asignatura usando PreparedStatement.
     * @param datos
     * @return 
     */
    public boolean eliminar(DAsignatura datos) {
        // Sentencia SQL directa para PostgreSQL
        String sql = "DELETE FROM asignatura WHERE id_asignatura = ?"; 
        
        // Se utiliza PreparedStatement en lugar de CallableStatement
        try (Connection cn = conexion.getConnection(); 
             PreparedStatement pst = cn.prepareStatement(sql)) {
                
            pst.setInt(1, datos.getId_asignatura());
            
            pst.executeUpdate();
            return true;
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error al eliminar asignatura (PreparedStatement)", e);
            JOptionPane.showMessageDialog(null, "Error al eliminar asignatura: " + e.getMessage(), "Error de BD", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }
    
    // =========================================================================
    // 5. BUSCAR (SEARCH) - NUEVO MÉTODO
    // =========================================================================
    /**
     * Busca asignaturas por nombre de asignatura o nombre de curso.
     * @param textoBuscar El nombre o parte del nombre a buscar.
     * @return DefaultTableModel con los resultados filtrados.
     */
    public DefaultTableModel buscarAsignaturas(String textoBuscar) {
        DefaultTableModel modelo = new DefaultTableModel(null, titulos);
        
        // Consulta SQL que usa la función y luego filtra los resultados con WHERE
        String sql = "SELECT * FROM ObtenerTodasLasAsignaturas() " +
                     "WHERE nombre_asignatura ILIKE ? OR nombre_curso ILIKE ?";
        
        // Usamos ILIKE para búsqueda insensible a mayúsculas/minúsculas (PostgreSQL)
        String terminoBusqueda = "%" + textoBuscar + "%";
        
        try (Connection cn = conexion.getConnection();
             PreparedStatement pst = cn.prepareStatement(sql)) {
            
            // Asignamos el término de búsqueda a los parámetros
            pst.setString(1, terminoBusqueda);
            pst.setString(2, terminoBusqueda);
            
            try (ResultSet rs = pst.executeQuery()) {
                return llenarModeloDesdeResultSet(rs, modelo);
            }
            
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error al buscar asignaturas.", e);
            JOptionPane.showMessageDialog(null, "Error al buscar asignaturas: " + e.getMessage(), "Error de BD", JOptionPane.ERROR_MESSAGE);
            return modelo; 
        }
    }

    private DefaultTableModel llenarModeloDesdeResultSet(ResultSet rs, DefaultTableModel modelo) throws SQLException {
        while (rs.next()) {
            String[] registro = new String[5];
            
            // Los nombres deben coincidir con los aliases de la función SQL
            registro[0] = rs.getString("id_asignatura");
            registro[1] = rs.getString("id_curso");
            registro[2] = rs.getString("nombre_curso");
            registro[3] = rs.getString("nombre_asignatura"); 
            registro[4] = rs.getString("creditos");
            modelo.addRow(registro);
        }
        return modelo;
    }
}