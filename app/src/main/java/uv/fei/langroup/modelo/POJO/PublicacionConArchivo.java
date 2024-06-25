package uv.fei.langroup.modelo.POJO;

import java.io.File;

public class PublicacionConArchivo {
    private Publicacion nuevaPublicacion;
    private String id;
    private String publicacionid;
    private String nombre;
    private String mime;
    private String titulo;
    private String descripcion;
    private String grupoid;
    private String colaboradorid;
    private File archivo;

    public PublicacionConArchivo(String titulo, String descripcion, String grupoid, String colaboradorid, File archivo) {
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.grupoid = grupoid;
        this.colaboradorid = colaboradorid;
        this.archivo = archivo;
    }

    public Publicacion getNuevaPublicacion() {
        return nuevaPublicacion;
    }

    public void setNuevaPublicacion(Publicacion nuevaPublicacion) {
        this.nuevaPublicacion = nuevaPublicacion;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPublicacionid() {
        return publicacionid;
    }

    public void setPublicacionid(String publicacionid) {
        this.publicacionid = publicacionid;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getMime() {
        return mime;
    }

    public void setMime(String mime) {
        this.mime = mime;
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

    public String getGrupoid() {
        return grupoid;
    }

    public void setGrupoid(String grupoid) {
        this.grupoid = grupoid;
    }

    public String getColaboradorid() {
        return colaboradorid;
    }

    public void setColaboradorid(String colaboradorid) {
        this.colaboradorid = colaboradorid;
    }

    public File getArchivo() {
        return archivo;
    }

    public void setArchivo(File archivo) {
        this.archivo = archivo;
    }
}
