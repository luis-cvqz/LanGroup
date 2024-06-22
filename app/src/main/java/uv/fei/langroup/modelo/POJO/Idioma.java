package uv.fei.langroup.modelo.POJO;

import androidx.annotation.NonNull;

public class Idioma {
    private String idiomaId;
    private String nombre;

    public Idioma(String idiomaId, String nombre) {
        this.idiomaId = idiomaId;
        this.nombre = nombre;
    }

    public String getIdiomaId() {
        return idiomaId;
    }

    public void setIdiomaId(String idiomaId) {
        this.idiomaId = idiomaId;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @NonNull
    @Override
    public String toString() {
        return this.nombre;
    }
}
