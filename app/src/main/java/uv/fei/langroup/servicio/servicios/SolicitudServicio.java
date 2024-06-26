package uv.fei.langroup.servicio.servicios;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;
import uv.fei.langroup.modelo.POJO.Solicitud;

public interface SolicitudServicio {
    @GET("solicitudes")
    Call<ArrayList<Solicitud>> obtenerSolicitudes();

    @GET("solicitudes")
    Call<ArrayList<Solicitud>> obtenerSolicitudesPorColaboradorId(@Query("colaboradorid") String colaboradorId);

    @GET("solicitudes/{id}/detalle")
    Call<Solicitud> obtenerSolicitudPorId(@Path("id") String solicitudId);

    @GET("solicitudes/{id}")
    Call<byte[]> obtenerConstanciaPorSolicitudId(@Path("id") String solicitudId);

    @POST("solicitudes")
    Call<Solicitud> crearSolicitud(@Body Solicitud solicitud);

    @PUT("solicitudes/{id}")
    Call<Solicitud> actualizarSolicitud(@Path("id") String solicitudId, @Body Solicitud solicitud);

    @DELETE("solicitudes/{id}")
    Call<Void> eliminarSolicitud(@Path("id") String solicitudId);
}
