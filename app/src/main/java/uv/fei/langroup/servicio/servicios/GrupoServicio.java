package uv.fei.langroup.servicio.servicios;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import uv.fei.langroup.modelo.POJO.Grupo;

public interface GrupoServicio {
    @GET("grupos/{id}")
    Call<Grupo> obtenerGrupoPorId(@Path("id") String grupoId);

    @GET("grupos/idioma/{idiomaNombre}")
    Call<ArrayList<Grupo>> obtenerGruposPorNombreIdioma(@Path("idiomaNombre") String idiomaNombre);

    @POST("grupos")
    Call<Grupo> crearGrupo(@Body Map<String, Object> grupo);

    @PUT("grupos/{id}")
    Call<Grupo> actualizarGrupo(@Path("id") String grupoId, @Body Grupo grupo);

    @DELETE("grupos/{id}")
    Call<Void> eliminarGrupo(@Path("id") String grupoId);
}
