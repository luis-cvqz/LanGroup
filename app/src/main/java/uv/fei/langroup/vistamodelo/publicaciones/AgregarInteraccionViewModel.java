package uv.fei.langroup.vistamodelo.publicaciones;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import uv.fei.langroup.modelo.POJO.Interaccion;
import uv.fei.langroup.servicio.DAO.InteraccionDAO;

public class AgregarInteraccionViewModel extends ViewModel {
    private MutableLiveData<ArrayList<Interaccion>> interacciones;

    private MutableLiveData<Integer> codigoInteraccion;

    private MutableLiveData<Integer> codigoAgregarInteraccion;

    public AgregarInteraccionViewModel() {
        interacciones = new MutableLiveData<>();
        codigoInteraccion = new MutableLiveData<>();
        codigoAgregarInteraccion = new MutableLiveData<>();
    }

    public LiveData<ArrayList<Interaccion>> getInteracciones() {
        return interacciones;
    }

    public LiveData<Integer> getCodigoInteraccion() {
        return codigoInteraccion;
    }

    public LiveData<Integer> getCodigoAgregarInteraccion() {
        return codigoAgregarInteraccion;
    }

    public void fetchInteracciones(String idPublicacion) {
        InteraccionDAO.obtenerInteraccionesDePublicacion(idPublicacion, new Callback<ArrayList<Interaccion>>() {
            @Override
            public void onResponse(Call<ArrayList<Interaccion>> call, Response<ArrayList<Interaccion>> response) {
                if(response.isSuccessful()){
                    interacciones.setValue(response.body());
                    codigoInteraccion.setValue(response.code());
                }else{
                    codigoInteraccion.setValue(response.code());
                    Log.e("AgregarInteraccionViewModel", "Código: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Interaccion>> call, Throwable t) {
                codigoInteraccion.setValue(500);
                Log.e("AgregarInteraccionViewModel", "Error en la conexión: " + t.getMessage());
            }
        });
    }

    public void fetchAgregarInteraccion(Interaccion interaccion) {
        InteraccionDAO.crearInteraccion(interaccion, new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    codigoAgregarInteraccion.setValue(response.code());
                }
                else {
                    codigoAgregarInteraccion.setValue(response.code());
                    Log.e("AgregarInteraccionViewModel", "Código: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                codigoAgregarInteraccion.setValue(500);
                Log.e("AgregarInteraccionViewModel", "Error en la conexión: " + t.getMessage());
            }
        });
    }
}
