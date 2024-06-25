package uv.fei.langroup.modelo.POJO;

import java.util.Date;
import java.util.Objects;

public class Interaccion {
    private String id;
    private int valoracion;
    private String comentario;
    private Date fecha;
    private String colaboradorid;
    private String publicacionid;

    private Colaborador colaborador;

    public Interaccion(String id, int valoracion, String comentario, Date fecha, String idColaborador, String idPublicacion) {
        this.id = id;
        this.valoracion = valoracion;
        this.comentario = comentario;
        this.fecha = fecha;
        this.colaboradorid = idColaborador;
        this.publicacionid = idPublicacion;
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

    public String getColaboradorid() {
        return colaboradorid;
    }

    public void setColaboradorid(String colaboradorid) {
        this.colaboradorid = colaboradorid;
    }

    public String getPublicacionid() {
        return publicacionid;
    }

    public void setPublicacionid(String publicacionid) {
        this.publicacionid = publicacionid;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Interaccion)) return false;
        Interaccion that = (Interaccion) o;
        return getValoracion() == that.getValoracion() && Objects.equals(getId(), that.getId()) && Objects.equals(getComentario(), that.getComentario()) && Objects.equals(getFecha(), that.getFecha()) && Objects.equals(getColaboradorid(), that.getColaboradorid()) && Objects.equals(getPublicacionid(), that.getPublicacionid());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getValoracion(), getComentario(), getFecha(), getColaboradorid(), getPublicacionid());
    }

    public Colaborador getColaborador() {
        return colaborador;
    }

    public void setColaborador(Colaborador colaborador) {
        this.colaborador = colaborador;
    }
}