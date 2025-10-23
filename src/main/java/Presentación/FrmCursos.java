package Presentación;

import Lógica.LCursos;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class FrmCursos extends JFrame {

    private final JTextField txtCorreo, txtNombre, txtDescripcion, txtIdCurso;
    private JTable tblCursos;
    private final LCursos logicaCursos = new LCursos();

    public FrmCursos() {
        setTitle("Gestión de Cursos - Administrador");
        setSize(800, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Panel superior
        JPanel panelTop = new JPanel();
        panelTop.setBackground(new Color(173, 216, 230));
        panelTop.add(new JLabel("Correo del Estudiante:"));
        txtCorreo = new JTextField(20);
        JButton btnBuscar = new JButton("Buscar");
        panelTop.add(txtCorreo);
        panelTop.add(btnBuscar);

        // Panel central
        tblCursos = new JTable();
        JScrollPane scroll = new JScrollPane(tblCursos);

        // Panel inferior
        JPanel panelBottom = new JPanel(new GridLayout(2, 4, 5, 5));
        txtIdCurso = new JTextField(); txtIdCurso.setVisible(false);
        txtNombre = new JTextField();
        txtDescripcion = new JTextField();

        panelBottom.add(new JLabel("Nombre:"));
        panelBottom.add(txtNombre);
        panelBottom.add(new JLabel("Descripción:"));
        panelBottom.add(txtDescripcion);

        JButton btnAgregar = new JButton("Agregar");
        JButton btnEditar = new JButton("Editar");
        JButton btnEliminar = new JButton("Eliminar");
        JButton btnLimpiar = new JButton("Limpiar");

        panelBottom.add(btnAgregar);
        panelBottom.add(btnEditar);
        panelBottom.add(btnEliminar);
        panelBottom.add(btnLimpiar);

        add(panelTop, BorderLayout.NORTH);
        add(scroll, BorderLayout.CENTER);
        add(panelBottom, BorderLayout.SOUTH);

        // --- Eventos ---
        btnBuscar.addActionListener(e -> mostrarCursos());
        btnAgregar.addActionListener(e -> agregarCurso());
        btnEditar.addActionListener(e -> editarCurso());
        btnEliminar.addActionListener(e -> eliminarCurso());
        btnLimpiar.addActionListener(e -> limpiarCampos());

        tblCursos.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent evt) {
                int fila = tblCursos.rowAtPoint(evt.getPoint());
                txtIdCurso.setText(tblCursos.getValueAt(fila, 0).toString());
                txtNombre.setText(tblCursos.getValueAt(fila, 1).toString());
                txtDescripcion.setText(tblCursos.getValueAt(fila, 2).toString());
            }
        });
    }

    private void mostrarCursos() {
        String correo = txtCorreo.getText().trim();
        if (correo.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Ingrese un correo válido");
            return;
        }
        tblCursos.setModel(logicaCursos.mostrarCursosPorCorreo(correo));
    }

    private void agregarCurso() {
        if (logicaCursos.insertarCurso(txtNombre.getText(), txtDescripcion.getText())) {
            JOptionPane.showMessageDialog(this, "Curso agregado correctamente");
            mostrarCursos();
            limpiarCampos();
        }
    }

    private void editarCurso() {
        if (txtIdCurso.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Seleccione un curso");
            return;
        }
        int id = Integer.parseInt(txtIdCurso.getText());
        if (logicaCursos.editarCurso(id, txtNombre.getText(), txtDescripcion.getText())) {
            JOptionPane.showMessageDialog(this, "Curso actualizado correctamente");
            mostrarCursos();
            limpiarCampos();
        }
    }

    private void eliminarCurso() {
        if (txtIdCurso.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Seleccione un curso");
            return;
        }
        int id = Integer.parseInt(txtIdCurso.getText());
        if (logicaCursos.eliminarCurso(id)) {
            JOptionPane.showMessageDialog(this, "Curso eliminado correctamente");
            mostrarCursos();
            limpiarCampos();
        }
    }

    private void limpiarCampos() {
        txtIdCurso.setText("");
        txtNombre.setText("");
        txtDescripcion.setText("");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new FrmCursos().setVisible(true));
    }
}
