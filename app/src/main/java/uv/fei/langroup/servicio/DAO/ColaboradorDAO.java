package uv.fei.langroup.servicio.DAO;

import android.util.Log;

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

    public static void crearCuenta(String nombre, String apellido, String usuario, String correo, String contrasenia, final Callback<Colaborador> callback){
        Retrofit retrofit = APIClient.iniciarAPI();
        ColaboradorServicio colaboradorServicio = retrofit.create(ColaboradorServicio.class);

        Map<String, Object> credentials = new HashMap<>();
        credentials.put("nombre", nombre);
        credentials.put("apellido", apellido);
        credentials.put("usuario", usuario);
        credentials.put("correo", correo);
        credentials.put("contrasenia", contrasenia);
        credentials.put("icono", "icon_perfil_1.png");
        credentials.put("rol", "Aprendiz"); //TODO se debe cambiar el nombre por el id del rol, en la api el rol contiene el id, no el nombre
        Call<Colaborador> call = colaboradorServicio.crearCuenta(credentials);

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

    public static void actualizarRolDeColaborador(String colaboradorId, String rolId, final Callback<Colaborador> callback){
        Retrofit retrofit = APIClient.iniciarAPI();
        ColaboradorServicio colaboradorServicio = retrofit.create(ColaboradorServicio.class);

        Call<Colaborador> call = colaboradorServicio.actualizarRolDeColaborador(colaboradorId, rolId);

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
                Log.d("Colaborador", "Error en la conexión: " + t.getMessage());
                callback.onFailure(call, t);
            }
        });
    }
}

