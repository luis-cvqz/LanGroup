package uv.fei.langroup.servicio.DAO;

import android.util.Log;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import java.util.ArrayList;
import uv.fei.langroup.modelo.POJO.Grupo;
import uv.fei.langroup.servicio.servicios.APIClient;
import uv.fei.langroup.servicio.servicios.GrupoServicio;

public class GrupoDAO {
    public static void crearGrupo(Grupo nuevoGrupo, final Callback<Grupo> callback) {
        Retrofit retrofit = APIClient.iniciarAPI();
        GrupoServicio grupoServicio = retrofit.create(GrupoServicio.class);

        Map<String, Object> grupo = new HashMap<>();
        grupo.put("nombre", nuevoGrupo.getNombre());
        grupo.put("descripcion", nuevoGrupo.getDescripcion());
        grupo.put("icono", "icono_grupo_1.png");
        grupo.put("idiomaid",nuevoGrupo.getIdIdioma());
        Call<Grupo> call = grupoServicio.crearGrupo(grupo);

        call.enqueue(new Callback<Grupo>() {
            @Override
            public void onResponse(Call<Grupo> call, Response<Grupo> response) {
                if (response.isSuccessful()) {
                    Grupo grupo = response.body();
                    callback.onResponse(call, Response.success(grupo));
                } else {
                    callback.onFailure(call, new Throwable("Error en la respuesta: " + response.code()));
                }
            }

            @Override
            public void onFailure(Call<Grupo> call, Throwable t) {
                Log.d("crearGrupo", "Error en la conexion: " + t.getMessage());
                callback.onFailure(call, t);
            }
        });
    }

    private GrupoServicio grupoServicio;

    public GrupoDAO() {
        grupoServicio = APIClient.iniciarAPI().create(GrupoServicio.class);
    }

    public LiveData<ArrayList<Grupo>> obtenerGrupos() {
        final MutableLiveData<ArrayList<Grupo>> data = new MutableLiveData<>();
        grupoServicio.obtenerGrupos().enqueue(new Callback<ArrayList<Grupo>>() {
            @Override
            public void onResponse(Call<ArrayList<Grupo>> call, Response<ArrayList<Grupo>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    // Verificar los datos obtenidos
                    for (Grupo grupo : response.body()) {
                        Log.d("GrupoDAO", "Grupo: " + grupo.getNombre() + ", IdiomaId: " + grupo.getIdIdioma());
                    }
                    data.setValue(response.body());
                } else {
                    data.setValue(null);
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Grupo>> call, Throwable t) {
                Log.e("GrupoDAO", "Error al obtener grupos", t);
                data.setValue(null);
            }
        });
        return data;

    }
}