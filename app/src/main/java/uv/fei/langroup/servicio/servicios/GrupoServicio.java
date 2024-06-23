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
import retrofit2.http.Query;
import uv.fei.langroup.modelo.POJO.Grupo;
import uv.fei.langroup.modelo.POJO.Idioma;

public interface GrupoServicio {

    @GET("grupos")
    Call<ArrayList<Grupo>> obtenerGrupos();

    @GET("grupos")
    Call<ArrayList<Grupo>> obtenerGruposPorRolColaborador(@Query("colaboradorid") String colaboradorId, @Query("rol") String rolNombre);

    @GET("grupos")
    Call<ArrayList<Grupo>> obtenerGruposPorColaborador(@Query("colaboradorid") String colaboradorId);

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

    @POST("grupos/colaboradores")
    Call<Void> unirseAGrupo(@Body Map<String, Object> data);
}
