package Lógica;

import Datos.DDetalle_evaluacion;
import java.sql.*;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class LDetalle_evaluacion {

    LConexion con = new LConexion();
    Connection cn = con.getConnection();

 
    public boolean insertar(DDetalle_evaluacion dts) {
        String sql = "INSERT INTO detalle_evaluacion (id_nota, nombre_evaluacion, peso, nota) VALUES (?, ?, ?, ?)";
        try (PreparedStatement pst = cn.prepareStatement(sql)) {
            pst.setInt(1, dts.getIdNota());
            pst.setString(2, dts.getNombreEvaluacion());
            pst.setDouble(3, dts.getPeso());
            pst.setDouble(4, dts.getNota());
            pst.executeUpdate();
            return true;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al insertar detalle: " + e.getMessage());
            return false;
        }
    }


    public DefaultTableModel mostrarPorNota(int idNota) {
        DefaultTableModel modelo;
        String[] titulos = {"ID", "Evaluación", "Peso (%)", "Nota", "Contribución"};
        String[] registro = new String[5];

        modelo = new DefaultTableModel(null, titulos);

        String sql = "SELECT id_detalle, nombre_evaluacion, peso, nota, ROUND(nota * peso / 100, 2) AS contribucion "
                   + "FROM detalle_evaluacion WHERE id_nota = ?";

        try (PreparedStatement pst = cn.prepareStatement(sql)) {
            pst.setInt(1, idNota);
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                registro[0] = rs.getString("id_detalle");
                registro[1] = rs.getString("nombre_evaluacion");
                registro[2] = rs.getString("peso");
                registro[3] = rs.getString("nota");
                registro[4] = rs.getString("contribucion");
                modelo.addRow(registro);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al mostrar detalles: " + e.getMessage());
        }

        return modelo;
    }

    public double calcularPromedio(int idNota) {
        double promedio = 0;
        String sql = "SELECT SUM(nota * peso / 100) AS promedio FROM detalle_evaluacion WHERE id_nota = ?";

        try (PreparedStatement pst = cn.prepareStatement(sql)) {
            pst.setInt(1, idNota);
            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                promedio = rs.getDouble("promedio");
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al calcular promedio: " + e.getMessage());
        }

        return Math.round(promedio * 100.0) / 100.0;
    }

    public boolean eliminar(int idDetalle) {
        String sql = "DELETE FROM detalle_evaluacion WHERE id_detalle = ?";
        try (PreparedStatement pst = cn.prepareStatement(sql)) {
            pst.setInt(1, idDetalle);
            return pst.executeUpdate() > 0;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al eliminar detalle: " + e.getMessage());
            return false;
        }
    }
}

