package uv.fei.langroup.POJO;

import java.sql.Date;

public class Interaccion {
    private String id;
    private int valoracion;
    private String comentario;
    private Date fecha;
    private String idColaborador;
    private String idPublicacion;

    public Interaccion(String id, int valoracion, String comentario, Date fecha, String idColaborador, String idPublicacion) {
        this.id = id;
        this.valoracion = valoracion;
        this.comentario = comentario;
        this.fecha = fecha;
        this.idColaborador = idColaborador;
        this.idPublicacion = idPublicacion;
    }

    public Interaccion() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getValoracion() {
        return valoracion;
    }

    public void setValoracion(int valoracion) {
        this.valoracion = valoracion;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getIdColaborador() {
        return idColaborador;
    }

    public void setIdColaborador(String idColaborador) {
        this.idColaborador = idColaborador;
    }

    public String getIdPublicacion() {
        return idPublicacion;
    }

    public void setIdPublicacion(String idPublicacion) {
        this.idPublicacion = idPublicacion;
    }
}