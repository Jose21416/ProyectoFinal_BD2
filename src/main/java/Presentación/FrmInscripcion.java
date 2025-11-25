/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JInternalFrame.java to edit this template
 */
package Presentación;

import Datos.DCursos;
import Datos.DUsuarios;
import Lógica.LCursos;
import Lógica.LEstudiante;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author GMG
 */
public class FrmInscripcion extends javax.swing.JInternalFrame {

    /**
     * Creates new form FrmInscripcion
     */
    public FrmInscripcion() {
        initComponents();
        setTitle("Asignar Carrera");
        DefaultTableModel modeloEstudiantes = new DefaultTableModel(new Object[]{"ID", "Nombre", "Carrera"}, 0);
        tblEstudiantesInscritos.setModel(modeloEstudiantes);
        cargarEstudiantesMatriculados();
        cargarComboEstudiantes();
        cargarComboCursos();
    }

    public void cargarEstudiantesMatriculados() {
        LEstudiante le = new LEstudiante();
        List<DUsuarios> estudiantes = le.listarSoloEstudiantes();
        DefaultTableModel modelo = (DefaultTableModel) tblEstudiantesInscritos.getModel();
        modelo.setRowCount(0); 

        if (estudiantes.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No hay estudiantes registrados",
                    "Información", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        for (DUsuarios e : estudiantes) {
            String carrera = (e.getId_curso() > 0) ? obtenerNombreCurso(e.getId_curso()) : "Sin asignar";
            Object[] fila = {e.getId_usuario(), e.getNombre(), carrera};
            modelo.addRow(fila);
        }
    }

    private String obtenerNombreCurso(int id_curso) {
        LCursos lc = new LCursos();
        List<DCursos> cursos = lc.listarCursos();
        for (DCursos c : cursos) {
            if (c.getId_curso() == id_curso) {
                return c.getNombre();
            }
        }
        return "Sin asignar";
    }

    private void asignarEstudianteACurso() {
        DUsuarios estudiante = (DUsuarios) cbxEstudiantes.getSelectedItem();
        DCursos curso = (DCursos) CbxCurso.getSelectedItem();

        if (estudiante == null || estudiante.getId_usuario() == 0
                || curso == null || curso.getId_curso() == 0) {
            JOptionPane.showMessageDialog(this, "Debes seleccionar un estudiante y un curso",
                    "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (estudiante.getId_curso() > 0) {
            int respuesta = JOptionPane.showConfirmDialog(this,
                    "El estudiante ya tiene un curso asignado. ¿Desea reasignarlo?",
                    "Confirmar reasignación",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE);

            if (respuesta != JOptionPane.YES_OPTION) {
                return;
            }
        }

        LEstudiante le = new LEstudiante();
        boolean exito = le.asignarCursoAEstudiante(estudiante.getId_usuario(), curso.getId_curso());

        if (exito) {
            JOptionPane.showMessageDialog(this,
                    "Estudiante inscrito correctamente al curso: " + curso.getNombre(),
                    "Éxito",
                    JOptionPane.INFORMATION_MESSAGE);
            estudiante.setId_curso(curso.getId_curso());
            cargarEstudiantesMatriculados();
            cbxEstudiantes.setSelectedIndex(0);
            CbxCurso.setSelectedIndex(0);

        } else {
            JOptionPane.showMessageDialog(this,
                    "No se pudo inscribir al estudiante. Intenta nuevamente.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void cargarComboEstudiantes() {
        LEstudiante le = new LEstudiante();
        List<DUsuarios> estudiantes = le.listarSoloEstudiantes();
        DefaultComboBoxModel modelo = new DefaultComboBoxModel();
        DUsuarios estPlaceholder = new DUsuarios();
        estPlaceholder.setId_usuario(0);
        estPlaceholder.setNombre("Seleccione un estudiante");
        modelo.addElement(estPlaceholder);
        for (DUsuarios e : estudiantes) {
            modelo.addElement(e);
        }

        cbxEstudiantes.setModel(modelo);
    }

    private void cargarComboCursos() {
        LCursos lc = new LCursos();
        List<DCursos> cursos = lc.listarCursos();
        DefaultComboBoxModel modelo = new DefaultComboBoxModel();
        DCursos cursoPlaceholder = new DCursos();
        cursoPlaceholder.setId_curso(0);
        cursoPlaceholder.setNombre("Seleccione un curso");
        modelo.addElement(cursoPlaceholder);
        for (DCursos c : cursos) {
            modelo.addElement(c);
        }

        CbxCurso.setModel(modelo);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        cbxEstudiantes = new javax.swing.JComboBox<Datos.DUsuarios>();
        CbxCurso = new javax.swing.JComboBox<Datos.DCursos>();
        btnAsignar = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblEstudiantesInscritos = new javax.swing.JTable();

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel1.setText("Seleccionar Estudiante:");

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel2.setText("Seleccionar Curso:");

        CbxCurso.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CbxCursoActionPerformed(evt);
            }
        });

        btnAsignar.setBackground(new java.awt.Color(0, 102, 204));
        btnAsignar.setForeground(new java.awt.Color(255, 255, 255));
        btnAsignar.setText("Asignar");
        btnAsignar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAsignarActionPerformed(evt);
            }
        });

        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/tesis.png"))); // NOI18N

        tblEstudiantesInscritos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        jScrollPane1.setViewportView(tblEstudiantesInscritos);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(66, 66, 66)
                .addComponent(jLabel3)
                .addGap(77, 77, 77)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(cbxEstudiantes, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 158, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(CbxCurso, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(btnAsignar, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(86, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(41, 41, 41)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(cbxEstudiantes, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(CbxCurso, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(34, 34, 34))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)))
                .addComponent(btnAsignar, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(38, 38, 38)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 182, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(39, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void CbxCursoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CbxCursoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_CbxCursoActionPerformed

    private void btnAsignarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAsignarActionPerformed
       asignarEstudianteACurso();
    }//GEN-LAST:event_btnAsignarActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<DCursos> CbxCurso;
    private javax.swing.JButton btnAsignar;
    private javax.swing.JComboBox<DUsuarios> cbxEstudiantes;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tblEstudiantesInscritos;
    // End of variables declaration//GEN-END:variables
}
