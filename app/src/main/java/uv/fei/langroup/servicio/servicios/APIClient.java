package uv.fei.langroup.servicio.servicios;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class APIClient {

    public static Retrofit iniciarAPI(){
        String baseURL = "https://26cc-187-190-223-10.ngrok-free.app/api/";
        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
        Retrofit retrofit = new Retrofit.Builder().baseUrl(baseURL).addConverterFactory(GsonConverterFactory.create(gson)).build();
        return retrofit;
    }
}
