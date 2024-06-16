package uv.fei.langroup.modelo.servicios;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import uv.fei.langroup.modelo.POJO.Idioma;

public interface IdiomaServicio {
    @GET("idiomas")
    Call<ArrayList<Idioma>> obtenerIdiomas();

    @GET("idiomas/{id}")
    Call<Idioma> obtenerIdiomaPorId(@Path("id") String idiomaId);

    @POST("idiomas")
    Call<Idioma> crearIdioma(@Body Idioma idioma);

    @PUT("idiomas/{id}")
    Call<Idioma> actualizarIdioma(@Path("id") String idiomaId, @Body Idioma idioma);

    @DELETE("idiomas/{id}")
    Call<Void> eliminarIdioma(@Path("id") String idiomaId);
}
