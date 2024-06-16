package uv.fei.langroup.modelo.servicios;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import uv.fei.langroup.modelo.POJO.Rol;

public interface RolServicio {
    @GET("roles/{id}")
    Call<Rol> obtenerRolPorId(@Path("id") String rolid);

    @GET("roles")
    Call<ArrayList<Rol>> obtenerRoles();
}
