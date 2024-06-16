package uv.fei.langroup.clases;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import com.google.gson.annotations.SerializedName;

public class Auth implements Parcelable {

    @SerializedName("correo")
    private String correo;

    @SerializedName("contrasenia")
    private String contrasenia;

    public Auth() {
    }

    public Auth(String correo, String contrasenia) {
        this.correo = correo;
        this.contrasenia = contrasenia;
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

    @Override
    public int describeContents() {
        return 0;
    }

    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeString(correo);
        dest.writeString(contrasenia);
    }

    protected Auth(Parcel in){
        correo = in.readString();
        contrasenia = in.readString();
    }

    public static final Creator<Auth> CREATOR = new Creator<Auth>() {
        @Override
        public Auth createFromParcel(Parcel in) {
            return new Auth(in);
        }

        @Override
        public Auth[] newArray(int size) {
            return new Auth[size];
        }
    };
}
