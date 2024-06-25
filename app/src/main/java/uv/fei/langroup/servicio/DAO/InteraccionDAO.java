package uv.fei.langroup.servicio.DAO;

import android.util.Log;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import uv.fei.langroup.modelo.POJO.Interaccion;
import uv.fei.langroup.modelo.POJO.Publicacion;
import uv.fei.langroup.servicio.servicios.APIClient;
import uv.fei.langroup.servicio.servicios.InteraccionServicio;

public class InteraccionDAO {
    public static void obtenerInteraccionesDePublicacion(String publicacionId, Callback<ArrayList<Interaccion>> callback) {
        Retrofit retrofit = APIClient.iniciarAPI();
        InteraccionServicio interaccionServicio = retrofit.create(InteraccionServicio.class);

        Call<ArrayList<Interaccion>> call = interaccionServicio.obtenerInteraccionesDePublicacion(publicacionId);

        call.enqueue(new Callback<ArrayList<Interaccion>>() {
            @Override
            public void onResponse(Call<ArrayList<Interaccion>> call, Response<ArrayList<Interaccion>> response) {
                if(response.isSuccessful()){
                    ArrayList<Interaccion> interacciones = response.body();
                    callback.onResponse(call, Response.success(interacciones));
                }else{
                    callback.onFailure(call, new Throwable("Error en la respuesta: " + response.code()));
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Interaccion>> call, Throwable t) {
                Log.d("InteraccionDAO", "Error en la conexión: " + t.getMessage());
                callback.onFailure(call, t);
            }
        });
    }

    public static void crearInteraccion(Interaccion interaccion, Callback<Void> callback) {
        Retrofit retrofit = APIClient.iniciarAPI();
        InteraccionServicio interaccionServicio = retrofit.create(InteraccionServicio.class);

        Call<Void> call =  interaccionServicio.crearInteraccion(interaccion);

        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    callback.onResponse(call, response);
                } else {
                    callback.onFailure(call, new Throwable("Error en la respuesta: " + response.code()));
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.d("InteraccionDAO", "Error en la conexión: " + t.getMessage());
                callback.onFailure(call, t);
            }
        });
    }
}
