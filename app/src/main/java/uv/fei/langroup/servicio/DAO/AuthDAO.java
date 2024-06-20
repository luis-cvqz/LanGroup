package uv.fei.langroup.servicio.DAO;

import android.util.Log;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import uv.fei.langroup.modelo.POJO.Auth;
import uv.fei.langroup.modelo.POJO.JsonResponse;
import uv.fei.langroup.servicio.servicios.APIClient;
import uv.fei.langroup.servicio.servicios.AuthServicio;

public class AuthDAO {
    public static void iniciarSesion (Auth auth, final Callback<JsonResponse> callback) {
        Retrofit retrofit = APIClient.iniciarAPI();
        AuthServicio authServicio = retrofit.create(AuthServicio.class);

        Call<JsonResponse> call = authServicio.iniciarSesion(auth);

        call.enqueue(new Callback<JsonResponse>() {
            @Override
            public void onResponse(Call<JsonResponse> call, Response<JsonResponse> response) {
                if (response.isSuccessful()) {
                    JsonResponse jsonResponse = response.body();
                    callback.onResponse(call, Response.success(jsonResponse));
                } else {
                    callback.onFailure(call, new Throwable("Error en la respuesta: " + response.code()));
                }
            }

            @Override
            public void onFailure(Call<JsonResponse> call, Throwable t) {
                Log.d("IniciarSesión", "Error en la conexión: " + t.getMessage());
                callback.onFailure(call, t);
            }
        });
    }
}
