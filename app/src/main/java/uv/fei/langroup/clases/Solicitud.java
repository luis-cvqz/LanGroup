package uv.fei.langroup.clases;

public class Solicitud {
    private String idSolicitud;
    private String idIdioma;
    private String idUsuario;
    private String rutaConstancia;
    private String profesion;
    private String motivo;
    private String contenido;
    private String estado;

    public Solicitud(String idSolicitud, String idIdioma, String idUsuario, String rutaConstancia, String profesion, String motivo, String contenido, String estado) {
        this.idSolicitud = idSolicitud;
        this.idIdioma = idIdioma;
        this.idUsuario = idUsuario;
        this.rutaConstancia = rutaConstancia;
        this.profesion = profesion;
        this.motivo = motivo;
        this.contenido = contenido;
        this.estado = estado;
    }

    public String getIdSolicitud() {
        return idSolicitud;
    }

    public void setIdSolicitud(String idSolicitud) {
        this.idSolicitud = idSolicitud;
    }

    public String getIdIdioma() {
        return idIdioma;
    }

    public void setIdIdioma(String idIdioma) {
        this.idIdioma = idIdioma;
    }

    public String getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(String idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getRutaConstancia() {
        return rutaConstancia;
    }

    public void setRutaConstancia(String rutaConstancia) {
        this.rutaConstancia = rutaConstancia;
    }

    public String getProfesion() {
        return profesion;
    }

    public void setProfesion(String profesion) {
        this.profesion = profesion;
    }

    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }

    public String getContenido() {
        return contenido;
    }

    public void setContenido(String contenido) {
        this.contenido = contenido;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}
