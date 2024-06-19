package uv.fei.langroup.servicio.DAO;

import android.util.Log;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

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
}
