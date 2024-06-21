package uv.fei.langroup.modelo.POJO;

public class Solicitud {
    private String solicitudId;
    private String contenido;
    private String motivo;
    private byte[] constancia;
    private String nombrearchivo;
    private String estado;
    private String colaboradorId;
    private String idiomaId;
    private Colaborador colaborador;
    private Idioma idioma;

    public Solicitud() {
    }

    public Solicitud(String solicitudId, String contenido, String motivo, byte[] constancia, String nombrearchivo, String estado, String colaboradorId, String idiomaId, Colaborador colaborador, Idioma idioma) {
        this.solicitudId = solicitudId;
        this.contenido = contenido;
        this.motivo = motivo;
        this.constancia = constancia;
        this.nombrearchivo = nombrearchivo;
        this.estado = estado;
        this.colaboradorId = colaboradorId;
        this.idiomaId = idiomaId;
        this.colaborador = colaborador;
        this.idioma = idioma;
    }

    public String getSolicitudId() {
        return solicitudId;
    }

    public void setSolicitudId(String solicitudId) {
        this.solicitudId = solicitudId;
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
        return nombrearchivo;
    }

    public void setNombreArchivo(String nombrearchivo) {
        this.nombrearchivo = nombrearchivo;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getColaboradorId() {
        return colaboradorId;
    }

    public void setColaboradorId(String colaboradorId) {
        this.colaboradorId = colaboradorId;
    }

    public String getIdiomaId() {
        return idiomaId;
    }

    public void setIdiomaId(String idiomaId) {
        this.idiomaId = idiomaId;
    }

    public Colaborador getColaborador() {
        return colaborador;
    }

    public void setColaborador(Colaborador colaborador) {
        this.colaborador = colaborador;
    }

    public Idioma getIdioma() {
        return idioma;
    }

    public void setIdioma(Idioma idioma) {
        this.idioma = idioma;
    }
}