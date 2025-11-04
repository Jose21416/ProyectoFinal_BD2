package Datos;

public class DNota_evaluacion {

    int id_nota_eval;
    int id_usuario;
    int id_evaluacion;
    double nota;

    public DNota_evaluacion() {
    }

    public DNota_evaluacion(int id_nota_eval, int id_usuario, int id_evaluacion, double nota) {
        this.id_nota_eval = id_nota_eval;
        this.id_usuario = id_usuario;
        this.id_evaluacion = id_evaluacion;
        this.nota = nota;
    }

    public int getId_nota_eval() {
        return id_nota_eval;
    }

    public void setId_nota_eval(int id_nota_eval) {
        this.id_nota_eval = id_nota_eval;
    }

    public int getId_usuario() {
        return id_usuario;
    }

    public void setId_usuario(int id_usuario) {
        this.id_usuario = id_usuario;
    }

    public int getId_evaluacion() {
        return id_evaluacion;
    }

    public void setId_evaluacion(int id_evaluacion) {
        this.id_evaluacion = id_evaluacion;
    }

    public double getNota() {
        return nota;
    }

    public void setNota(double nota) {
        this.nota = nota;
    }
    
    
    
}
