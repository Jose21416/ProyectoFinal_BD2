package Datos;

public class DUsuarios {
    
    private int id_usuario;
    private String usuario;
    private String nombre;
    private String correo;
    private String contrasena;
    private String telefono;
    private TipoUsuario tipoUsuario = TipoUsuario.ESTUDIANTE;
    private Estado estado = Estado.ACTIVO;

    public enum TipoUsuario {
        ESTUDIANTE, SUPERVISOR, ADMINISTRADOR
    }
    
    public enum Estado {
        ACTIVO, INACTIVO
    }
    
    public DUsuarios() {
    }

    public DUsuarios(int id_usuario, String usuario, String nombre, String correo, String contrasena, String telefono, TipoUsuario tipoUsuario, Estado estado) {
        this.id_usuario = id_usuario;
        this.usuario = usuario;
        this.nombre = nombre;
        this.correo = correo;
        this.contrasena = contrasena;
        this.telefono = telefono;
        this.tipoUsuario = tipoUsuario != null ? tipoUsuario : TipoUsuario.ESTUDIANTE;
        this.estado = estado != null ? estado : Estado.ACTIVO;
    }
    
    public DUsuarios(String usuario, String nombre, String correo, String contrasena, String telefono, TipoUsuario tipoUsuario, Estado estado) {
        this(0, usuario, nombre, correo, contrasena, telefono, tipoUsuario, estado);
    }

    public int getId_usuario() {
        return id_usuario;
    }

    public void setId_usuario(int id_usuario) {
        this.id_usuario = id_usuario;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public TipoUsuario getTipoUsuario() {
        return tipoUsuario;
    }

    public void setTipoUsuario(TipoUsuario tipoUsuario) {
        this.tipoUsuario = tipoUsuario;
    }

    public Estado getEstado() {
        return estado;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }
    
    public static String usuarioLogueado;
}
