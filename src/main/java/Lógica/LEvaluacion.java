
package Lógica;

import Datos.DEvaluacion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

public class LEvaluacion {

    private String sSQL;

    public boolean crearEvaluacion(DEvaluacion e) {

        sSQL = "INSERT INTO evaluacion (id_asignatura, nombre, porcentaje) VALUES (?, ?, ?)";

        LConexion conexion = new LConexion();

        try (Connection cn = conexion.getConnection();
             PreparedStatement pst = cn.prepareStatement(sSQL)) {

            pst.setInt(1, e.getId_asignatura());
            pst.setString(2, e.getNombre());
            pst.setDouble(3, e.getPorcentaje());

            pst.executeUpdate();
            return true;

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al crear evaluación: " + ex.getMessage());
            return false;
        }
    }


    public boolean actualizarEvaluacion(DEvaluacion e) {

        sSQL = "UPDATE evaluacion SET nombre = ?, porcentaje = ? WHERE id_evaluacion = ?";

        LConexion conexion = new LConexion();

        try (Connection cn = conexion.getConnection();
             PreparedStatement pst = cn.prepareStatement(sSQL)) {

            pst.setString(1, e.getNombre());
            pst.setDouble(2, e.getPorcentaje());
            pst.setInt(3, e.getId_evaluacion());

            pst.executeUpdate();
            return true;

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al actualizar evaluación: " + ex.getMessage());
            return false;
        }
    }


    public boolean eliminarEvaluacion(int id_evaluacion) {

        sSQL = "DELETE FROM evaluacion WHERE id_evaluacion = ?";

        LConexion conexion = new LConexion();

        try (Connection cn = conexion.getConnection();
             PreparedStatement pst = cn.prepareStatement(sSQL)) {

            pst.setInt(1, id_evaluacion);
            pst.executeUpdate();

            return true;

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al eliminar evaluación: " + ex.getMessage());
            return false;
        }
    }


    public List<DEvaluacion> listarEvaluacionesPorAsignatura(int id_asignatura) {

        List<DEvaluacion> lista = new ArrayList<>();

        sSQL = "SELECT id_evaluacion, nombre, porcentaje FROM evaluacion WHERE id_asignatura = ? ORDER BY nombre ASC";

        LConexion conexion = new LConexion();

        try (Connection cn = conexion.getConnection();
             PreparedStatement pst = cn.prepareStatement(sSQL)) {

            pst.setInt(1, id_asignatura);
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {

                DEvaluacion e = new DEvaluacion();
                e.setId_evaluacion(rs.getInt("id_evaluacion"));
                e.setNombre(rs.getString("nombre"));
                e.setPorcentaje(rs.getDouble("porcentaje"));
                e.setId_asignatura(id_asignatura);

                lista.add(e);
            }

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al listar evaluaciones: " + ex.getMessage());
        }

        return lista;
    }
    
    public boolean existeEvaluacion(DEvaluacion e) {
        boolean existe = false;

        String sql = "SELECT COUNT(*) FROM evaluacion WHERE LOWER(nombre) = LOWER(?) AND id_asignatura = ?";
        LConexion conexion = new LConexion();

        try (Connection con = conexion.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, e.getNombre().trim());
            ps.setInt(2, e.getId_asignatura());

            ResultSet rs = ps.executeQuery();

            if (rs.next() && rs.getInt(1) > 0) {
                existe = true;
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return existe;
    }
}


