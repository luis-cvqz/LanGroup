package uv.fei.langroup.servicio.servicios;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import uv.fei.langroup.modelo.POJO.Interaccion;

public interface InteraccionServicio {
    @GET("interacciones/{publicacion}")
    Call<ArrayList<Interaccion>> obtenerInteraccionesDePublicacion(@Path("publicacion") String publicacionId);

    @POST("interacciones")
    Call<Void> crearInteraccion(@Body Interaccion interaccion);

    @PUT("interacciones/{id}")
    Call<Interaccion> actualizarInteraccion(@Path("id") String interaccionId, @Body Interaccion interaccion);

    @DELETE("interacciones/{id}")
    Call<Void> eliminarInteraccion(@Path("id") String interaccionId);
}
