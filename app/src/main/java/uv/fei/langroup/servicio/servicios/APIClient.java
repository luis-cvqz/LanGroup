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
            OkHttpClient client = new OkHttpClient.Builder()
                    .addInterceptor(new AuthInterceptor())
                    .build();

            retrofit = new Retrofit.Builder()
                    .baseUrl("https://dbfb-2806-2f0-7080-da47-f845-bc87-3bdd-1a82.ngrok-free.app/api/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(client)
                    .build();
        }
      
        return retrofit;
    }
}
