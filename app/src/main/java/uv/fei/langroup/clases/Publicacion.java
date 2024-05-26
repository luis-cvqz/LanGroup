package uv.fei.langroup.clases;

import java.sql.Date;

public class Publicacion {
    private String idPublicacion;
    private String idUsuario;
    private String titulo;
    private String descripcion;
    private String rutaArchivo;
    private Date fecha;

    public Publicacion(String idPublicacion, String idUsuario, String titulo, String descripcion, String rutaArchivo, Date fecha) {
        this.idPublicacion = idPublicacion;
        this.idUsuario = idUsuario;
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.rutaArchivo = rutaArchivo;
        this.fecha = fecha;
    }

    public String getIdPublicacion() {
        return idPublicacion;
    }

    public void setIdPublicacion(String idPublicacion) {
        this.idPublicacion = idPublicacion;
    }

    public String getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(String idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getRutaArchivo() {
        return rutaArchivo;
    }

    public void setRutaArchivo(String rutaArchivo) {
        this.rutaArchivo = rutaArchivo;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }
}
