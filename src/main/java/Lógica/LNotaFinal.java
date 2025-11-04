package Lógica;

import Datos.DNotaFinal;
import java.sql.*;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class LNotaFinal {

    LConexion con = new LConexion();
    Connection cn = con.getConnection();

    public DefaultTableModel mostrarNotasPorEstudiante(DNotaFinal dts) {
        String[] titulos = {"ID Nota", "Asignatura", "Periodo", "Nota Final"};
        DefaultTableModel modelo = new DefaultTableModel(null, titulos);

        // Si el periodo no está definido, muestra todas las notas del estudiante
        String sql;
        if (dts.getPeriodo() == null || dts.getPeriodo().trim().isEmpty()) {
            sql = """
            SELECT n.id_notaFinal, a.nombre AS asignatura, n.periodo, n.notaFinal
            FROM notaFinal n
            INNER JOIN asignatura a ON n.id_asignatura = a.id_asignatura
            WHERE n.id_usuario = ?
            ORDER BY n.periodo DESC;
        """;
        } else {
            sql = """
            SELECT n.id_notaFinal, a.nombre AS asignatura, n.periodo, n.notaFinal
            FROM notaFinal n
            INNER JOIN asignatura a ON n.id_asignatura = a.id_asignatura
            WHERE n.id_usuario = ? AND n.periodo = ?
            ORDER BY a.nombre;
        """;
        }

        try (PreparedStatement pst = cn.prepareStatement(sql)) {
            pst.setInt(1, dts.getId_usuario());

            if (dts.getPeriodo() != null && !dts.getPeriodo().trim().isEmpty()) {
                pst.setString(2, dts.getPeriodo());
            }

            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                String[] registro = new String[4];
                registro[0] = String.valueOf(rs.getInt("id_notaFinal"));
                registro[1] = rs.getString("asignatura");
                registro[2] = rs.getString("periodo");
                registro[3] = String.valueOf(rs.getDouble("notaFinal"));
                modelo.addRow(registro);
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al mostrar notas: " + e.getMessage());
        }

        return modelo;
    }

    public double calcularPromedioPorPeriodo(int idUsuario, String periodo) {
        double promedio = 0;
        String sql = """
        SELECT 
            ROUND(SUM(n.notaFinal * a.creditos) / SUM(a.creditos), 2) AS promedio
        FROM notaFinal n
        INNER JOIN asignatura a ON n.id_asignatura = a.id_asignatura
        WHERE n.id_usuario = ? AND n.periodo = ?;
    """;

        try (PreparedStatement pst = cn.prepareStatement(sql)) {
            pst.setInt(1, idUsuario);
            pst.setString(2, periodo);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                promedio = rs.getDouble("promedio");
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al calcular promedio: " + e.getMessage());
        }

        return promedio;
    }

}
