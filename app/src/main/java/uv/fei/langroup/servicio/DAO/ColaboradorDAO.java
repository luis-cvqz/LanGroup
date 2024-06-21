package uv.fei.langroup.servicio.DAO;

import android.util.Log;

import androidx.lifecycle.ViewModel;

import org.checkerframework.checker.units.qual.C;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import uv.fei.langroup.servicio.servicios.APIClient;
import uv.fei.langroup.modelo.POJO.Colaborador;
import uv.fei.langroup.servicio.servicios.ColaboradorServicio;

public class ColaboradorDAO {

    public static void crearCuenta(Colaborador colaborador, final Callback<Colaborador> callback) {
        Retrofit retrofit = APIClient.iniciarAPI();
        ColaboradorServicio colaboradorServicio = retrofit.create(ColaboradorServicio.class);

        Call<Colaborador> call = colaboradorServicio.crearCuenta(colaborador);

        call.enqueue(new Callback<Colaborador>() {
            @Override
            public void onResponse(Call<Colaborador> call, Response<Colaborador> response) {
                if (response.isSuccessful()) {
                    callback.onResponse(call, response);
                } else {
                    callback.onResponse(call, response);
                }
            }

            @Override
            public void onFailure(Call<Colaborador> call, Throwable t) {
                Log.d("CreaciónPrueba", "Error en la conexión: " + t.getMessage());
                callback.onFailure(call, t);
            }
        });
    }

    public static void obtenerColaboradoresPorNombreRol(String nombreRol, final Callback<ArrayList<Colaborador>> callback){
        Retrofit retrofit = APIClient.iniciarAPI();
        ColaboradorServicio colaboradorServicio = retrofit.create(ColaboradorServicio.class);

        Call<ArrayList<Colaborador>> call = colaboradorServicio.obtenerColaboradoresPorRol(nombreRol);

        call.enqueue(new Callback<ArrayList<Colaborador>>() {
            @Override
            public void onResponse(Call<ArrayList<Colaborador>> call, Response<ArrayList<Colaborador>> response) {
                if(response.isSuccessful()){
                    ArrayList<Colaborador> colaboradores = response.body();
                    callback.onResponse(call, Response.success(colaboradores));
                }else{
                    callback.onFailure(call, new Throwable("Error en la respuesta: " + response.code()));
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Colaborador>> call, Throwable t) {
                Log.d("Colaborador", "Error en la conexión: " + t.getMessage());
                callback.onFailure(call, t);
            }
        });
    }

    public static void actualizarRolDeColaborador(String colaboradorId, Colaborador colaborador, final Callback<Colaborador> callback){
        Retrofit retrofit = APIClient.iniciarAPI();
        ColaboradorServicio colaboradorServicio = retrofit.create(ColaboradorServicio.class);

        Call<Colaborador> call = colaboradorServicio.actualizarRolDeColaborador(colaboradorId, colaborador);

        call.enqueue(new Callback<Colaborador>() {
            @Override
            public void onResponse(Call<Colaborador> call, Response<Colaborador> response) {
                if(response.isSuccessful()){
                    Colaborador colaborador = response.body();
                    callback.onResponse(call, Response.success(colaborador));
                }else{
                    callback.onFailure(call, new Throwable("Error en la respuesta: " + response.code()));
                }
            }

            @Override
            public void onFailure(Call<Colaborador> call, Throwable t) {
                Log.d("ColaboradorDAO", "Error en la conexión: " + t.getMessage());
                callback.onFailure(call, t);
            }
        });
    }

    public static void obtenerColaboradorPorCorreo(String correo, final Callback<Colaborador> callback) {
        Retrofit retrofit = APIClient.iniciarAPI();
        ColaboradorServicio colaboradorServicio = retrofit.create(ColaboradorServicio.class);

        Call<Colaborador> call = colaboradorServicio.obtenerColaboradorPorCorreo(correo);

        call.enqueue(new Callback<Colaborador>() {
            @Override
            public void onResponse(Call<Colaborador> call, Response<Colaborador> response) {
                if (response.isSuccessful()) {
                    Colaborador colaborador = response.body();
                    callback.onResponse(call, Response.success(colaborador));
                } else {
                    callback.onFailure(call, new Throwable("Error en la respuesta: " + response.code()));
                }
            }

            @Override
            public void onFailure(Call<Colaborador> call, Throwable t) {
                Log.d("ColaboradorDAO", "Error en la conexión: " + t.getMessage());
                callback.onFailure(call, t);
            }
        });
    }
}

