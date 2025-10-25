package Datos;

public class DDetalle_evaluacion {

        private int idDetalle;
        private int idNota;
        private String nombreEvaluacion;
        private double peso;
        private double nota;
        
        public DDetalle_evaluacion() {
        }

        public DDetalle_evaluacion(int idDetalle, int idNota, String nombreEvaluacion, double peso, double nota) {
            this.idDetalle = idDetalle;
            this.idNota = idNota;
            this.nombreEvaluacion = nombreEvaluacion;
            this.peso = peso;
            this.nota = nota;
        }

        public int getIdDetalle() {
            return idDetalle;
        }

        public void setIdDetalle(int idDetalle) {
            this.idDetalle = idDetalle;
        }

        public int getIdNota() {
            return idNota;
        }

        public void setIdNota(int idNota) {
            this.idNota = idNota;
        }

        public String getNombreEvaluacion() {
            return nombreEvaluacion;
        }

        public void setNombreEvaluacion(String nombreEvaluacion) {
            this.nombreEvaluacion = nombreEvaluacion;
        }

        public double getPeso() {
            return peso;
        }

        public void setPeso(double peso) {
            this.peso = peso;
        }

        public double getNota() {
            return nota;
        }

        public void setNota(double nota) {
            this.nota = nota;
        }

        @Override
        public String toString() {
            return nombreEvaluacion + " (" + peso + "%)";
        }

}
