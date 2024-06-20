package uv.fei.langroup.vistamodelo;

import android.util.Log;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.LiveData;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import uv.fei.langroup.modelo.POJO.Auth;
import uv.fei.langroup.modelo.POJO.Colaborador;
import uv.fei.langroup.modelo.POJO.JsonResponse;
import uv.fei.langroup.servicio.DAO.AuthDAO;
import uv.fei.langroup.servicio.DAO.ColaboradorDAO;
import uv.fei.langroup.utilidades.SesionSingleton;

public class MainViewModel extends ViewModel {
    private MutableLiveData<JsonResponse> authLiveData;
    private MutableLiveData<Colaborador> colaborador;
    private MutableLiveData<Integer> colaboradorCodigo;
    private MutableLiveData<Integer> authCodigo;

    public MainViewModel() {
        authLiveData = new MutableLiveData<>();
        colaborador = new MutableLiveData<>();
        colaboradorCodigo = new MutableLiveData<>();
        authCodigo = new MutableLiveData<>();
    }

    public LiveData<Colaborador> getColaborador() {
        return colaborador;
    }

    public LiveData<JsonResponse> getIniciarSesionResponse() {
        return authLiveData;
    }

    public LiveData<Integer> getColaboradorCodigo() {
        return colaboradorCodigo;
    }

    public LiveData<Integer> getAuthCodigo() {
        return authCodigo;
    }

    public void fetchIniciarSesion(Auth auth) {
        AuthDAO.iniciarSesion(auth, new Callback<JsonResponse>() {
            @Override
            public void onResponse(Call<JsonResponse> call, Response<JsonResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    authLiveData.setValue(response.body());
                    authCodigo.setValue(response.code());
                } else {
                    authLiveData.setValue(null);
                    authCodigo.setValue(response.code());
                    Log.e("MainViewModel", "Código: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<JsonResponse> call, Throwable t) {
                String errorMensaje = t.getMessage();
                Log.d("MainViewModel", errorMensaje);

                if (errorMensaje.contains("Error en la respuesta")) {
                    String[] partes = errorMensaje.split(": ");

                    if (partes.length == 2) {
                        int codigoError = Integer.parseInt(partes[1]);
                        authCodigo.setValue(codigoError);
                    } else {
                        authCodigo.setValue(500);
                    }
                } else {
                    authCodigo.setValue(500);
                    Log.e("MainViewModel", "Error en la conexión: " + t.getMessage());
                }

                authLiveData.setValue(null);
            }
        });
    }

    public void fetchColaborador(String correo) {
        ColaboradorDAO.obtenerColaboradorPorCorreo(correo, new Callback<Colaborador>() {
            @Override
            public void onResponse(Call<Colaborador> call, Response<Colaborador> response) {
                if (response.isSuccessful() && response.body() != null) {
                    colaborador.setValue(response.body());
                    colaboradorCodigo.setValue(response.code());
                } else {
                    colaborador.setValue(null);
                    colaboradorCodigo.setValue(response.code());
                    Log.e("MainViewModel", "Código: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<Colaborador> call, Throwable t) {
                colaborador.setValue(null);

                colaboradorCodigo.setValue(500);
                Log.e("MainViewModel", "Error en la conexión: " + t.getMessage());
            }
        });
    }
}
