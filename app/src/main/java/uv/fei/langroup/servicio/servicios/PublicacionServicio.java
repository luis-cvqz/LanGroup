package uv.fei.langroup.servicio.servicios;

import java.util.ArrayList;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;
import uv.fei.langroup.modelo.POJO.Publicacion;
import uv.fei.langroup.modelo.POJO.PublicacionConArchivo;

public interface PublicacionServicio {
    @GET("publicaciones")
    Call<ArrayList<Publicacion>> obtenerPublicacionesPorColaborador(@Query("colaborador") String colaboradorid);

    @GET("publicaciones")
    Call <ArrayList<Publicacion>> obtenerPublicacionesPorGrupoColaborador(@Query("grupo") String grupoid, @Query("colaborador") String colaboradorid);

    @GET("publicaciones")
    Call <ArrayList<Publicacion>> obtenerPublicacionesPorGrupo(@Query("grupo") String grupoid);

    @GET("publicaciones/{id}")
    Call<Publicacion> obtenerPublicacionPorId(@Path("id") String publicacionId);

    @POST("publicaciones")
    Call<Publicacion> crearPublicacion(@Body Publicacion publicacion);

    // @POST("publicaciones/imagenes")
    @Multipart
    @POST("publicaciones/imagenes")
    Call<PublicacionConArchivo> crearPublicacionConArchivo(
            @Part MultipartBody.Part archivoMultimedia,
            @Part("titulo") RequestBody titulo,
            @Part("descripcion") RequestBody descripcion,
            @Part("colaboradorid") RequestBody colaboradorid,
            @Part("grupoid") RequestBody grupoid);

    @PUT("publicaciones/{id}")
    Call<Publicacion> actualizarPublicacion(@Path("id") String publicacionId, @Body Publicacion publicacion);

    @DELETE("publicaciones/{id}")
    Call<Void> eliminarPublicacion(@Path("id") String publicacionId);
}
