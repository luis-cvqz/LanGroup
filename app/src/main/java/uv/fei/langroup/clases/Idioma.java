package uv.fei.langroup.clases;

public class Idioma {
    private String idIdioma;
    private String nombre;

    public Idioma(String idIdioma, String nombre) {
        this.idIdioma = idIdioma;
        this.nombre = nombre;
    }

    public String getIdIdioma() {
        return idIdioma;
    }

    public void setIdIdioma(String idIdioma) {
        this.idIdioma = idIdioma;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
