package uv.fei.langroup.vistamodelo.idiomas;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import uv.fei.langroup.modelo.POJO.Idioma;
import uv.fei.langroup.servicio.DAO.IdiomaDAO;

public class IdiomaViewModel extends ViewModel {

    private MutableLiveData<List<Idioma>> idiomas;

    public LiveData<List<Idioma>> getIdiomas(String colaboradorId) {
        if (idiomas == null) {
            idiomas = new MutableLiveData<>();
            loadIdiomas(colaboradorId);
        }
        return idiomas;
    }

    private void loadIdiomas(String colaboradorId) {
        IdiomaDAO.obtenerIdiomasPorColaborador(colaboradorId, new Callback<ArrayList<Idioma>>() {
            @Override
            public void onResponse(Call<ArrayList<Idioma>> call, Response<ArrayList<Idioma>> response) {
                if (response.isSuccessful()) {
                    idiomas.setValue(response.body());
                } else {
                    idiomas.setValue(new ArrayList<>());
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Idioma>> call, Throwable t) {
                idiomas.setValue(new ArrayList<>());
            }
        });
    }
}
