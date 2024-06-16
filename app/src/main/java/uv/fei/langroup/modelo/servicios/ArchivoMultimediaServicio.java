package uv.fei.langroup.modelo.servicios;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import uv.fei.langroup.modelo.POJO.ArchivoMultimedia;

public interface ArchivoMultimediaServicio {
    @GET("archivosmultimedia")
    Call<List<ArchivoMultimedia>> obtenerArchivosMultimedia();

    @GET("archivosmultimedia/{id}")
    Call<ArchivoMultimedia> obtenerArchivoMultimediaPorId(@Path("id") String archivoMultimediaId);

    @GET("archivosmultimedia/{id}/detalle")
    Call<ArchivoMultimedia> obtenerDetallesArchivoMultimedia(@Path("id") String archivoMultimediaId);

    @POST("archivosmultimedia/videos")
    Call<ArchivoMultimedia> crearVideo(@Body ArchivoMultimedia video);

    @POST("archivosmultimedia")
    Call<ArchivoMultimedia> crearArchivoMultimedia(@Body ArchivoMultimedia archivoMultimedia);

    @DELETE("archivosmultimedia/{id}")
    Call<Void> eliminarArchivoMultimedia(@Path("id") String archivoMultimediaId);

    @DELETE("archivosmultimedia/videos/{id}")
    Call<Void> eliminarVideo(@Path("id") String videoId);
}
