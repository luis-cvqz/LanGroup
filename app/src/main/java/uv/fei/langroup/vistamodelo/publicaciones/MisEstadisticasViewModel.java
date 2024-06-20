package uv.fei.langroup.vistamodelo.publicaciones;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import uv.fei.langroup.modelo.POJO.Publicacion;
import uv.fei.langroup.servicio.DAO.PublicacionDAO;

public class MisEstadisticasViewModel extends ViewModel {
    private MutableLiveData<ArrayList<Publicacion>> publicaciones;
    private MutableLiveData<Integer> codigo;

    public MisEstadisticasViewModel(){
        publicaciones = new MutableLiveData<>();
        codigo = new MutableLiveData<>();
    }

    public LiveData<ArrayList<Publicacion>> getPublicaciones(){
        return publicaciones;
    }

    public LiveData<Integer> getCodigo(){
        return codigo;
    }

    public void fetchPublicaciones(String colaboradorId){
        PublicacionDAO.obtenerPublicacionesPorColaborador(colaboradorId, new Callback<ArrayList<Publicacion>>() {
            @Override
            public void onResponse(Call<ArrayList<Publicacion>> call, Response<ArrayList<Publicacion>> response) {
                if(response.isSuccessful()){
                    publicaciones.setValue(response.body());
                    codigo.setValue(response.code());
                }else{
                    codigo.setValue(response.code());
                    Log.e("MisEstadisticasViewModel", "Código: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Publicacion>> call, Throwable t) {
                codigo.setValue(500);
                Log.e("MisEstadisticasViewModel", "Error en la conexión: " + t.getMessage());
            }
        });

    }
}
