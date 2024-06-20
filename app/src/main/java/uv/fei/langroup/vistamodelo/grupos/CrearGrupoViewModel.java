package uv.fei.langroup.vistamodelo.grupos;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import uv.fei.langroup.modelo.POJO.Idioma;
import uv.fei.langroup.servicio.DAO.GrupoDAO;
import uv.fei.langroup.servicio.DAO.IdiomaDAO;

public class CrearGrupoViewModel extends ViewModel {
    private GrupoDAO grupoDAO;
    private IdiomaDAO idiomaDAO;
    private MutableLiveData<ArrayList<Idioma>> idiomas;

    public CrearGrupoViewModel() {
        grupoDAO = new GrupoDAO();
        idiomaDAO = new IdiomaDAO();
        idiomas = new MutableLiveData<>();
    }

    public LiveData<ArrayList<Idioma>> getIdiomas() { return idiomas; }

    public void fetchIdiomas() {
        IdiomaDAO.obtenerIdiomas(new Callback<ArrayList<Idioma>>() {
            @Override
            public void onResponse(Call<ArrayList<Idioma>> call, Response<ArrayList<Idioma>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    idiomas.setValue(response.body());
                } else {
                    Log.e("CrearGrupoFragment", "Error en la respuesta:" + response.code());
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Idioma>> call, Throwable t) {
                Log.e("CrearGrupoFragment", "Error en la conexión: " + t.getMessage());
            }
        });
    }
}
