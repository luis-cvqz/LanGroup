package uv.fei.langroup.Servicios.Colaborador;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import uv.fei.langroup.clases.Colaborador;

public interface ColaboradorServicio {

    @POST("colaboradores")
    Call<Colaborador> crearCuenta(@Body Map<String, Object> credentials);
}
