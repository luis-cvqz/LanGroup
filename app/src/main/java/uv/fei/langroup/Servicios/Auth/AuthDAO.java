package uv.fei.langroup.Servicios.Auth;

import android.util.Log;

import androidx.core.app.AppOpsManagerCompat;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import uv.fei.langroup.Servicios.APIClient;
import uv.fei.langroup.clases.Auth;

public class AuthDAO {

    public static void iniciarSesión(String correo, String contrasenia, final Callback<Auth> callback){
        Retrofit retrofit = APIClient.iniciarAPI();
        AuthServicio authServicio = retrofit.create(AuthServicio.class);

        Map<String, String> credentials = new HashMap<>();
        credentials.put("correo", correo);
        credentials.put("contrasenia", contrasenia);
        Call<Auth> call = authServicio.iniciarSesion(credentials);

        call.enqueue(new retrofit2.Callback<Auth>() {
            @Override
            public void onResponse(Call<Auth> call, Response<Auth> response) {
                if(response.isSuccessful()){
                    Auth colaboradorIniciado = response.body();
                    callback.onResponse(call, Response.success(colaboradorIniciado));
                }else{
                    callback.onFailure(call, new Throwable("Error en la respuesta: " + response.code()));
                }
            }

            @Override
            public void onFailure(Call<Auth> call, Throwable t) {
                Log.d("PruebaLogIn", "Error en la conexión: " + t.getMessage());
                callback.onFailure(call, t);
            }
        });
    }
}
