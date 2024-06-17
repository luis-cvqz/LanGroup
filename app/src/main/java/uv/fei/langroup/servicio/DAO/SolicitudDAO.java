package uv.fei.langroup.servicio.DAO;

import android.util.Log;

import java.util.ArrayList;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import uv.fei.langroup.modelo.POJO.Solicitud;
import uv.fei.langroup.servicio.servicios.APIClient;
import uv.fei.langroup.servicio.servicios.SolicitudServicio;

public class SolicitudDAO {
    public static void obtenerSolicitudesPorEstado(String estado, final Callback<ArrayList<Solicitud>> callback){
        Retrofit retrofit = APIClient.iniciarAPI();
        SolicitudServicio solicitudServicio = retrofit.create(SolicitudServicio.class);

        Call<ArrayList<Solicitud>> call = solicitudServicio.obtenerSolicitudes();

        call.enqueue(new Callback<ArrayList<Solicitud>>() {
            @Override
            public void onResponse(Call<ArrayList<Solicitud>> call, Response<ArrayList<Solicitud>> response) {
                if(response.isSuccessful()){
                    ArrayList<Solicitud> solicitudes = response.body();
                    ArrayList<Solicitud> solicitudesFiltradas = new ArrayList<>();

                    for (Solicitud solicitud : solicitudes) {
                        if(solicitud.getEstado().equalsIgnoreCase(estado)){
                            solicitudesFiltradas.add(solicitud);
                        }
                    }

                    callback.onResponse(call, Response.success(solicitudesFiltradas));
                }else{
                    callback.onFailure(call, new Throwable("Error en la respuesta: " + response.code()));
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Solicitud>> call, Throwable t) {
                Log.d("Solicitudes", "Error en la conexión: " + t.getMessage());
                callback.onFailure(call, t);
            }
        });
    }

    public static void obtenerSolicitudesPorColaboradorId(String colaboradorId, final Callback<ArrayList<Solicitud>> callback){
        Retrofit retrofit = APIClient.iniciarAPI();
        SolicitudServicio solicitudServicio = retrofit.create(SolicitudServicio.class);

        Call<ArrayList<Solicitud>> call = solicitudServicio.obtenerSolicitudesPorColaboradorId(colaboradorId);

        call.enqueue(new Callback<ArrayList<Solicitud>>() {
            @Override
            public void onResponse(Call<ArrayList<Solicitud>> call, Response<ArrayList<Solicitud>> response) {
                if(response.isSuccessful()){
                    ArrayList<Solicitud> solicitudes = response.body();
                    callback.onResponse(call, Response.success(solicitudes));
                }else{
                    callback.onFailure(call, new Throwable("Error en la respuesta: " + response.code()));
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Solicitud>> call, Throwable t) {
                Log.d("Solicitudes", "Error en la conexión: " + t.getMessage());
                callback.onFailure(call, t);
            }
        });
    }

    public static void crearSolicitud(Solicitud solicitud, final Callback<Solicitud> callback){
        Retrofit retrofit = APIClient.iniciarAPI();
        SolicitudServicio solicitudServicio = retrofit.create(SolicitudServicio.class);

        Call<Solicitud> call = solicitudServicio.crearSolicitud(solicitud);

        call.enqueue(new Callback<Solicitud>() {
            @Override
            public void onResponse(Call<Solicitud> call, Response<Solicitud> response) {
                if(response.isSuccessful()){
                    Solicitud solicitudResponse = response.body();
                    callback.onResponse(call, Response.success(solicitudResponse));
                }else{
                    callback.onFailure(call, new Throwable("Error en la respuesta: " + response.code()));
                }
            }

            @Override
            public void onFailure(Call<Solicitud> call, Throwable t) {
                Log.d("Solicitudes", "Error en la conexión: " + t.getMessage());
                callback.onFailure(call, t);
            }
        });
    }

    public static void actualizarSolicitud(String solicitudId, Solicitud solicitud, final Callback<Solicitud> callback){
        Retrofit retrofit = APIClient.iniciarAPI();
        SolicitudServicio solicitudServicio = retrofit.create(SolicitudServicio.class);

        Call<Solicitud> call = solicitudServicio.actualizarSolicitud(solicitudId, solicitud);

        call.enqueue(new Callback<Solicitud>() {
            @Override
            public void onResponse(Call<Solicitud> call, Response<Solicitud> response) {
                if(response.isSuccessful()){
                    Solicitud solicitudResponse = response.body();
                    callback.onResponse(call, Response.success(solicitudResponse));
                }else{
                    callback.onFailure(call, new Throwable("Error en la respuesta: " + response.code()));
                }
            }

            @Override
            public void onFailure(Call<Solicitud> call, Throwable t) {
                Log.d("Solicitudes", "Error en la conexión: " + t.getMessage());
                callback.onFailure(call, t);
            }
        });
    }
}
