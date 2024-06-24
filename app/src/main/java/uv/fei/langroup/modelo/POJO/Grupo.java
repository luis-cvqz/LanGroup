package uv.fei.langroup.modelo.POJO;
import com.google.gson.annotations.SerializedName;

public class Grupo {
    @SerializedName("grupoId")
    private String grupoId;

    @SerializedName("nombre")
    private String nombre;

    @SerializedName("descripcion")
    private String descripcion;

    @SerializedName("icono")
    private String icono;

    @SerializedName("idiomaId")
    private String idIdioma;

    @SerializedName("idiomaid")
    private String idiomaid;

    private Idioma idioma;

    public Grupo(String grupoId, String nombre, String descripcion, String icono, String idIdioma) {
        this.grupoId = grupoId;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.icono = icono;
        this.idIdioma = idIdioma;
    }

    public Grupo() {}

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

    public String getIdiomaid() {
        return idiomaid;
    }

    public void setIdiomaid(String idiomaid) {
        this.idiomaid = idiomaid;
    }
}
