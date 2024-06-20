package uv.fei.langroup.vistamodelo.instructores;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import uv.fei.langroup.modelo.POJO.Idioma;
import uv.fei.langroup.servicio.DAO.IdiomaDAO;

public class SolicitarRolInstructorViewModel extends ViewModel {
    private MutableLiveData<ArrayList<Idioma>> idiomas;
    private MutableLiveData<Integer> codigo;

    public SolicitarRolInstructorViewModel(){
        idiomas = new MutableLiveData<>();
        codigo = new MutableLiveData<>();
    }

    public LiveData<ArrayList<Idioma>> getIdiomas(){
        return idiomas;
    }

    public LiveData<Integer> getCodigo(){
        return codigo;
    }

    public void fetchIdiomas(){
        IdiomaDAO.obtenerIdiomas(new Callback<ArrayList<Idioma>>() {
            @Override
            public void onResponse(Call<ArrayList<Idioma>> call, Response<ArrayList<Idioma>> response) {
                if(response.isSuccessful()){
                    idiomas.setValue(response.body());
                    codigo.setValue(response.code());
                }else{
                    codigo.setValue(response.code());
                    Log.e("SolicitarRolInstructorViewModel", "Código: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Idioma>> call, Throwable t) {
                codigo.setValue(500);
                Log.e("SolicitarRolInstructorViewModel", "Error en la conexión: " + t.getMessage());
            }
        });
    }
}
