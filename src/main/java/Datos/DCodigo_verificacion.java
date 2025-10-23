
package Datos;

import java.sql.Date;


public class DCodigo_verificacion {
    private int id_codigo;
    private int id_usuario;
    private String codigo;
    private Metodo metodo;
    private Date fecha_creacion; 
    private Date fecha_expiracion; 
    private int usado; 
    private int intentos;

    public DCodigo_verificacion() {
    }

    public DCodigo_verificacion(int id_codigo, int id_usuario, String codigo, Metodo metodo, Date fecha_creacion, Date fecha_expiracion, int usado, int intentos) {
        this.id_codigo = id_codigo;
        this.id_usuario = id_usuario;
        this.codigo = codigo;
        this.metodo = metodo;
        this.fecha_creacion = fecha_creacion;
        this.fecha_expiracion = fecha_expiracion;
        this.usado = usado;
        this.intentos = intentos;
    }
    
    public int getId_codigo() {
        return id_codigo;
    }

    public void setId_codigo(int id_codigo) {
        this.id_codigo = id_codigo;
    }

    public int getId_usuario() {
        return id_usuario;
    }

    public void setIdUsuario(int id_usuario) {
        this.id_usuario = id_usuario;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public Metodo getMetodo() {
        return metodo;
    }

    public void setMetodo(Metodo metodo) {
        this.metodo = metodo;
    }

    public Date getFecha_creacion() {
        return fecha_creacion;
    }

    public void setFecha_creacion(Date fecha_creacion) {
        this.fecha_creacion = fecha_creacion;
    }

    public Date getFecha_expiracion() {
        return fecha_expiracion;
    }

    public void setFecha_expiracion(Date fecha_expiracion) {
        this.fecha_expiracion = fecha_expiracion;
    }

    public int getUsado() {
        return usado;
    }

    public void setUsado(int usado) {
        this.usado = usado;
    }

    public int getIntentos() {
        return intentos;
    }

    public void setIntentos(int intentos) {
        this.intentos = intentos;
    }
    
    public enum Metodo{
        CORREO, SMS
    }
  
}
