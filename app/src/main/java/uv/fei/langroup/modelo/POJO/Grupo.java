package uv.fei.langroup.modelo.POJO;

public class Grupo {
    private String grupoId;
    private String nombre;
    private String descripcion;
    private String icono;
    private String idIdioma;
    private Idioma idioma;

    public Grupo(String grupoId, String nombre, String descripcion, String icono, String idIdioma) {
        this.grupoId = grupoId;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.icono = icono;
        this.idIdioma = idIdioma;
    }

    public Grupo() {
    }

    public Idioma getIdioma() {
        return idioma;
    }

    public void setIdioma(Idioma idioma) {
        this.idioma = idioma;
    }
    public String getId() {
        return grupoId;
    }

    public void setId(String id) {
        this.grupoId = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
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

    public String getIdIdioma() {
        return idIdioma;
    }

    public void setIdIdioma(String idIdioma) {
        this.idIdioma = idIdioma;
    }
}
