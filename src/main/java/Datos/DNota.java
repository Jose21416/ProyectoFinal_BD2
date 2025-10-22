package Datos;

public class DNota {

    private int id_nota;
    private int id_usuario;
    private int id_asignatura;
    private String periodo;
    private double nota;

    public DNota() {
    }

    public DNota(int id_nota, int id_usuario, int id_asignatura, String periodo, double nota) {
        this.id_nota = id_nota;
        this.id_usuario = id_usuario;
        this.id_asignatura = id_asignatura;
        this.periodo = periodo;
        this.nota = nota;
    }

    public int getId_nota() {
        return id_nota;
    }

    public void setId_nota(int id_nota) {
        this.id_nota = id_nota;
    }

    public int getId_usuario() {
        return id_usuario;
    }

    public void setId_usuario(int id_usuario) {
        this.id_usuario = id_usuario;
    }

    public int getId_asignatura() {
        return id_asignatura;
    }

    public void setId_asignatura(int id_asignatura) {
        this.id_asignatura = id_asignatura;
    }

    public String getPeriodo() {
        return periodo;
    }

    public void setPeriodo(String periodo) {
        this.periodo = periodo;
    }

    public double getNota() {
        return nota;
    }

    public void setNota(double nota) {
        this.nota = nota;
    }
    
    
    
}
