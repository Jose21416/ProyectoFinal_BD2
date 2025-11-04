package Lógica;

import Datos.DNota_evaluacion;
import java.sql.*;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class LDetalle_evaluacion {

    LConexion con = new LConexion();
    Connection cn = con.getConnection();

    // =========================================================
    // INSERTAR (registrar nota de un estudiante en una evaluación)
    // =========================================================
    public boolean insertar(DNota_evaluacion dts) {
        String sql = "INSERT INTO nota_evaluacion (id_usuario, id_evaluacion, nota) VALUES (?, ?, ?)";
        try (PreparedStatement pst = cn.prepareStatement(sql)) {
            pst.setInt(1, dts.getId_usuario());
            pst.setInt(2, dts.getId_evaluacion());
            pst.setDouble(3, dts.getNota());
            pst.executeUpdate();
            return true;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al insertar nota de evaluación: " + e.getMessage());
            return false;
        }
    }

    // =========================================================
    // MOSTRAR todas las evaluaciones de una asignatura para un estudiante
    // =========================================================
    public DefaultTableModel mostrarPorAsignatura(int idUsuario, int idAsignatura) {
        String[] titulos = {"ID Evaluación", "Evaluación", "Peso (%)", "Nota", "Contribución"};
        DefaultTableModel modelo = new DefaultTableModel(null, titulos);

        String sql = """
            SELECT 
                e.id_evaluacion,
                e.nombre AS evaluacion,
                e.porcentaje AS peso,
                ne.nota,
                ROUND(ne.nota * e.porcentaje / 100, 2) AS contribucion
            FROM evaluacion e
            LEFT JOIN nota_evaluacion ne ON e.id_evaluacion = ne.id_evaluacion AND ne.id_usuario = ?
            WHERE e.id_asignatura = ?
            ORDER BY e.nombre;
        """;

        try (PreparedStatement pst = cn.prepareStatement(sql)) {
            pst.setInt(1, idUsuario);
            pst.setInt(2, idAsignatura);
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                String[] registro = new String[5];
                registro[0] = String.valueOf(rs.getInt("id_evaluacion"));
                registro[1] = rs.getString("evaluacion");
                registro[2] = String.valueOf(rs.getDouble("peso"));
                registro[3] = (rs.getObject("nota") == null) ? "-" : String.valueOf(rs.getDouble("nota"));
                registro[4] = (rs.getObject("nota") == null) ? "-" : String.valueOf(rs.getDouble("contribucion"));
                modelo.addRow(registro);
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al mostrar evaluaciones: " + e.getMessage());
        }

        return modelo;
    }

    // =========================================================
    // CALCULAR PROMEDIO ponderado de una asignatura
    // =========================================================
    public double calcularPromedio(int idUsuario, int idAsignatura) {
        double promedio = 0;
        String sql = """
            SELECT 
                ROUND(SUM(ne.nota * e.porcentaje / 100), 2) AS promedio
            FROM nota_evaluacion ne
            INNER JOIN evaluacion e ON ne.id_evaluacion = e.id_evaluacion
            WHERE ne.id_usuario = ? AND e.id_asignatura = ?;
        """;

        try (PreparedStatement pst = cn.prepareStatement(sql)) {
            pst.setInt(1, idUsuario);
            pst.setInt(2, idAsignatura);
            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                promedio = rs.getDouble("promedio");
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al calcular promedio: " + e.getMessage());
        }

        return Math.round(promedio * 100.0) / 100.0;
    }

    // =========================================================
    // ELIMINAR una nota de evaluación
    // =========================================================
    public boolean eliminar(int idNotaEval) {
        String sql = "DELETE FROM nota_evaluacion WHERE id_nota_eval = ?";
        try (PreparedStatement pst = cn.prepareStatement(sql)) {
            pst.setInt(1, idNotaEval);
            return pst.executeUpdate() > 0;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al eliminar nota de evaluación: " + e.getMessage());
            return false;
        }
    }
}
