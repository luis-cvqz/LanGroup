package uv.fei.langroup.servicio.servicios;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import uv.fei.langroup.utilidades.AuthInterceptor;

public class APIClient {
    private static Retrofit retrofit = null;

    public static Retrofit iniciarAPI(){
        if (retrofit == null) {
            /* OkHttpClient client = new OkHttpClient.Builder()
                    .addInterceptor(new AuthInterceptor())
                    .build(); */

            retrofit = new Retrofit.Builder()
                    .baseUrl("https://58fe-187-190-223-10.ngrok-free.app/api/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
      
        return retrofit;
    }
}
