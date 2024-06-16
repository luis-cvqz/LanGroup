package uv.fei.langroup.Servicios.Auth;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import uv.fei.langroup.clases.Auth;

public interface AuthServicio {

    @POST("auth")
    Call<Auth> iniciarSesion(@Body Map<String, String> credentials);
}
