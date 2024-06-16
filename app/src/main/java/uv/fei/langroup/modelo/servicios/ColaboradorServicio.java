package uv.fei.langroup.modelo.servicios;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import uv.fei.langroup.modelo.POJO.Colaborador;

public interface ColaboradorServicio {

    @POST("colaboradores")
    Call<Colaborador> crearCuenta(@Body Map<String, Object> credentials);
}