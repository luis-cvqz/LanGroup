package uv.fei.langroup.servicio.servicios;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import uv.fei.langroup.utilidades.AuthInterceptor;

public class APIClient {
    private static ManagedChannel grpcChannel = null;
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

    public static ManagedChannel iniciarGrpc(){
        if(grpcChannel == null){
            grpcChannel = ManagedChannelBuilder
                    .forTarget("localhost:3300")
                    .usePlaintext()
                    .build();
        }

        return grpcChannel;
    }

    public static void cerrarGrpc(){
        if(grpcChannel != null){
            grpcChannel.shutdown();
            grpcChannel = null;
        }
    }
}
