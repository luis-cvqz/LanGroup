package uv.fei.langroup.servicio.DAO;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import uv.fei.langroup.modelo.POJO.Publicacion;
import uv.fei.langroup.servicio.servicios.APIClient;
import uv.fei.langroup.servicio.servicios.PublicacionServicio;

public class PublicacionDAO {

    public static void obtenerPublicacionesPorColaborador(String colaboradorId, Callback<ArrayList<Publicacion>> callback){
        Retrofit retrofit = APIClient.iniciarAPI();
        PublicacionServicio publicacionServicio = retrofit.create(PublicacionServicio.class);

        Call<ArrayList<Publicacion>> call = publicacionServicio.obtenerPublicacionesPorColaborador(colaboradorId);

        call.enqueue(new Callback<ArrayList<Publicacion>>() {
            @Override
            public void onResponse(Call<ArrayList<Publicacion>> call, Response<ArrayList<Publicacion>> response) {
                if(response.isSuccessful()){
                    ArrayList<Publicacion> publicaciones = response.body();
                    callback.onResponse(call, Response.success(publicaciones));
                }else{
                    callback.onFailure(call, new Throwable("Error en la respuesta: " + response.code()));
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Publicacion>> call, Throwable t) {
                Log.d("Rol", "Error en la conexión: " + t.getMessage());
                callback.onFailure(call, t);
            }
        });
    }

    public static void obtenerPublicacionesPorGrupoColaborador(String grupoId, String colaboradorId, Callback<ArrayList<Publicacion>> callback) {
        Retrofit retrofit = APIClient.iniciarAPI();
        PublicacionServicio publicacionServicio = retrofit.create(PublicacionServicio.class);

        Call<ArrayList<Publicacion>> call = publicacionServicio.obtenerPublicacionesPorGrupoColaborador(grupoId, colaboradorId);

        call.enqueue(new Callback<ArrayList<Publicacion>>() {
            @Override
            public void onResponse(Call<ArrayList<Publicacion>> call, Response<ArrayList<Publicacion>> response) {
                if(response.isSuccessful()){
                    ArrayList<Publicacion> publicaciones = response.body();
                    callback.onResponse(call, Response.success(publicaciones));
                }else{
                    callback.onFailure(call, new Throwable("Error en la respuesta: " + response.code()));
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Publicacion>> call, Throwable t) {
                Log.d("PublicacionDAO", "Error en la conexión: " + t.getMessage());
                callback.onFailure(call, t);
            }
        });
    }
}
