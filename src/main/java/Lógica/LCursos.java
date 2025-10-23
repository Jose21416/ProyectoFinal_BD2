package Lógica;

import Datos.DCursos;
import javax.swing.table.DefaultTableModel;

public class LCursos {

    public DefaultTableModel mostrarCursosPorCorreo(String correo) {
        DCursos dao = new DCursos();
        return dao.mostrarCursosPorCorreo(correo);
    }

    public boolean insertarCurso(String nombre, String descripcion) {
        DCursos dao = new DCursos();
        dao.setNombre(nombre);
        dao.setDescripcion(descripcion);
        return dao.insertar();
    }

    public boolean editarCurso(int id, String nombre, String descripcion) {
        DCursos dao = new DCursos(id, nombre, descripcion);
        return dao.editar();
    }

    public boolean eliminarCurso(int id) {
        DCursos dao = new DCursos();
        dao.setIdCurso(id);
        return dao.eliminar();
    }
}

