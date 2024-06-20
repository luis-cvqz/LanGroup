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
import uv.fei.langroup.modelo.POJO.Solicitud;
import uv.fei.langroup.servicio.DAO.ColaboradorDAO;
import uv.fei.langroup.servicio.DAO.SolicitudDAO;

public class AgregarInstructorViewModel extends ViewModel {
    private MutableLiveData<ArrayList<Colaborador>> aprendicesConSolicitudPendiente;
    private MutableLiveData<Integer> codigo;

    public AgregarInstructorViewModel(){
        aprendicesConSolicitudPendiente = new MutableLiveData<>();
        codigo = new MutableLiveData<>();
    }

    public LiveData<ArrayList<Colaborador>> getAprendicesConSolicitudPendiente(){
        return aprendicesConSolicitudPendiente;
    }

    public LiveData<Integer> getCodigo(){
        return codigo;
    }

    public void fetchAprendicesConSolicitudPendiente(){
        SolicitudDAO.obtenerSolicitudesPorEstado("Pendiente", new Callback<ArrayList<Solicitud>>() {
            @Override
            public void onResponse(Call<ArrayList<Solicitud>> call, Response<ArrayList<Solicitud>> response) {
                if(response.isSuccessful()){
                    ArrayList<Solicitud> solicitudesPendientes = response.body();

                    ColaboradorDAO.obtenerColaboradoresPorNombreRol("Aprendiz", new Callback<ArrayList<Colaborador>>() {
                        @Override
                        public void onResponse(Call<ArrayList<Colaborador>> call, Response<ArrayList<Colaborador>> response) {
                            if(response.isSuccessful()){
                                ArrayList<Colaborador> aprendicesConSolicitudPendienteApi = new ArrayList<>();
                                for(Colaborador aprendiz : response.body()){
                                    for(Solicitud solicitud : solicitudesPendientes){
                                        if(solicitud.getColaborador().getId().equalsIgnoreCase(aprendiz.getId())){
                                            aprendicesConSolicitudPendienteApi.add(aprendiz);
                                        }
                                    }
                                }

                                aprendicesConSolicitudPendiente.setValue(aprendicesConSolicitudPendienteApi);
                                codigo.setValue(response.code());
                            }else{
                                codigo.setValue(response.code());
                                Log.e("AgregarInstructorViewModel", "Error en la conexión: " + response.code());
                            }
                        }

                        @Override
                        public void onFailure(Call<ArrayList<Colaborador>> call, Throwable t) {
                            codigo.setValue(500);
                            Log.e("AgregarInstructorViewModel", "Error en la conexión: " + t.getMessage());
                        }
                    });
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
