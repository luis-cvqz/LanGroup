package uv.fei.langroup.modelo.POJO;

import java.util.Date;
import java.util.Objects;

public class Publicacion {
    private String id;
    private String titulo;
    private String descripcion;
    private Date fecha;
    private Colaborador colaborador;
    private Grupo grupo;

    public Publicacion() {
    }

    public Publicacion(String id, String titulo, String descripcion, Date fecha, Colaborador colaborador, Grupo grupo) {
        this.id = id;
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.fecha = fecha;
        this.colaborador = colaborador;
        this.grupo = grupo;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public Colaborador getColaborador() {
        return colaborador;
    }

    public void setColaborador(Colaborador colaborador) {
        this.colaborador = colaborador;
    }

    public Grupo getGrupo() {
        return grupo;
    }

    public void setGrupo(Grupo grupo) {
        this.grupo = grupo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Publicacion)) return false;
        Publicacion that = (Publicacion) o;
        return Objects.equals(getId(), that.getId()) && Objects.equals(getTitulo(), that.getTitulo()) && Objects.equals(getDescripcion(), that.getDescripcion()) && Objects.equals(getFecha(), that.getFecha()) && Objects.equals(getColaborador(), that.getColaborador()) && Objects.equals(getGrupo(), that.getGrupo());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getTitulo(), getDescripcion(), getFecha(), getColaborador(), getGrupo());
    }
}
