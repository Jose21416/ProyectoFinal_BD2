package Datos;
//ae
public class DAsignatura {

    private int id_asignatura;
    private int id_curso;
    private String nombre;
    private int creditos;
    private String nombreCurso;

    public DAsignatura() {
    }

    public DAsignatura(int id_asignatura, int id_curso, String nombre, int creditos, String nombreCurso) {
        this.id_asignatura = id_asignatura;
        this.id_curso = id_curso;
        this.nombre = nombre;
        this.creditos = creditos;
        this.nombreCurso = nombreCurso;
    }

    public int getId_asignatura() {
        return id_asignatura;
    }

    public void setId_asignatura(int id_asignatura) {
        this.id_asignatura = id_asignatura;
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

    public int getCreditos() {
        return creditos;
    }

    public void setCreditos(int creditos) {
        this.creditos = creditos;
    }
    
    public String getNombreCurso() {
        return nombreCurso;
    }
    
    public void setNombreCurso() {
        this.nombreCurso = nombreCurso;
    }
    
}
