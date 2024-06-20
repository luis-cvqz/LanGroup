package uv.fei.langroup.vistamodelo.instructores;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import org.checkerframework.checker.units.qual.C;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import uv.fei.langroup.modelo.POJO.Solicitud;
import uv.fei.langroup.servicio.DAO.SolicitudDAO;

public class VerSolicitudViewModel extends ViewModel {
    private MutableLiveData<Solicitud> solicitud;
    private MutableLiveData<Integer> codigo;

    public VerSolicitudViewModel(){
        solicitud = new MutableLiveData<>();
        codigo = new MutableLiveData<>();
    }

    public LiveData<Solicitud> getSolicitud(){
        return solicitud;
    }

    public LiveData<Integer> getCodigo(){
        return codigo;
    }

    public void fetchSolicitud(String colaboradorId){
        SolicitudDAO.obtenerSolicitudesPorColaboradorId(colaboradorId, new Callback<ArrayList<Solicitud>>() {
            @Override
            public void onResponse(Call<ArrayList<Solicitud>> call, Response<ArrayList<Solicitud>> response) {
                if(response.isSuccessful()){
                    for(Solicitud solicitudPendiente : response.body()){
                        if(solicitudPendiente.getEstado().equalsIgnoreCase("Pendiente")){
                            solicitud.setValue(solicitudPendiente);
                            break;
                        }
                    }
                    codigo.setValue(response.code());
                }else{
                    codigo.setValue(response.code());
                    Log.e("AgregarInstructorViewModel", "Error en la conexión: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Solicitud>> call, Throwable t) {
                codigo.setValue(500);
                Log.e("AgregarInstructorViewModel", "Error en la conexión: " + t.getMessage());
            }
        });
    }
}
