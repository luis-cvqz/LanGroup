package uv.fei.langroup.clases;

public class ArchivoMultimedia {
    private String idArchivoPublicacion;
    private String idPublicacion;
    private String idSolicitud;
    private String idTipoArchivo;
    private String rutaArchivo;
    private String mime;

    public ArchivoMultimedia(String idArchivoPublicacion, String idPublicacion, String idSolicitud, String idTipoArchivo, String rutaArchivo, String mime) {
        this.idArchivoPublicacion = idArchivoPublicacion;
        this.idPublicacion = idPublicacion;
        this.idSolicitud = idSolicitud;
        this.idTipoArchivo = idTipoArchivo;
        this.rutaArchivo = rutaArchivo;
        this.mime = mime;
    }

    public String getIdArchivoPublicacion() {
        return idArchivoPublicacion;
    }

    public void setIdArchivoPublicacion(String idArchivoPublicacion) {
        this.idArchivoPublicacion = idArchivoPublicacion;
    }

    public String getIdPublicacion() {
        return idPublicacion;
    }

    public void setIdPublicacion(String idPublicacion) {
        this.idPublicacion = idPublicacion;
    }

    public String getIdSolicitud() {
        return idSolicitud;
    }

    public void setIdSolicitud(String idSolicitud) {
        this.idSolicitud = idSolicitud;
    }

    public String getIdTipoArchivo() {
        return idTipoArchivo;
    }

    public void setIdTipoArchivo(String idTipoArchivo) {
        this.idTipoArchivo = idTipoArchivo;
    }

    public String getRutaArchivo() {
        return rutaArchivo;
    }

    public void setRutaArchivo(String rutaArchivo) {
        this.rutaArchivo = rutaArchivo;
    }

    public String getMime() {
        return mime;
    }

    public void setMime(String mime) {
        this.mime = mime;
    }
}
