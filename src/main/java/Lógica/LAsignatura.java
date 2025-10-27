package Lógica;

import Datos.DAsignatura;
import java.sql.Connection;
import java.sql.CallableStatement;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LAsignatura {
    
    // Instancia de la clase de conexión para reutilizarla
    private final LConexion conexion = new LConexion();
    private String sql = "";
    
    // Logger para registrar errores en el servidor/consola
    private static final Logger logger = Logger.getLogger(LAsignatura.class.getName());
    
    // 1. CREAR (CREATE)
    public boolean insertar(DAsignatura datos) {
        // Llama al Procedure: CALL CrearAsignatura(p_id_curso, p_nombre, p_creditos)
        sql = "{call CrearAsignatura(?, ?, ?)}"; 
        
        // Usamos try-with-resources para asegurar el cierre de Connection y CallableStatement
        try (Connection cn = conexion.getConnection(); 
             CallableStatement cs = cn.prepareCall(sql)) {
            
            cs.setInt(1, datos.getId_curso());
            cs.setString(2, datos.getNombre());
            cs.setInt(3, datos.getCreditos());
            
            cs.execute();
            return true;
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error al insertar asignatura", e);
            JOptionPane.showMessageDialog(null, "Error al insertar asignatura: " + e.getMessage());
            return false;
        }
    }

    // 2. LEER (READ) - Muestra todas las asignaturas
    public DefaultTableModel mostrarTodas() {
        DefaultTableModel modelo;
        
        // Títulos: 0: ID Asig., 1: ID Curso, 2: Nombre Curso, 3: Nombre Asignatura, 4: Créditos
        String[] titulos = {"ID Asig.", "ID Curso", "Curso", "Asignatura", "Créditos"};
        modelo = new DefaultTableModel(null, titulos);
        
        // Uso de SELECT * FROM Funcion() para PostgreSQL (lo más robusto)
        sql = "SELECT * FROM ObtenerTodasLasAsignaturas()"; 

        // Usamos try-with-resources con Connection, Statement y ResultSet
        try (Connection cn = conexion.getConnection();
             Statement st = cn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            
            while (rs.next()) {
                String[] registro = new String[5];
                
                // Los nombres de columna deben coincidir exactamente con los ALIAS de la función SQL
                registro[0] = rs.getString("id_asignatura");
                registro[1] = rs.getString("id_curso");
                registro[2] = rs.getString("nombre_curso");
                registro[3] = rs.getString("nombre_asignatura"); 
                registro[4] = rs.getString("creditos");
                modelo.addRow(registro);
            }
            
        } catch (SQLException e) {
            // Se registra el error en el log
            logger.log(Level.SEVERE, "Error al mostrar asignaturas (usando SELECT * FROM): ", e);
            
            // Se muestra el error detallado al usuario
            JOptionPane.showMessageDialog(null, "Error de base de datos. Verifique la función SQL 'ObtenerTodasLasAsignaturas()'. Detalle: " + e.getMessage(), "Error Crítico", JOptionPane.ERROR_MESSAGE);
            
            // Devolvemos el modelo vacío para evitar el error 'Cannot set a null TableModel' en FrmAsignaturas.
            return modelo; 
        }
        return modelo;
    }

    // 3. ACTUALIZAR (UPDATE)
    public boolean editar(DAsignatura datos) {
        // Llama al Procedure: CALL ActualizarAsignatura(p_id_asignatura, p_id_curso, p_nombre, p_creditos)
        sql = "{call ActualizarAsignatura(?, ?, ?, ?)}"; 
        
        try (Connection cn = conexion.getConnection(); 
             CallableStatement cs = cn.prepareCall(sql)) {
             
            cs.setInt(1, datos.getId_asignatura());
            cs.setInt(2, datos.getId_curso());
            cs.setString(3, datos.getNombre());
            cs.setInt(4, datos.getCreditos());
            
            cs.execute();
            return true;
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error al editar asignatura", e);
            JOptionPane.showMessageDialog(null, "Error al editar asignatura: " + e.getMessage());
            return false;
        }
    }

    // 4. ELIMINAR (DELETE)
    public boolean eliminar(DAsignatura datos) {
        // Llama al Procedure: CALL EliminarAsignatura(p_id_asignatura)
        sql = "{call EliminarAsignatura(?)}"; 
        
        try (Connection cn = conexion.getConnection(); 
             CallableStatement cs = cn.prepareCall(sql)) {
             
            cs.setInt(1, datos.getId_asignatura());
            cs.execute();
            return true;
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error al eliminar asignatura", e);
            JOptionPane.showMessageDialog(null, "Error al eliminar asignatura: " + e.getMessage());
            return false;
        }
    }
}