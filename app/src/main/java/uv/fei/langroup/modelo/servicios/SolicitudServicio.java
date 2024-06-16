package uv.fei.langroup.modelo.servicios;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import uv.fei.langroup.modelo.POJO.Solicitud;

public interface SolicitudServicio {
    @GET("solicitudes")
    Call<List<Solicitud>> obtenerSolicitudes();

    @GET("solicitudes/{id}")
    Call<Solicitud> obtenerSolicitudPorId(@Path("id") String solicitudId);

    @POST("solicitudes")
    Call<Solicitud> crearSolicitud(@Body Solicitud solicitud);

    @PUT("solicitudes/{id}")
    Call<Solicitud> actualizarSolicitud(@Path("id") String solicitudId);

    @DELETE("solicitudes/{id}")
    Call<Void> eliminarSolicitud(@Path("id") String solicitudId);
}
