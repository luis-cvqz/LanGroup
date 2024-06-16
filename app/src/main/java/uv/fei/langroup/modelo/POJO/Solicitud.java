package uv.fei.langroup.modelo.POJO;

public class Solicitud {
    private String id;
    private String contenido;
    private String motivo;
    private byte[] constancia;
    private String nombreArchivo;
    private String estado;
    private String idColaborador;
    private String idIdioma;

    public Solicitud(String id, String contenido, String motivo, byte[] constancia, String nombreArchivo, String estado, String idColaborador, String idIdioma) {
        this.id = id;
        this.contenido = contenido;
        this.motivo = motivo;
        this.constancia = constancia;
        this.nombreArchivo = nombreArchivo;
        this.estado = estado;
        this.idColaborador = idColaborador;
        this.idIdioma = idIdioma;
    }

    public Solicitud() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getContenido() {
        return contenido;
    }

    public void setContenido(String contenido) {
        this.contenido = contenido;
    }

    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }

    public byte[] getConstancia() {
        return constancia;
    }

    public void setConstancia(byte[] constancia) {
        this.constancia = constancia;
    }

    public String getNombreArchivo() {
        return nombreArchivo;
    }

    public void setNombreArchivo(String nombreArchivo) {
        this.nombreArchivo = nombreArchivo;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getIdColaborador() {
        return idColaborador;
    }

    public void setIdColaborador(String idColaborador) {
        this.idColaborador = idColaborador;
    }

    public String getIdIdioma() {
        return idIdioma;
    }

    public void setIdIdioma(String idIdioma) {
        this.idIdioma = idIdioma;
    }
}