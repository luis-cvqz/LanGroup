package uv.fei.langroup.modelo.servicios;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class APIClient {

    public static Retrofit iniciarAPI(){
        String baseURL = "https://0d2b-2806-10a6-c-8adf-dcb6-b146-63b1-4fd2.ngrok-free.app/api/";
        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
        Retrofit retrofit = new Retrofit.Builder().baseUrl(baseURL).addConverterFactory(GsonConverterFactory.create(gson)).build();
        return retrofit;
    }
}
