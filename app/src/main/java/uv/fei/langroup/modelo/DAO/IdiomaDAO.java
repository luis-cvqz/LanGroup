package uv.fei.langroup.modelo.DAO;

import android.util.Log;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import uv.fei.langroup.modelo.POJO.Idioma;
import uv.fei.langroup.modelo.servicios.APIClient;
import uv.fei.langroup.modelo.servicios.IdiomaServicio;

public class IdiomaDAO {
    public static void obtenerIdiomas(final Callback<ArrayList<Idioma>> callback){
        Retrofit retrofit = APIClient.iniciarAPI();
        IdiomaServicio idiomaServicio = retrofit.create(IdiomaServicio.class);

        Call<ArrayList<Idioma>> call = idiomaServicio.obtenerIdiomas();

        call.enqueue(new Callback<ArrayList<Idioma>>() {
            @Override
            public void onResponse(Call<ArrayList<Idioma>> call, Response<ArrayList<Idioma>> response) {
                if(response.isSuccessful()){
                    ArrayList<Idioma> roles = response.body();
                    callback.onResponse(call, Response.success(roles));
                }else{
                    callback.onFailure(call, new Throwable("Error en la respuesta: " + response.code()));
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Idioma>> call, Throwable t) {
                Log.d("Rol", "Error en la conexión: " + t.getMessage());
                callback.onFailure(call, t);
            }
        });
    }
}
