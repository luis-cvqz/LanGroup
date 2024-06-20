package uv.fei.langroup.vistamodelo.instructores;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import uv.fei.langroup.modelo.POJO.Colaborador;
import uv.fei.langroup.servicio.DAO.ColaboradorDAO;

public class EliminarInstructorViewModel extends ViewModel {
    private MutableLiveData<ArrayList<Colaborador>> instructores;
    private ColaboradorDAO colaboradorDAO;

    public EliminarInstructorViewModel(){
        colaboradorDAO = new ColaboradorDAO();
        instructores = new MutableLiveData<>();
    }

    public LiveData<ArrayList<Colaborador>> getInstructores(){
        return instructores;
    }

    public void fetchInstructores(){
        ColaboradorDAO.obtenerColaboradoresPorNombreRol("Instructor", new Callback<ArrayList<Colaborador>>() {
            @Override
            public void onResponse(Call<ArrayList<Colaborador>> call, Response<ArrayList<Colaborador>> response) {
                if(response.isSuccessful()){
                    instructores.setValue(response.body());
                }else{
                    Log.e("EliminarInstructorViewModel", "Código: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Colaborador>> call, Throwable t) {
                Log.e("EliminarInstructorViewModel", "Error en la conexión: " + t.getMessage());
            }
        });
    }
}
