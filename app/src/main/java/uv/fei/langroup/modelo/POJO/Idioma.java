package uv.fei.langroup.modelo.POJO;

public class Idioma {
    private String id;
    private String nombre;

    public Idioma(String id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }

    public String getIdIdioma() {
        return id;
    }

    public void setIdIdioma(String idIdioma) {
        this.id = idIdioma;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
