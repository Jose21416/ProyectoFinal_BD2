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
    
    private final LConexion conexion; 
    private String sSQL = "";
    private static final Logger logger = Logger.getLogger(LCursos.class.getName());

    public LCursos() {
        this.conexion = new LConexion();
    }

    public int obtenerIdCursoPorNombre(String nombreCurso) {
        String sql = "SELECT id_curso FROM curso WHERE nombre = ? LIMIT 1";

        try (Connection cn = conexion.getConnection(); PreparedStatement pst = cn.prepareStatement(sql)) {

            pst.setString(1, nombreCurso);
            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                return rs.getInt("id_curso");
            }

        } catch (SQLException e) {
            System.out.println("Error al obtener ID del curso: " + e.getMessage());
        }

        return -1; 
    }
    
    public DefaultTableModel mostrarTodos() {
        DefaultTableModel modelo;
        String[] titulos = {"ID", "Nombre", "Descripción"};
        modelo = new DefaultTableModel(null, titulos);

        String sql = "SELECT id_curso, nombre, descripcion FROM curso ORDER BY nombre ASC";

        try (Connection cn = conexion.getConnection(); Statement st = cn.createStatement(); ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                String[] registro = new String[3];
                registro[0] = rs.getString("id_curso");
                registro[1] = rs.getString("nombre");
                registro[2] = rs.getString("descripcion");
                modelo.addRow(registro);
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error SQL al cargar cursos: " + e.getMessage());
        }

        return modelo;
    }

    
 
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
    
    public List<DCursos> listarCursos() {
        List<DCursos> lista = new ArrayList<>();
        sSQL = "SELECT id_curso, nombre FROM curso ORDER BY nombre"; 

        try (Connection cn = conexion.getConnection(); PreparedStatement pst = cn.prepareStatement(sSQL); ResultSet rs = pst.executeQuery()) {

            while (rs.next()) {
                DCursos curso = new DCursos();
                curso.setId_curso(rs.getInt("id_curso"));
                curso.setNombre(rs.getString("nombre"));
                lista.add(curso);
            }

        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error al listar cursos", e);
        }

        return lista;
    }
}