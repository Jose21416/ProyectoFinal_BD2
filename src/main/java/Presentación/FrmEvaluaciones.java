package Presentación;

import Datos.DAsignatura;
import Datos.DEvaluacion;
import Lógica.LAsignatura;
import Lógica.LEvaluacion;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class FrmEvaluaciones extends javax.swing.JFrame {
    private int idSeleccionado = -1;
    public FrmEvaluaciones() {
        initComponents();
        cargarAsignaturas();
        cargarTabla();
        setTitle("Evaluaciones");
        setLocationRelativeTo(null);
        cbxAsignaturas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cargarTabla();
            }
        });
        txtpeso.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                char c = evt.getKeyChar();
                if (!Character.isDigit(c) && c != '.') {
                    evt.consume();
                }
                if (c == '.' && txtpeso.getText().contains(".")) {
                    evt.consume();
                }
            }
        });
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent e) {
                new FrmAdministrador().setVisible(true);
            }
        });
    }

    private void cargarAsignaturas() {
        LAsignatura logica = new LAsignatura();
        List<DAsignatura> lista = logica.listarAsignaturas();

        cbxAsignaturas.removeAllItems();

        // 👉 OPCIÓN POR DEFECTO
        cbxAsignaturas.addItem("Seleccione Asignatura");

        for (DAsignatura a : lista) {
            cbxAsignaturas.addItem(a.getNombre()); 
        }

        // 👉 Dejar "Seleccione Asignatura" seleccionado
        cbxAsignaturas.setSelectedIndex(0);
    }

     private void cargarTabla() {
        String columnas[] = {"ID", "Evaluación", "Peso"};
        DefaultTableModel modelo = new DefaultTableModel(null, columnas);
        tblEvaluaciones.setModel(modelo);

        // Si no seleccionaron nada válido → no mostrar nada
        if (cbxAsignaturas.getSelectedIndex() <= 0) {
            return;
        }

        LEvaluacion logica = new LEvaluacion();

        int idAsignatura = new LAsignatura().obtenerIdPorNombre(
                cbxAsignaturas.getSelectedItem().toString()
        );

        List<DEvaluacion> lista = logica.listarEvaluacionesPorAsignatura(idAsignatura);

        for (DEvaluacion e : lista) {
            modelo.addRow(new Object[]{
                e.getId_evaluacion(),
                e.getNombre(),
                e.getPorcentaje()
            });
        }
    }
    
    
    private DEvaluacion getEvaluacionFromForm() {
        DEvaluacion e = new DEvaluacion();
        e.setNombre(txtEvaluacion.getText().trim());
        e.setPorcentaje(Double.parseDouble(txtpeso.getText().trim()));

        int idAsignatura = new LAsignatura().obtenerIdPorNombre(
                cbxAsignaturas.getSelectedItem().toString()
        );

        e.setId_asignatura(idAsignatura); 
        return e;
    }

    
    private void limpiarCampos() {
        txtEvaluacion.setText("");
        txtpeso.setText("");
        cbxAsignaturas.setSelectedIndex(0);

        DefaultTableModel modelo = (DefaultTableModel) tblEvaluaciones.getModel();
        modelo.setRowCount(0);

        idSeleccionado = -1;
    }
    
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        cbxAsignaturas = new javax.swing.JComboBox<>();
        txtEvaluacion = new javax.swing.JTextField();
        txtpeso = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblEvaluaciones = new javax.swing.JTable();
        btnAgregar = new javax.swing.JButton();
        btnLimpiar = new javax.swing.JButton();
        btnEditar = new javax.swing.JButton();
        btnEliminar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(255, 255, 255));

        jLabel1.setFont(new java.awt.Font("Segoe UI Black", 1, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 0, 0));
        jLabel1.setText("Modulo de evaluaciones");

        jLabel2.setFont(new java.awt.Font("Segoe UI Black", 1, 14)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(0, 0, 0));
        jLabel2.setText("Nombre de evaluación:");

        jLabel3.setFont(new java.awt.Font("Segoe UI Black", 1, 14)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(0, 0, 0));
        jLabel3.setText("Peso:");

        jLabel4.setFont(new java.awt.Font("Segoe UI Black", 1, 14)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(0, 0, 0));
        jLabel4.setText("Asignatura:");

        cbxAsignaturas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbxAsignaturasActionPerformed(evt);
            }
        });

        txtEvaluacion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtEvaluacionActionPerformed(evt);
            }
        });

        tblEvaluaciones.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        tblEvaluaciones.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblEvaluacionesMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblEvaluaciones);

        btnAgregar.setBackground(new java.awt.Color(0, 153, 153));
        btnAgregar.setForeground(new java.awt.Color(51, 51, 51));
        btnAgregar.setText("Agregar");
        btnAgregar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAgregarActionPerformed(evt);
            }
        });

        btnLimpiar.setBackground(new java.awt.Color(0, 153, 153));
        btnLimpiar.setForeground(new java.awt.Color(51, 51, 51));
        btnLimpiar.setText("Limpiar");
        btnLimpiar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLimpiarActionPerformed(evt);
            }
        });

        btnEditar.setBackground(new java.awt.Color(0, 153, 153));
        btnEditar.setForeground(new java.awt.Color(51, 51, 51));
        btnEditar.setText("Editar");
        btnEditar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditarActionPerformed(evt);
            }
        });

        btnEliminar.setBackground(new java.awt.Color(0, 153, 153));
        btnEliminar.setForeground(new java.awt.Color(51, 51, 51));
        btnEliminar.setText("Eliminar");
        btnEliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap(85, Short.MAX_VALUE)
                        .addComponent(jLabel1)
                        .addGap(78, 78, 78))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.LEADING))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(txtEvaluacion, javax.swing.GroupLayout.DEFAULT_SIZE, 160, Short.MAX_VALUE)
                                    .addComponent(txtpeso)))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(57, 57, 57)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(btnAgregar, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(btnEditar, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(59, 59, 59)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(btnEliminar, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(btnLimpiar, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel4)
                                .addGap(102, 102, 102)
                                .addComponent(cbxAsignaturas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 36, Short.MAX_VALUE)))
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(75, 75, 75))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(20, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 346, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(30, 30, 30)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4)
                            .addComponent(cbxAsignaturas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(txtEvaluacion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(txtpeso, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(26, 26, 26)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnAgregar, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnLimpiar, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(47, 47, 47)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnEditar, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnEliminar, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(20, 20, 20)))
                .addGap(28, 28, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtEvaluacionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtEvaluacionActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtEvaluacionActionPerformed

    private void btnEditarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditarActionPerformed
         if (idSeleccionado <= 0) {
            JOptionPane.showMessageDialog(this, "Seleccione una evaluación de la tabla.");
            return;
        }

        if (txtEvaluacion.getText().trim().isEmpty() || txtpeso.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Complete todos los campos.");
            return;
        }

        double peso;
        try {
            peso = Double.parseDouble(txtpeso.getText().trim());
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "El peso debe ser un número válido.");
            return;
        }

        DEvaluacion e = new DEvaluacion();
        e.setId_evaluacion(idSeleccionado);
        e.setNombre(txtEvaluacion.getText().trim());
        e.setPorcentaje(peso);

        LEvaluacion logica = new LEvaluacion();

        if (logica.actualizarEvaluacion(e)) {
            JOptionPane.showMessageDialog(this, "Evaluación editada correctamente.");
            cargarTabla(); // 🔥 Recargar tabla
        }
    }//GEN-LAST:event_btnEditarActionPerformed

    private void btnLimpiarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLimpiarActionPerformed
        limpiarCampos();
        tblEvaluaciones.setModel(new DefaultTableModel());  // dejar vacía
        cbxAsignaturas.setSelectedIndex(0);                   // volver a "Seleccione asignatura"
    }//GEN-LAST:event_btnLimpiarActionPerformed

    private void btnAgregarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAgregarActionPerformed
        if (txtEvaluacion.getText().trim().isEmpty() || txtpeso.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Complete todos los campos.");
            return;
        }
        double peso;
        try {
            peso = Double.parseDouble(txtpeso.getText().trim());
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "El peso debe ser un número válido (int o decimal).");
            return;
        }

        DEvaluacion e = getEvaluacionFromForm();
        if (e == null) {
            return;
        }

        LEvaluacion logica = new LEvaluacion();

        if (logica.existeEvaluacion(e)) {
            JOptionPane.showMessageDialog(this, "Esta evaluación ya existe.");
            return;
        }

        if (logica.crearEvaluacion(e)) {
            JOptionPane.showMessageDialog(this, "Evaluación registrada correctamente.");

            DefaultTableModel model = (DefaultTableModel) tblEvaluaciones.getModel();
            model.addRow(new Object[]{e.getId_evaluacion(), e.getNombre(), e.getPorcentaje()});

            cargarTabla();   
            idSeleccionado = -1;
        }
    }//GEN-LAST:event_btnAgregarActionPerformed

    private void btnEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarActionPerformed
        int fila = tblEvaluaciones.getSelectedRow();

        if (fila == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione una evaluación.");
            return;
        }

        int id = Integer.parseInt(tblEvaluaciones.getValueAt(fila, 0).toString());

        int confirm = JOptionPane.showConfirmDialog(this,
                "¿Seguro que deseas eliminar?",
                "Confirmación",
                JOptionPane.YES_NO_OPTION);

        if (confirm != JOptionPane.YES_OPTION) {
            return;
        }

        LEvaluacion logica = new LEvaluacion();

        if (logica.eliminarEvaluacion(id)) {
            JOptionPane.showMessageDialog(this, "Evaluación eliminada.");
            cargarTabla();   
        }
    }//GEN-LAST:event_btnEliminarActionPerformed

    private void tblEvaluacionesMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblEvaluacionesMouseClicked
        int fila = tblEvaluaciones.rowAtPoint(evt.getPoint());

        if (fila >= 0) {
            idSeleccionado = Integer.parseInt(tblEvaluaciones.getValueAt(fila, 0).toString());
            txtEvaluacion.setText(tblEvaluaciones.getValueAt(fila, 1).toString());
            txtpeso.setText(tblEvaluaciones.getValueAt(fila, 2).toString());
        }
    }//GEN-LAST:event_tblEvaluacionesMouseClicked

    private void cbxAsignaturasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbxAsignaturasActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cbxAsignaturasActionPerformed

    public static void main(String args[]) {

        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(FrmEvaluaciones.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FrmEvaluaciones.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FrmEvaluaciones.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FrmEvaluaciones.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }

        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FrmEvaluaciones().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAgregar;
    private javax.swing.JButton btnEditar;
    private javax.swing.JButton btnEliminar;
    private javax.swing.JButton btnLimpiar;
    private javax.swing.JComboBox<String> cbxAsignaturas;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tblEvaluaciones;
    private javax.swing.JTextField txtEvaluacion;
    private javax.swing.JTextField txtpeso;
    // End of variables declaration//GEN-END:variables

}
