package uv.fei.langroup.modelo.servicios;

import java.util.ArrayList;
import java.util.List;

import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import uv.fei.langroup.modelo.POJO.Publicacion;
import retrofit2.Call;

public interface PublicacionServicio {
    @GET("publicaciones/colaborador?={colaboradorid}")
    Call<ArrayList<Publicacion>> obtenerPublicacionesPorColaborador(@Path("colaboradorid") String colaboradorid);

    @GET("publicaciones/{id}")
    Call<Publicacion> obtenerPublicacionPorId(@Path("id") String publicacionId);

    @POST("publicaciones")
    Call<Publicacion> crearPublicacion(@Body Publicacion publicacion);

    @PUT("publicaciones/{id}")
    Call<Publicacion> actualizarPublicacion(@Path("id") String publicacionId, @Body Publicacion publicacion);

    @DELETE("publicaciones/{id}")
    Call<Void> eliminarPublicacion(@Path("id") String publicacionId);
}
