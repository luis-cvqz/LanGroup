package uv.fei.langroup.modelo.POJO;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class Colaborador implements Parcelable {
    private String colaboradorId;
    private String usuario;
    private String correo;
    private String contrasenia;
    private String nombre;
    private String apellido;
    private String descripcion;
    private String icono;
    private String rol;
    private String rolid;

    public Colaborador() {
    }

    public Colaborador(String colaboradorId, String usuario, String correo, String contrasenia, String nombre, String apellido, String descripcion, String icono, String rol, String rolid) {
        this.colaboradorId = colaboradorId;
        this.usuario = usuario;
        this.correo = correo;
        this.contrasenia = contrasenia;
        this.nombre = nombre;
        this.apellido = apellido;
        this.descripcion = descripcion;
        this.icono = icono;
        this.rol = rol;
        this.rolid = rolid;
    }

    public Colaborador(String colaboradorId, String usuario, String correo, String contrasenia, String nombre, String apellido, String descripcion, String icono, String rol) {
        this.colaboradorId = colaboradorId;
        this.usuario = usuario;
        this.correo = correo;
        this.contrasenia = contrasenia;
        this.nombre = nombre;
        this.apellido = apellido;
        this.descripcion = descripcion;
        this.icono = icono;
        this.rol = rol;
    }

    public String getColaboradorId() {
        return colaboradorId;
    }

    public void setColaboradorId(String colaboradorId) {
        this.colaboradorId = colaboradorId;
    }

    public String getRolid() {
        return rolid;
    }

    public void setRolid(String rolid) {
        this.rolid = rolid;
    }
    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getContrasenia() {
        return contrasenia;
    }

    public void setContrasenia(String contrasenia) {
        this.contrasenia = contrasenia;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
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

    public String getRol() {
        return rol;
    }

    public void setRol(String Rol) {
        this.rol = Rol;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeString(usuario);
        dest.writeString(correo);
        dest.writeString(contrasenia);
        dest.writeString(nombre);
        dest.writeString(apellido);
        dest.writeString(descripcion);
        dest.writeString(rol);
        dest.writeString(icono);
    }

    protected Colaborador(Parcel in){
        usuario = in.readString();
        correo = in.readString();
        contrasenia = in.readString();
        nombre = in.readString();
        apellido = in.readString();
        descripcion = in.readString();
        rol = in.readString();
        icono = in.readString();
    }

    public static final Creator<Colaborador> CREATOR = new Creator<Colaborador>() {
        @Override
        public Colaborador createFromParcel(Parcel in) {
            return new Colaborador(in);
        }

        @Override
        public Colaborador[] newArray(int size) {
            return new Colaborador[size];
        }
    };

    @NonNull
    @Override
    public String toString() {
        return this.usuario + " | " + this.correo;
    }
}
