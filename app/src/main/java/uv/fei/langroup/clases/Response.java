package uv.fei.langroup.clases;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

public class Response implements Parcelable {

    @SerializedName("correo")
    private String correo;

    @SerializedName("usuario")
    private String usuario;

    @SerializedName("rol")
    private String rol;

    @SerializedName("jwt")
    private String jwt;

    @SerializedName("codigo")
    private int codigo;

    public Response() {
    }

    public Response(String correo, String usuario, String rol, String jwt, int codigo) {
        this.correo = correo;
        this.usuario = usuario;
        this.rol = rol;
        this.jwt = jwt;
        this.codigo = codigo;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    public String getJwt() {
        return jwt;
    }

    public void setJwt(String jwt) {
        this.jwt = jwt;
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeString(correo);
        dest.writeString(usuario);
        dest.writeString(rol);
        dest.writeString(jwt);
        dest.writeInt(codigo);
    }

    protected Response(Parcel in){
        correo = in.readString();
        usuario = in.readString();
        rol = in.readString();
        jwt = in.readString();
        codigo = in.readInt();
    }

    public static final Creator<Response> CREATOR = new Creator<Response>() {
        @Override
        public Response createFromParcel(Parcel in) {
            return new Response(in);
        }

        @Override
        public Response[] newArray(int size) {
            return new Response[size];
        }
    };
}
