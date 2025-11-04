package Presentación;

import Lógica.LNotaFinal;
import Datos.DNotaFinal;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class FrmNotas extends javax.swing.JInternalFrame {

    private int idUsuario;

    public FrmNotas() {
        initComponents();
    }

    public FrmNotas(int idusuario) {
        initComponents();
        this.idUsuario = idusuario;
        System.out.println("ID recibido en FrmNotas: " + idusuario);

        cargarPeriodos();
        mostrarNotas();
        tblPromedioPonderado.setForeground(new java.awt.Color(0, 0, 0));
        tblPromedioPonderado.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 14));

    }

    private void mostrarNotas() {
        LNotaFinal func = new LNotaFinal();
        DNotaFinal dts = new DNotaFinal();
        dts.setId_usuario(idUsuario);

        String periodo = (String) cbPeriodo.getSelectedItem();
        if (periodo != null && !periodo.equals("Elija el periodo académico...")) {
            dts.setPeriodo(periodo);
        } else {
            dts.setPeriodo(null);
        }

        // Mostrar notas filtradas (o todas si periodo es null)
        DefaultTableModel modelo = func.mostrarNotasPorEstudiante(dts);
        tblNotas.setModel(modelo);

        // Calcular el promedio solo si hay un periodo seleccionado
        if (dts.getPeriodo() != null) {
            double promedio = func.calcularPromedioPorPeriodo(idUsuario, periodo);
            tblPromedioPonderado.setText(String.format("%.2f", promedio));
        } else {
            tblPromedioPonderado.setText("—");
        }
    }

    private void cargarPeriodos() {
        cbPeriodo.removeAllItems();
        cbPeriodo.addItem("Elija el periodo académico...");
        cbPeriodo.addItem("2024-I");
        cbPeriodo.addItem("2024-II");
        cbPeriodo.addItem("2025-I");
        cbPeriodo.addItem("2025-II");
        cbPeriodo.setSelectedIndex(0); // Selecciona "Elija el periodo académico..."
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel2 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblNotas = new javax.swing.JTable();
        jSeparator1 = new javax.swing.JSeparator();
        jLabel1 = new javax.swing.JLabel();
        cbPeriodo = new javax.swing.JComboBox<>();
        jLabel3 = new javax.swing.JLabel();
        tblPromedioPonderado = new javax.swing.JLabel();

        setClosable(true);

        jLabel2.setText("Periodo:");

        tblNotas.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        tblNotas.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblNotasMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblNotas);

        jLabel1.setText("Mis notas");

        cbPeriodo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbPeriodoActionPerformed(evt);
            }
        });

        jLabel3.setText("PROMEDIO PONDERADO:");

        tblPromedioPonderado.setText("jLabel4");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jSeparator1, javax.swing.GroupLayout.Alignment.TRAILING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(273, 273, 273)
                        .addComponent(jLabel1))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 594, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(cbPeriodo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(tblPromedioPonderado)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addComponent(jLabel1)
                .addGap(12, 12, 12)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(57, 57, 57)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cbPeriodo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 249, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 37, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(tblPromedioPonderado))
                .addGap(28, 28, 28))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void cbPeriodoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbPeriodoActionPerformed
        mostrarNotas();
    }//GEN-LAST:event_cbPeriodoActionPerformed

    private int obtenerIdAsignaturaPorNombre(String nombreAsignatura) {
        int id = -1;
        String sql = "SELECT id_asignatura FROM asignatura WHERE nombre = ?";
        try (java.sql.PreparedStatement pst = new Lógica.LConexion().getConnection().prepareStatement(sql)) {
            pst.setString(1, nombreAsignatura);
            java.sql.ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                id = rs.getInt("id_asignatura");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al obtener id de asignatura: " + e.getMessage());
        }
        return id;
    }

    private void tblNotasMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblNotasMouseClicked
        int fila = tblNotas.getSelectedRow();
        if (fila >= 0) {
            int idAsignatura = obtenerIdAsignaturaPorNombre(tblNotas.getValueAt(fila, 1).toString());
            String asignatura = tblNotas.getValueAt(fila, 1).toString();
            String periodo = tblNotas.getValueAt(fila, 2).toString();

            FrmDetalle_evaluacion frm = new FrmDetalle_evaluacion(idUsuario, idAsignatura, asignatura, periodo);
            frm.setVisible(true);
        } else {
            JOptionPane.showMessageDialog(this, "Seleccione una fila válida.");
        }
    }//GEN-LAST:event_tblNotasMouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> cbPeriodo;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JTable tblNotas;
    private javax.swing.JLabel tblPromedioPonderado;
    // End of variables declaration//GEN-END:variables

}
