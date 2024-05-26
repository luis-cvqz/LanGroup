package uv.fei.langroup.clases;

import java.sql.Date;

public class Interaccion {
    private String idInteraccion;
    private String idUsuario;
    private String idPublicacion;
    private String comentario;
    private int valoracion;
    private Date fecha;

    public Interaccion(String idInteraccion, String idUsuario, String idPublicacion, String comentario, int valoracion, Date fecha) {
        this.idInteraccion = idInteraccion;
        this.idUsuario = idUsuario;
        this.idPublicacion = idPublicacion;
        this.comentario = comentario;
        this.valoracion = valoracion;
        this.fecha = fecha;
    }

    public String getIdInteraccion() {
        return idInteraccion;
    }

    public void setIdInteraccion(String idInteraccion) {
        this.idInteraccion = idInteraccion;
    }

    public String getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(String idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getIdPublicacion() {
        return idPublicacion;
    }

    public void setIdPublicacion(String idPublicacion) {
        this.idPublicacion = idPublicacion;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public int getValoracion() {
        return valoracion;
    }

    public void setValoracion(int valoracion) {
        this.valoracion = valoracion;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }
}
