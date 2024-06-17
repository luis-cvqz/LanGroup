package uv.fei.langroup.servicio.DAO;

import android.util.Log;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import uv.fei.langroup.modelo.POJO.Rol;
import uv.fei.langroup.servicio.servicios.APIClient;
import uv.fei.langroup.servicio.servicios.RolServicio;

public class RolDAO {
    public static void obtenerRolPorId(String rolId, final Callback<Rol> callback){
        Retrofit retrofit = APIClient.iniciarAPI();
        RolServicio rolServicio = retrofit.create(RolServicio.class);

        Call<Rol> call = rolServicio.obtenerRolPorId(rolId);

        call.enqueue(new Callback<Rol>() {
            @Override
            public void onResponse(Call<Rol> call, Response<Rol> response) {
                if(response.isSuccessful()){
                    Rol rol = response.body();
                    callback.onResponse(call, Response.success(rol));
                }else{
                    callback.onFailure(call, new Throwable("Error en la respuesta: " + response.code()));
                }
            }

            @Override
            public void onFailure(Call<Rol> call, Throwable t) {
                Log.d("Rol", "Error en la conexión: " + t.getMessage());
                callback.onFailure(call, t);
            }
        });
    }

    public static void obtenerRoles(final Callback<ArrayList<Rol>> callback){
        Retrofit retrofit = APIClient.iniciarAPI();
        RolServicio rolServicio = retrofit.create(RolServicio.class);

        Call<ArrayList<Rol>> call = rolServicio.obtenerRoles();

        call.enqueue(new Callback<ArrayList<Rol>>() {
            @Override
            public void onResponse(Call<ArrayList<Rol>> call, Response<ArrayList<Rol>> response) {
                if(response.isSuccessful()){
                    ArrayList<Rol> roles = response.body();
                    callback.onResponse(call, Response.success(roles));
                }else{
                    callback.onFailure(call, new Throwable("Error en la respuesta: " + response.code()));
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Rol>> call, Throwable t) {
                Log.d("Rol", "Error en la conexión: " + t.getMessage());
                callback.onFailure(call, t);
            }
        });
    }
}
