package uv.fei.langroup.servicio.servicios;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;
import uv.fei.langroup.modelo.POJO.Colaborador;

public interface ColaboradorServicio {

    @POST("colaboradores")
    Call<Colaborador> crearCuenta(@Body Colaborador colaborador);

    @GET("colaboradores/{correo}")
    Call<Colaborador> obtenerColaboradorPorCorreo(@Path("correo") String correo);

    @GET("colaboradores")
    Call<ArrayList<Colaborador>> obtenerColaboradoresPorRol(@Query("rol") String rol);

    @PUT("colaboradores/{id}")
    Call<Colaborador> actualizarColaborador(@Path("id") String colaboradorid, @Body Colaborador colaborador);

    @PUT("colaboradores/{colaboradorid}/rol")
    Call<Colaborador> actualizarRolDeColaborador(@Path("colaboradorid") String colaboradorid, @Body Colaborador colaborador);
}