package Lógica;

import Datos.DAsignatura;
import java.sql.Connection;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LAsignatura {
   
    private final LConexion conexion = new LConexion();
    private static final Logger logger = Logger.getLogger(LAsignatura.class.getName());
    
    private final String[] titulos = {"ID Asig.", "ID Curso", "Curso", "Asignatura", "Créditos"};
    
    public boolean insertar(DAsignatura datos) {
        String sql = "INSERT INTO asignatura (id_curso, nombre, creditos) VALUES (?, ?, ?)";
    
        try (Connection cn = conexion.getConnection(); 
            PreparedStatement pst = cn.prepareStatement(sql)) { 
        
            pst.setInt(1, datos.getId_curso());
            pst.setString(2, datos.getNombre());
            pst.setInt(3, datos.getCreditos());
        
            pst.executeUpdate();
            return true;
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error al insertar asignatura (PreparedStatement)", e);
            JOptionPane.showMessageDialog(null, "Error al insertar asignatura: " + e.getMessage(), "Error de BD", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

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
                
                registro[0] = rs.getString("id_asignatura");
                registro[1] = rs.getString("id_curso");
                registro[2] = rs.getString("nombre_curso");
                registro[3] = rs.getString("nombre_asignatura"); 
                registro[4] = rs.getString("creditos");
                modelo.addRow(registro);
            }
            
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error al cargar asignaturas desde la función SQL.", e);
            
            JOptionPane.showMessageDialog(null, "Error al cargar asignaturas. Verifique si la función SQL 'ObtenerTodasLasAsignaturas()' existe y devuelve los campos correctos. Detalle: " + e.getMessage(), "Error Crítico", JOptionPane.ERROR_MESSAGE);
            
            return modelo;
        }
        return modelo;
    }

    public boolean editar(DAsignatura datos) {
        String sql = "UPDATE asignatura SET id_curso = ?, nombre = ?, creditos = ? WHERE id_asignatura = ?"; 
        
        try (Connection cn = conexion.getConnection(); 
             PreparedStatement pst = cn.prepareStatement(sql)) {
                
            pst.setInt(1, datos.getId_curso());
            pst.setString(2, datos.getNombre());
            pst.setInt(3, datos.getCreditos());
            pst.setInt(4, datos.getId_asignatura());
            
            pst.executeUpdate();
            return true;
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error al editar asignatura (PreparedStatement)", e);
            JOptionPane.showMessageDialog(null, "Error al editar asignatura: " + e.getMessage(), "Error de BD", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

    public boolean eliminar(DAsignatura datos) {
        String sql = "DELETE FROM asignatura WHERE id_asignatura = ?"; 
        
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
    
    public DefaultTableModel buscarAsignaturas(String textoBuscar) {
        DefaultTableModel modelo = new DefaultTableModel(null, titulos);
        
        String sql = "SELECT * FROM ObtenerTodasLasAsignaturas() " +
                     "WHERE nombre_asignatura ILIKE ? OR nombre_curso ILIKE ?";
        
        String terminoBusqueda = "%" + textoBuscar + "%";
        
        try (Connection cn = conexion.getConnection();
             PreparedStatement pst = cn.prepareStatement(sql)) {
            
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
            
            registro[0] = rs.getString("id_asignatura");
            registro[1] = rs.getString("id_curso");
            registro[2] = rs.getString("nombre_curso");
            registro[3] = rs.getString("nombre_asignatura"); 
            registro[4] = rs.getString("creditos");
            modelo.addRow(registro);
        }
        return modelo;
    }
    
    private String sSQL;

    public List<DAsignatura> listarAsignaturas() {

        List<DAsignatura> lista = new ArrayList<>();
        sSQL = "SELECT id_asignatura, nombre FROM asignatura ORDER BY nombre ASC";

        LConexion conexion = new LConexion();

        try (Connection cn = conexion.getConnection();
             PreparedStatement pst = cn.prepareStatement(sSQL)) {

            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                DAsignatura a = new DAsignatura();
                a.setId_asignatura(rs.getInt("id_asignatura"));
                a.setNombre(rs.getString("nombre"));
                lista.add(a);
            }

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al listar asignaturas: " + ex.getMessage());
        }

        return lista;
    }
    
    public int obtenerIdPorNombre(String nombre) {
        int id = -1;

        String sql = "SELECT id_asignatura FROM asignatura WHERE nombre = ?";

        try (Connection cn = conexion.getConnection(); PreparedStatement pst = cn.prepareStatement(sql)) {

            pst.setString(1, nombre);
            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                id = rs.getInt("id_asignatura");
            }

        } catch (SQLException e) {
            System.out.println("Error en obtenerIdPorNombre: " + e.getMessage());
        }

        return id;
    }
}