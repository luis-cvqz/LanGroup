package uv.fei.langroup.servicio.servicios;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class APIClient {

    public static Retrofit iniciarAPI(){
        String baseURL = "https://b8a5-2806-2f0-7080-da47-5cab-17a9-d04b-4b31.ngrok-free.app/api/";
        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
        Retrofit retrofit = new Retrofit.Builder().baseUrl(baseURL).addConverterFactory(GsonConverterFactory.create(gson)).build();
        return retrofit;
    }
}
