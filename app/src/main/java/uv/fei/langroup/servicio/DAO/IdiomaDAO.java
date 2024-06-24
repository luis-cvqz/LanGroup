package uv.fei.langroup.servicio.DAO;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    public static void agregarIdiomaColaborador(String colaboradorId, List<String> idiomaIds, final Callback<Void> callback) {
        Retrofit retrofit = APIClient.iniciarAPI();
        IdiomaServicio idiomaServicio = retrofit.create(IdiomaServicio.class);

        // Log para verificar que colaboradorId se está pasando correctamente
        Log.d("IdiomaDAO", "Colaborador ID: " + colaboradorId);

        // Crear un HashMap para el cuerpo de la solicitud
        Map<String, Object> body = new HashMap<>();
        body.put("colaboradorId", colaboradorId);
        body.put("idiomaIds", idiomaIds);

        // Realizar la llamada al servicio
        Call<Void> call = idiomaServicio.agregarIdiomaColaborador(body);

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
                Log.d("IdiomaDAO", "Error en la conexión: " + t.getMessage());
                callback.onFailure(call, t);
            }
        });
    }

    public static void obtenerIdiomasPorColaborador(String colaboradorId, final Callback<ArrayList<Idioma>> callback) {
        Retrofit retrofit = APIClient.iniciarAPI();
        IdiomaServicio idiomaServicio = retrofit.create(IdiomaServicio.class);

        Call<ArrayList<Idioma>> call = idiomaServicio.obtenerIdiomasPorColaborador(colaboradorId);

        call.enqueue(new Callback<ArrayList<Idioma>>() {
            @Override
            public void onResponse(Call<ArrayList<Idioma>> call, Response<ArrayList<Idioma>> response) {
                if(response.isSuccessful()){
                    ArrayList<Idioma> idiomas = response.body();
                    callback.onResponse(call, Response.success(idiomas));
                }else{
                    callback.onFailure(call, new Throwable("Error en la respuesta: " + response.code()));
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Idioma>> call, Throwable t) {
                Log.d("IdiomaDAO", "Error en la conexión: " + t.getMessage());
                callback.onFailure(call, t);
            }
        });
    }
}
