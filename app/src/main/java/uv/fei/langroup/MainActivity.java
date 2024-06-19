package uv.fei.langroup;

import static uv.fei.langroup.utilidades.Validador.esContraseniaValida;
import static uv.fei.langroup.utilidades.Validador.esCorreoValido;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import uv.fei.langroup.modelo.POJO.Auth;
import uv.fei.langroup.modelo.POJO.Colaborador;
import uv.fei.langroup.modelo.POJO.JsonResponse;
import uv.fei.langroup.servicio.DAO.AuthDAO;
import uv.fei.langroup.servicio.DAO.ColaboradorDAO;
import uv.fei.langroup.utilidades.SesionSingleton;
import uv.fei.langroup.vista.cuenta.CrearCuentaActivity;
import uv.fei.langroup.vista.cuenta.RecuperarContraseniaActivity;
import uv.fei.langroup.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.txtRegistro.setOnClickListener(v->{
            abrirCrearCuenta();
        });

        binding.btnLogin.setOnClickListener(v->{
            if (validarFormato()) {
                iniciarSesion();
            }
        });

        binding.txtRecuperarContrasenia.setOnClickListener(v->{
            abrirRecuperarContrasenia();
        });

        binding.txtCorreo.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                cambiarVisibilidadErrorCorreo(View.GONE);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        binding.txtPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                cambiarVisibilidadErrorContrasenia(View.GONE);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void cambiarVisibilidadErrorCorreo(int valor) {
        binding.lblCorreoError.setVisibility(valor);
    }

    private void cambiarVisibilidadErrorContrasenia(int valor) {
        binding.lblContraseniaError.setVisibility(valor);
    }
    private void abrirMenuPrincipal() {
        Intent intent = new Intent(this, MenuPrincipalActivity.class);
        startActivity(intent);
    }

    private void abrirCrearCuenta() {
        Intent intent = new Intent(this, CrearCuentaActivity.class);
        startActivity(intent);
    }

    private void abrirRecuperarContrasenia() {
        Intent intent = new Intent(this, RecuperarContraseniaActivity.class);
        startActivity(intent);
    }

    private boolean validarFormato() {
        boolean esValido = true;

        if (!esCorreoValido(binding.txtCorreo.getText().toString())) {
            binding.lblCorreoError.setVisibility(View.VISIBLE);
            esValido = false;
        }

        if (!esContraseniaValida(binding.txtPassword.getText().toString())) {
            binding.lblContraseniaError.setVisibility(View.VISIBLE);
            esValido = false;
        }

        return esValido;
    }

    private void iniciarSesion() {
        Auth auth = new Auth();
        auth.setCorreo(String.valueOf(binding.txtCorreo.getText()));
        auth.setContrasenia(String.valueOf(binding.txtPassword.getText()));
        AuthDAO.iniciarSesion(auth, new Callback<JsonResponse>() {
            @Override
            public void onResponse(Call<JsonResponse> call, Response<JsonResponse> response) {
                JsonResponse jsonResponse = response.body();
                if (jsonResponse != null && response.isSuccessful()) {
                    SesionSingleton.getInstance().setToken(jsonResponse.getJwt());
                    buscarColaborador();
                    abrirMenuPrincipal();
                } else {
                    Log.e("Colaborador", "Error en la respuesta: " + response.code());
                    showMessage("Ocurrió un problema. Intenta más tarde.");
                }
            }

            @Override
            public void onFailure(Call<JsonResponse> call, Throwable t) {
                String errorMensaje = t.getMessage();
                Log.d("MainActivity", errorMensaje);

                if (errorMensaje.contains("Error en la respuesta")) {
                    String[] partes = errorMensaje.split(": ");
                    if (partes.length == 2) {
                        int codigoError = Integer.parseInt(partes[1]);
                        manejarCodigoError(codigoError);
                    }
                } else {
                    Log.e("Colaborador", "Error en la conexión: " + t.getMessage());
                    showMessage("Ocurrió un problema. Intenta más tarde.");
                }
            }
        });

    }

    private void showMessage(String msj){
        Toast.makeText(this, msj, Toast.LENGTH_SHORT).show();
    }

    private void manejarCodigoError(int codigoError) {
        switch (codigoError) {
            case 400:
                showMessage("Verifica tus credenciales.");
                break;
            case 401:
                showMessage("Verifica tus credenciales.");
                break;
            case 500:
                showMessage("No hay conexión con el servidor. Intente más tarde.");
                break;
            default:
                showMessage("Ocurrió un problema. Intente más tarde.");
                break;
        }
    }

    private void guardarSingleton(Colaborador colaborador) {
        SesionSingleton.getInstance().setColaborador(colaborador);
    }

    private void buscarColaborador() {
        String correo = String.valueOf(binding.txtCorreo.getText());
        ColaboradorDAO.obtenerColaboradorPorCorreo(correo, new Callback<Colaborador>() {
            @Override
            public void onResponse(Call<Colaborador> call, Response<Colaborador> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Colaborador colaborador = response.body();
                    guardarSingleton(colaborador);
                } else {
                    showMessage("Ocurrió un problema. Intenta más tarde.");
                }
            }

            @Override
            public void onFailure(Call<Colaborador> call, Throwable t) {
                Log.e("Colaborador", "Error en la conexión: " + t.getMessage());
                showMessage("Ocurrió un problema. Intenta más tarde.");
            }
        });
    }
}