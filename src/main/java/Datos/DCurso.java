package Datos;

public class DCurso {

    private int id_curso;
    private String nombre;
    private String descripcion;

    public DCurso() {
    }

    public DCurso(int id_curso, String nombre, String descripcion) {
        this.id_curso = id_curso;
        this.nombre = nombre;
        this.descripcion = descripcion;
    }

    public int getId_curso() {
        return id_curso;
    }

    public void setId_curso(int id_curso) {
        this.id_curso = id_curso;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    
    
    
}
