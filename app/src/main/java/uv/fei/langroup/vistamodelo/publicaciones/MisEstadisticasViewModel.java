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
    private PublicacionDAO publicacionDAO;

    public MisEstadisticasViewModel(){
        publicacionDAO = new PublicacionDAO();
        publicaciones = new MutableLiveData<>();
    }

    public LiveData<ArrayList<Publicacion>> getPublicaciones(){
        return publicaciones;
    }

    public void fetchPublicaciones(String colaboradorId){
        PublicacionDAO.obtenerPublicacionesPorColaborador(colaboradorId, new Callback<ArrayList<Publicacion>>() {
            @Override
            public void onResponse(Call<ArrayList<Publicacion>> call, Response<ArrayList<Publicacion>> response) {
                if(response.isSuccessful()){
                    publicaciones.setValue(response.body());
                }else{
                    Log.e("MisEstadisticasViewModel", "Código: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Publicacion>> call, Throwable t) {
                Log.e("MisEstadisticasViewModel", "Error en la conexión: " + t.getMessage());
            }
        });

    }
}
