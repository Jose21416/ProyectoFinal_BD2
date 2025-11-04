package Datos;

public class DEvaluacion {

    int id_evaluacion;
    int id_asignatura;
    String nombre;
    Double porcentaje;

    public DEvaluacion() {
    }

    public DEvaluacion(int id_evaluacion, int id_asignatura, String nombre, Double porcentaje) {
        this.id_evaluacion = id_evaluacion;
        this.id_asignatura = id_asignatura;
        this.nombre = nombre;
        this.porcentaje = porcentaje;
    }

    public int getId_evaluacion() {
        return id_evaluacion;
    }

    public void setId_evaluacion(int id_evaluacion) {
        this.id_evaluacion = id_evaluacion;
    }

    public int getId_asignatura() {
        return id_asignatura;
    }

    public void setId_asignatura(int id_asignatura) {
        this.id_asignatura = id_asignatura;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Double getPorcentaje() {
        return porcentaje;
    }

    public void setPorcentaje(Double porcentaje) {
        this.porcentaje = porcentaje;
    }
    
    
    
}
