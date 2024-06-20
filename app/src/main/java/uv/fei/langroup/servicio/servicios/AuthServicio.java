package uv.fei.langroup.servicio.servicios;

import retrofit2.http.POST;
import retrofit2.Call;
import retrofit2.http.Body;
import uv.fei.langroup.modelo.POJO.Auth;
import uv.fei.langroup.modelo.POJO.JsonResponse;

public interface AuthServicio {
    @POST("auth")
    Call<JsonResponse> iniciarSesion(@Body Auth auth);
}
