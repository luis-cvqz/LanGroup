package uv.fei.langroup.modelo.POJO;

public class ArchivoMultimedia {
    private String id;
    private String idPublicacion;
    private String nombre;
    private String mime;
    private int tamanio;
    private boolean enBaseDatos;
    private byte[] archivo;

    public ArchivoMultimedia(String id, String idPublicacion, String nombre, String mime, int tamanio, boolean enBaseDatos, byte[] archivo) {
        this.id = id;
        this.idPublicacion = idPublicacion;
        this.nombre = nombre;
        this.mime = mime;
        this.tamanio = tamanio;
        this.enBaseDatos = enBaseDatos;
        this.archivo = archivo;
    }

    public ArchivoMultimedia() {
    }

    public String getIdArchivoPublicacion() {
        return id;
    }

    public void setIdArchivoPublicacion(String id) {
        this.id = id;
    }

    public String getIdPublicacion() {
        return idPublicacion;
    }

    public void setIdPublicacion(String idPublicacion) {
        this.idPublicacion = idPublicacion;
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

    public int getTamanio() {
        return tamanio;
    }

    public void setTamanio(int tamanio) {
        this.tamanio = tamanio;
    }

    public boolean isEnBaseDatos() {
        return enBaseDatos;
    }

    public void setEnBaseDatos(boolean enBaseDatos) {
        this.enBaseDatos = enBaseDatos;
    }

    public byte[] getArchivo() {
        return archivo;
    }

    public void setArchivo(byte[] archivo) {
        this.archivo = archivo;
    }
}