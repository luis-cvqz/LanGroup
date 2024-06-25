package uv.fei.langroup.servicio.DAO;

import android.util.Log;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import uv.fei.langroup.modelo.POJO.ArchivoMultimedia;
import uv.fei.langroup.servicio.servicios.APIClient;
import uv.fei.langroup.servicio.servicios.ArchivoMultimediaServicio;

public class ArchivoMultimediaDAO {
    public static void crearArchivoMultimedia(File imagen, String publicacionid, final Callback<ArchivoMultimedia> callback) {
        Retrofit retrofit = APIClient.iniciarAPI();
        ArchivoMultimediaServicio servicio = retrofit.create(ArchivoMultimediaServicio.class);

        RequestBody requestFile = RequestBody.create(MediaType.parse("image/jpeg"), imagen);
        MultipartBody.Part body = MultipartBody.Part.createFormData("file", imagen.getName(), requestFile);
        RequestBody id = RequestBody.create(MediaType.parse("text/plain"), publicacionid);

        Call<ArchivoMultimedia> call = servicio.crearArchivoMultimedia(body, id);

        call.enqueue(new Callback<ArchivoMultimedia>() {
            @Override
            public void onResponse(Call<ArchivoMultimedia> call, Response<ArchivoMultimedia> response) {
                if (response.isSuccessful()) {
                    ArchivoMultimedia archivoMultimedia = response.body();
                    callback.onResponse(call, Response.success(archivoMultimedia));
                } else {
                    callback.onFailure(call, new Throwable("Error en la respuesta: " + response.code()));
                }
            }

            @Override
            public void onFailure(Call<ArchivoMultimedia> call, Throwable t) {
                Log.d("CrearArchivoMultimediaDAO", "Error al crear: " + t.getMessage());
                callback.onFailure(call, t);
            }
        });
    }

    /*public static void crearArchivoMultimedia(ArchivoMultimedia archivoMultimedia, final Callback<ArchivoMultimedia> callback) {
        Retrofit retrofit = APIClient.iniciarAPI();
        ArchivoMultimediaServicio archivoMultimediaServicio = retrofit.create(ArchivoMultimediaServicio.class);

        Call<ArchivoMultimedia> call = archivoMultimediaServicio.crearArchivoMultimedia(archivoMultimedia);

        call.enqueue(new Callback<ArchivoMultimedia>() {
            @Override
            public void onResponse(Call<ArchivoMultimedia> call, Response<ArchivoMultimedia> response) {
                if (response.isSuccessful()) {
                    ArchivoMultimedia archivoMultimedia = response.body();
                    callback.onResponse(call, Response.success(archivoMultimedia));
                } else {
                    callback.onFailure(call, new Throwable("Error en la respuesta: " + response.code()));
                }
            }

            @Override
            public void onFailure(Call<ArchivoMultimedia> call, Throwable t) {
                Log.d("CrearArchivoMultimediaDAO", "Error al crear: " + t.getMessage());
                callback.onFailure(call, t);
            }
        });
    }*/
}
