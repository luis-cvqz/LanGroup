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
                    .baseUrl("https://3a54-2806-10a6-c-8adf-58bb-5671-9960-c1db.ngrok-free.app/api/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(client)
                    .build();
        }
      
        return retrofit;
    }
}
