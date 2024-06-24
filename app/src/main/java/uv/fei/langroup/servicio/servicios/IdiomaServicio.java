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
import uv.fei.langroup.modelo.POJO.Idioma;

public interface IdiomaServicio {

    @GET("idiomas")
    Call<ArrayList<Idioma>> obtenerIdiomasPorColaborador(@Query("colaboradorid") String colaboradorId);
    @GET("idiomas")
    Call<ArrayList<Idioma>> obtenerIdiomas();

    @GET("idiomas/{id}")
    Call<Idioma> obtenerIdiomaPorId(@Path("id") String idiomaId);

    @POST("idiomas/colaboradores")
    Call<Void> agregarIdiomaColaborador(@Body Map<String, Object> body);

    @POST("idiomas")
    Call<Idioma> crearIdioma(@Body Idioma idioma);

    @PUT("idiomas/{id}")
    Call<Idioma> actualizarIdioma(@Path("id") String idiomaId, @Body Idioma idioma);

    @DELETE("idiomas/{id}")
    Call<Void> eliminarIdioma(@Path("id") String idiomaId);
}
