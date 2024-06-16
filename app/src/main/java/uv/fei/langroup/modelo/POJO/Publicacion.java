package uv.fei.langroup.modelo.POJO;

import java.sql.Date;

public class Publicacion {
    private String id;
    private String titulo;
    private String descripcion;
    private Date fecha;
    private String idColaborador;
    private String idGrupo;

    public Publicacion(String id, String titulo, String descripcion, Date fecha, String idColaborador, String idGrupo) {
        this.id = id;
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.fecha = fecha;
        this.idColaborador = idColaborador;
        this.idGrupo = idGrupo;
    }

    public Publicacion() {
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

    public String getIdColaborador() {
        return idColaborador;
    }

    public void setIdColaborador(String idColaborador) {
        this.idColaborador = idColaborador;
    }

    public String getIdGrupo() {
        return idGrupo;
    }

    public void setIdGrupo(String idGrupo) {
        this.idGrupo = idGrupo;
    }
}
