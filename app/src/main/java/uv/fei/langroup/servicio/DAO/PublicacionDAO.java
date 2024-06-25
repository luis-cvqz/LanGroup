package uv.fei.langroup.servicio.DAO;

import android.util.Log;

import java.io.File;
import java.util.ArrayList;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import uv.fei.langroup.modelo.POJO.Publicacion;
import uv.fei.langroup.modelo.POJO.PublicacionConArchivo;
import uv.fei.langroup.servicio.servicios.APIClient;
import uv.fei.langroup.servicio.servicios.PublicacionServicio;

public class PublicacionDAO {

    public static void crearPublicacion(Publicacion nuevaPublicacion, final Callback<Publicacion> callback) {
        Retrofit retrofit = APIClient.iniciarAPI();
        PublicacionServicio publicacionServicio = retrofit.create(PublicacionServicio.class);

        Call<Publicacion> call = publicacionServicio.crearPublicacion(nuevaPublicacion);

        call.enqueue(new Callback<Publicacion>() {
            @Override
            public void onResponse(Call<Publicacion> call, Response<Publicacion> response) {
                if (response.isSuccessful()) {
                    Publicacion publicacionCreada = response.body();
                    callback.onResponse(call, Response.success(publicacionCreada));
                } else {
                    callback.onFailure(call, new Throwable("Error en la respuesta: " + response.code() + response.message()));
                }
            }

            @Override
            public void onFailure(Call<Publicacion> call, Throwable t) {
                Log.d("crearPublicacion", "Error en la conexion: " + t.getMessage());
                callback.onFailure(call, t);
            }
        });
    }

    public static void crearPublicacionConImagen(File imagen, Publicacion nuevaPublicacion, Callback<PublicacionConArchivo> callback) {
        Retrofit retrofit = APIClient.iniciarAPI();
        PublicacionServicio servicio = retrofit.create(PublicacionServicio.class);

        String tituloPublicacion = nuevaPublicacion.getTitulo();
        String descripcionPublicacion = nuevaPublicacion.getDescripcion();
        String colaboradorId = nuevaPublicacion.getColaboradorId();
        String grupoId = nuevaPublicacion.getGrupoId();

        RequestBody requestFile = RequestBody.create(MediaType.parse("image/jpeg"), imagen);
        MultipartBody.Part body = MultipartBody.Part.createFormData("file", imagen.getName(), requestFile);
        //RequestBody id = RequestBody.create(MediaType.parse("text/plain"), publicacionid);
        RequestBody titulo = RequestBody.create(MediaType.parse("text/plain"), tituloPublicacion);
        RequestBody descripcion = RequestBody.create(MediaType.parse("text/plain"), descripcionPublicacion);
        RequestBody colaboradorid = RequestBody.create(MediaType.parse("text/plain"), colaboradorId);
        RequestBody grupoid = RequestBody.create(MediaType.parse("text/plain"), grupoId);

        Call<PublicacionConArchivo> call = servicio.crearPublicacionConArchivo(body, titulo, descripcion, colaboradorid, grupoid);

        call.enqueue(new Callback<PublicacionConArchivo>() {
            @Override
            public void onResponse(Call<PublicacionConArchivo> call, Response<PublicacionConArchivo> response) {
                if (response.isSuccessful()) {
                    PublicacionConArchivo publicacionConArchivo = response.body();
                    callback.onResponse(call, Response.success(publicacionConArchivo));
                } else {
                    callback.onFailure(call, new Throwable("Error en la respuesta: " + response.code()));
                }
            }

            @Override
            public void onFailure(Call<PublicacionConArchivo> call, Throwable t) {
                Log.d("CrearArchivoMultimediaDAO", "Error al crear: " + t.getMessage());
                callback.onFailure(call, t);
            }
        });
    }

    public static void obtenerPublicacionesPorColaborador(String colaboradorId, Callback<ArrayList<Publicacion>> callback){
        Retrofit retrofit = APIClient.iniciarAPI();
        PublicacionServicio publicacionServicio = retrofit.create(PublicacionServicio.class);

        Call<ArrayList<Publicacion>> call = publicacionServicio.obtenerPublicacionesPorColaborador(colaboradorId);

        call.enqueue(new Callback<ArrayList<Publicacion>>() {
            @Override
            public void onResponse(Call<ArrayList<Publicacion>> call, Response<ArrayList<Publicacion>> response) {
                if(response.isSuccessful()){
                    ArrayList<Publicacion> publicaciones = response.body();
                    callback.onResponse(call, Response.success(publicaciones));
                }else{
                    callback.onFailure(call, new Throwable("Error en la respuesta: " + response.code()));
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Publicacion>> call, Throwable t) {
                Log.d("Rol", "Error en la conexión: " + t.getMessage());
                callback.onFailure(call, t);
            }
        });
    }

    public static void obtenerPublicacionesPorGrupoColaborador(String grupoId, String colaboradorId, Callback<ArrayList<Publicacion>> callback) {
        Retrofit retrofit = APIClient.iniciarAPI();
        PublicacionServicio publicacionServicio = retrofit.create(PublicacionServicio.class);

        Call<ArrayList<Publicacion>> call = publicacionServicio.obtenerPublicacionesPorGrupoColaborador(grupoId, colaboradorId);

        call.enqueue(new Callback<ArrayList<Publicacion>>() {
            @Override
            public void onResponse(Call<ArrayList<Publicacion>> call, Response<ArrayList<Publicacion>> response) {
                if(response.isSuccessful()){
                    ArrayList<Publicacion> publicaciones = response.body();
                    callback.onResponse(call, Response.success(publicaciones));
                }else{
                    callback.onFailure(call, new Throwable("Error en la respuesta: " + response.code()));
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Publicacion>> call, Throwable t) {
                Log.d("PublicacionDAO", "Error en la conexión: " + t.getMessage());
                callback.onFailure(call, t);
            }
        });
    }

    public static void obtenerPublicacionesPorGrupo(String grupoId, Callback<ArrayList<Publicacion>> callback) {
        Retrofit retrofit = APIClient.iniciarAPI();
        PublicacionServicio publicacionServicio = retrofit.create(PublicacionServicio.class);

        Call<ArrayList<Publicacion>> call = publicacionServicio.obtenerPublicacionesPorGrupo(grupoId);

        call.enqueue(new Callback<ArrayList<Publicacion>>() {
            @Override
            public void onResponse(Call<ArrayList<Publicacion>> call, Response<ArrayList<Publicacion>> response) {
                if(response.isSuccessful()){
                    ArrayList<Publicacion> publicaciones = response.body();
                    callback.onResponse(call, Response.success(publicaciones));
                }else{
                    callback.onFailure(call, new Throwable("Error en la respuesta: " + response.code()));
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Publicacion>> call, Throwable t) {
                Log.d("PublicacionDAO", "Error en la conexión: " + t.getMessage());
                callback.onFailure(call, t);
            }
        });
    }

    public static void eliminarPublicacion (String publicacionId, final Callback<Void> callback) {
        Retrofit retrofit = APIClient.iniciarAPI();
        PublicacionServicio publicacionServicio = retrofit.create(PublicacionServicio.class);

        Call<Void> call = publicacionServicio.eliminarPublicacion(publicacionId);

        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    callback.onResponse(call, response);
                } else {
                    callback.onFailure(call, new Throwable("Error en la respuesta: " + response.code()));
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.d("PublicacionDAO", "Error en la conexión: " + t.getMessage());
                callback.onFailure(call, t);
            }
        });
    }
}
