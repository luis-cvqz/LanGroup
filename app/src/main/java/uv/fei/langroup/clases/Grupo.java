package uv.fei.langroup.clases;

public class Grupo {
    private String claveGrupo;
    private String nombre;
    private String descripcion;
    private String icono;

    public Grupo(String claveGrupo, String nombre, String descripcion, String icono) {
        this.claveGrupo = claveGrupo;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.icono = icono;
    }

    public String getClaveGrupo() {
        return claveGrupo;
    }

    public void setClaveGrupo(String claveGrupo) {
        this.claveGrupo = claveGrupo;
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
}
