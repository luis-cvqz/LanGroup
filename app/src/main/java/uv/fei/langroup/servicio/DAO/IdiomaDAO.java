package uv.fei.langroup.servicio.DAO;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import uv.fei.langroup.modelo.POJO.Idioma;
import uv.fei.langroup.servicio.servicios.APIClient;
import uv.fei.langroup.servicio.servicios.IdiomaServicio;

public class IdiomaDAO {

    private IdiomaServicio idiomaServicio;

    public IdiomaDAO() {
        idiomaServicio = APIClient.iniciarAPI().create(IdiomaServicio.class);
    }

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

    public LiveData<Idioma> obtenerIdiomaPorId(String idiomaId) {
        final MutableLiveData<Idioma> data = new MutableLiveData<>();

        // Añadir logging para depuración
        Log.d("IdiomaDAO", "obtenerIdiomaPorId: idiomaId = " + idiomaId);

        if (idiomaId != null) {
            idiomaServicio.obtenerIdiomaPorId(idiomaId).enqueue(new Callback<Idioma>() {
                @Override
                public void onResponse(Call<Idioma> call, Response<Idioma> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        data.setValue(response.body());
                    } else {
                        data.setValue(null);
                    }
                }

                @Override
                public void onFailure(Call<Idioma> call, Throwable t) {
                    Log.e("IdiomaDAO", "Error al obtener idioma", t);
                    data.setValue(null);
                }
            });
        } else {
            data.setValue(null);
        }

        return data;
    }
}
