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
import retrofit2.http.Part;
import retrofit2.http.Path;
import uv.fei.langroup.modelo.POJO.ArchivoMultimedia;

public interface ArchivoMultimediaServicio {
    @GET("archivosmultimedia")
    Call<ArrayList<ArchivoMultimedia>> obtenerArchivosMultimedia();

    @GET("archivosmultimedia/{id}")
    Call<ArchivoMultimedia> obtenerArchivoMultimediaPorId(@Path("id") String archivoMultimediaId);

    @GET("archivosmultimedia/{id}/detalle")
    Call<ArchivoMultimedia> obtenerDetallesArchivoMultimedia(@Path("id") String archivoMultimediaId);

    @POST("archivosmultimedia/videos")
    Call<ArchivoMultimedia> crearVideo(@Body ArchivoMultimedia video);

    @Multipart
    @POST("archivosmultimedia")
    Call<ArchivoMultimedia> crearArchivoMultimedia(
            @Part MultipartBody.Part archivoMultimedia,
            @Part("publicacionid") RequestBody publicacionid );

    @DELETE("archivosmultimedia/{id}")
    Call<Void> eliminarArchivoMultimedia(@Path("id") String archivoMultimediaId);

    @DELETE("archivosmultimedia/videos/{id}")
    Call<Void> eliminarVideo(@Path("id") String videoId);
}
