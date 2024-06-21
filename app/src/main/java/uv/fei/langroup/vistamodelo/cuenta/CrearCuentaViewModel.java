package uv.fei.langroup.vistamodelo.cuenta;

import android.util.Log;
import android.widget.Toast;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import uv.fei.langroup.modelo.POJO.Colaborador;
import uv.fei.langroup.servicio.DAO.ColaboradorDAO;

public class CrearCuentaViewModel extends ViewModel {
    private MutableLiveData<Colaborador> colaboradorLiveData = new MutableLiveData<>();
    private MutableLiveData<String> errorMessageLiveData = new MutableLiveData<>();

    public LiveData<Colaborador> getColaboradorLiveData() {
        return colaboradorLiveData;
    }

    public LiveData<String> getErrorMessageLiveData() {
        return errorMessageLiveData;
    }

    public void crearCuenta(Colaborador colaborador) {
        Log.d("CrearCuentaViewModel", "Antes de llamar al método crearCuenta del DAO");
        ColaboradorDAO.crearCuenta(colaborador, new Callback<Colaborador>() {
            @Override
            public void onResponse(Call<Colaborador> call, Response<Colaborador> response) {
                if (response.isSuccessful()) {
                    colaboradorLiveData.setValue(response.body());
                } else {
                    String errorMessage = "Error en la operación";
                    switch (response.code()) {
                        case 400:
                            errorMessage = "Correo duplicado";
                            break;
                        case 404:
                            errorMessage = "Rol no encontrado";
                            break;
                        case 500:
                            errorMessage = "Error interno del servidor";
                            break;
                    }
                    errorMessageLiveData.setValue(errorMessage);
                }
            }

            @Override
            public void onFailure(Call<Colaborador> call, Throwable t) {
                t.printStackTrace();
                errorMessageLiveData.setValue("Error en la conexión");
            }
        });
    }
}
