package uv.fei.langroup.modelo.POJO;

public class Colaborador {
    private String id;
    private String usuario;
    private String correo;
    private String contrasenia;
    private String nombre;
    private String apellido;
    private String descripcion;
    private String icono;
    private String idRol;

    public Colaborador(String id, String usuario, String correo, String contrasenia, String nombre, String apellido, String descripcion, String icono, String idRol) {
        this.id = id;
        this.usuario = usuario;
        this.correo = correo;
        this.contrasenia = contrasenia;
        this.nombre = nombre;
        this.apellido = apellido;
        this.descripcion = descripcion;
        this.icono = icono;
        this.idRol = idRol;
    }

    public Colaborador() {
    }

    public String getIdUsuario() {
        return id;
    }

    public void setIdUsuario(String idUsuario) {
        this.id = idUsuario;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getContrasenia() {
        return contrasenia;
    }

    public void setContrasenia(String contrasenia) {
        this.contrasenia = contrasenia;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getIcono() {
        return icono;
    }

    public void setIcono(String icono) {
        this.icono = icono;
    }

    public String getIdRol() {
        return idRol;
    }

    public void setIdRol(String idRol) {
        this.idRol = idRol;
    }
}
