package Presentación;

import Datos.DAsignatura;
import Datos.DUsuarios;
import Lógica.LGestionNotas;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class FrmGestionNotas extends javax.swing.JFrame {

    private LGestionNotas logica = new LGestionNotas();
    private DefaultTableModel modeloTabla;

    private int idAlumnoSeleccionado = -1;
    private int idAsignaturaSeleccionada = -1;
    private int idEvaluacionSeleccionada = -1;

    public FrmGestionNotas() {
        initComponents();
        configuracionInicial();
        setLocationRelativeTo(null);
    }

    private void configuracionInicial() {
        modeloTabla = new DefaultTableModel(new Object[]{"ID Eval", "Evaluación", "Porcentaje", "Nota"}, 0);
        tblGestionNotas.setModel(modeloTabla);

        tblGestionNotas.getColumnModel().getColumn(0).setMinWidth(0);
        tblGestionNotas.getColumnModel().getColumn(0).setMaxWidth(0);
        tblGestionNotas.getColumnModel().getColumn(0).setWidth(0);

        bloquearControles();

        cargarComboAlumnos();
    }

    private void bloquearControles() {
        cmbAsignatura.setEnabled(false);
        tblGestionNotas.setEnabled(false);
        txtEvaluacion.setEnabled(false);
        txtNota.setEnabled(false);
        btnModificar.setEnabled(false);
        btnLimpiar.setEnabled(false);

        cmbAsignatura.removeAllItems();
        limpiarTabla();
    }

    private void limpiarTabla() {
        modeloTabla = new DefaultTableModel(new Object[]{"ID Eval", "Evaluación", "Porcentaje", "Nota"}, 0);
        tblGestionNotas.setModel(modeloTabla);
        tblGestionNotas.getColumnModel().getColumn(0).setMinWidth(0);
        tblGestionNotas.getColumnModel().getColumn(0).setMaxWidth(0);
        tblGestionNotas.getColumnModel().getColumn(0).setWidth(0);
    }

    private void cargarComboAlumnos() {
        cmbAlumno.removeAllItems();
        cmbAlumno.addItem("Seleccione alumno...");

        List<DUsuarios> lista = logica.listarEstudiantesActivos();
        for (DUsuarios u : lista) {
            cmbAlumno.addItem(new Item(u.getId_usuario(), u.getNombre()));
        }
    }

    private void cargarComboAsignaturas(int idAlumno) {
        cmbAsignatura.removeAllItems();
        cmbAsignatura.addItem("Seleccione asignatura...");

        List<DAsignatura> lista = logica.listarAsignaturasPorAlumno(idAlumno);
        for (DAsignatura a : lista) {
            cmbAsignatura.addItem(new Item(a.getId_asignatura(), a.getNombre()));
        }

        cmbAsignatura.setEnabled(true);
    }

    private void cargarNotasEnTabla() {
        modeloTabla = logica.mostrarNotasEstudiante(idAlumnoSeleccionado, idAsignaturaSeleccionada);
        tblGestionNotas.setModel(modeloTabla);

        tblGestionNotas.getColumnModel().getColumn(0).setMinWidth(0);
        tblGestionNotas.getColumnModel().getColumn(0).setMaxWidth(0);
        tblGestionNotas.getColumnModel().getColumn(0).setWidth(0);

        tblGestionNotas.setEnabled(true);
        btnLimpiar.setEnabled(true);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        cmbAlumno = new javax.swing.JComboBox<>();
        jLabel3 = new javax.swing.JLabel();
        cmbAsignatura = new javax.swing.JComboBox<>();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblGestionNotas = new javax.swing.JTable();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        txtEvaluacion = new javax.swing.JTextField();
        txtNota = new javax.swing.JTextField();
        btnModificar = new javax.swing.JButton();
        btnLimpiar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setText("Gestión de notas");

        jLabel2.setText("Alumno:");

        cmbAlumno.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbAlumnoActionPerformed(evt);
            }
        });

        jLabel3.setText("Asignatura:");

        cmbAsignatura.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbAsignaturaActionPerformed(evt);
            }
        });

        tblGestionNotas.setModel(new javax.swing.table.DefaultTableModel(
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
        tblGestionNotas.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblGestionNotasMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblGestionNotas);

        jLabel4.setText("Evaluación:");

        jLabel5.setText("Nota:");

        btnModificar.setText("Modificar");
        btnModificar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnModificarActionPerformed(evt);
            }
        });

        btnLimpiar.setText("Limpiar");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(32, 32, 32)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(36, 36, 36)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(93, 93, 93)
                                .addComponent(btnModificar)
                                .addGap(28, 28, 28)
                                .addComponent(btnLimpiar))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel4)
                                .addGap(18, 18, 18)
                                .addComponent(txtEvaluacion, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(67, 67, 67)
                                .addComponent(jLabel5)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtNota)))
                        .addGap(90, 90, 90))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addGroup(layout.createSequentialGroup()
                                    .addComponent(jLabel2)
                                    .addGap(33, 33, 33)
                                    .addComponent(cmbAlumno, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGroup(layout.createSequentialGroup()
                                    .addComponent(jLabel3)
                                    .addGap(18, 18, 18)
                                    .addComponent(cmbAsignatura, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addGap(215, 215, 215))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(cmbAlumno, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(cmbAsignatura, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(43, 43, 43)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 213, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(26, 26, 26)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(txtEvaluacion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5)
                    .addComponent(txtNota, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(50, 50, 50)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnModificar)
                    .addComponent(btnLimpiar))
                .addContainerGap(76, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void cmbAsignaturaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbAsignaturaActionPerformed
        if (cmbAsignatura.isEnabled() && cmbAsignatura.getSelectedIndex() > 0) {
            Item item = (Item) cmbAsignatura.getSelectedItem();
            idAsignaturaSeleccionada = item.getId();

            cargarNotasEnTabla();
            txtNota.setEnabled(true);
            btnModificar.setEnabled(true);

        } else {
            idAsignaturaSeleccionada = -1;
            limpiarTabla();
            tblGestionNotas.setEnabled(false);
        }
    }//GEN-LAST:event_cmbAsignaturaActionPerformed

    private void cmbAlumnoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbAlumnoActionPerformed
        if (cmbAlumno.getSelectedIndex() <= 0) {
            bloquearControles();
            return;
        }

        Object seleccion = cmbAlumno.getSelectedItem();

        if (seleccion instanceof Item) {
            Item item = (Item) seleccion;

            idAlumnoSeleccionado = item.getId();

            limpiarTabla();

            cargarComboAsignaturas(idAlumnoSeleccionado);

        } else {
            System.out.println("Error: El elemento seleccionado no es un objeto Item validó.");
        }
    }//GEN-LAST:event_cmbAlumnoActionPerformed

    private void tblGestionNotasMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblGestionNotasMouseClicked
        int fila = tblGestionNotas.getSelectedRow();

        if (fila >= 0) {

            try {
                idEvaluacionSeleccionada = Integer.parseInt(tblGestionNotas.getValueAt(fila, 0).toString());
            } catch (NumberFormatException e) {
                System.out.println("Error al obtener ID: " + e.getMessage());
            }

            String nombreEvaluacion = tblGestionNotas.getValueAt(fila, 1).toString();
            Object valorNota = tblGestionNotas.getValueAt(fila, 3);

            txtEvaluacion.setText(nombreEvaluacion);

            if (valorNota != null) {
                txtNota.setText(valorNota.toString());
            } else {
                txtNota.setText("0.0");
            }

            txtNota.setEnabled(true);
        }
    }//GEN-LAST:event_tblGestionNotasMouseClicked

    private void btnModificarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnModificarActionPerformed
        if (idEvaluacionSeleccionada == -1 || idAlumnoSeleccionado == -1) {
            return;
        }

        try {
            double nota = Double.parseDouble(txtNota.getText());
            if (nota < 0 || nota > 20) {
                JOptionPane.showMessageDialog(this, "La nota debe estar entre 0 y 20");
                return;
            }

            boolean exito = logica.actualizarNota(idAlumnoSeleccionado, idEvaluacionSeleccionada, nota);

            if (exito) {
                JOptionPane.showMessageDialog(this, "Nota actualizada correctamente");
                cargarNotasEnTabla();
                txtEvaluacion.setText("");
                txtNota.setText("");
                txtNota.setEnabled(false);
                btnModificar.setEnabled(false);
            }

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Por favor ingrese una nota válida (número).");
        }
    }//GEN-LAST:event_btnModificarActionPerformed

    public static void main(String args[]) {

        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(FrmGestionNotas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FrmGestionNotas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FrmGestionNotas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FrmGestionNotas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }

        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FrmGestionNotas().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnLimpiar;
    private javax.swing.JButton btnModificar;
    private javax.swing.JComboBox<Object> cmbAlumno;
    private javax.swing.JComboBox<Object> cmbAsignatura;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tblGestionNotas;
    private javax.swing.JTextField txtEvaluacion;
    private javax.swing.JTextField txtNota;
    // End of variables declaration//GEN-END:variables

}

class Item {

    private int id;
    private String descripcion;

    public Item(int id, String descripcion) {
        this.id = id;
        this.descripcion = descripcion;
    }

    public int getId() {
        return id;
    }

    @Override
    public String toString() {
        return descripcion;
    }
}
