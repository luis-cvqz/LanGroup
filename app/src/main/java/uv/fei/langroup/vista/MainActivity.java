package uv.fei.langroup.vista;

import static uv.fei.langroup.utilidades.Validador.esContraseniaValida;
import static uv.fei.langroup.utilidades.Validador.esCorreoValido;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import uv.fei.langroup.R;
import uv.fei.langroup.modelo.POJO.Auth;
import uv.fei.langroup.modelo.POJO.Colaborador;
import uv.fei.langroup.utilidades.SesionSingleton;
import uv.fei.langroup.vista.cuenta.CrearCuentaActivity;
import uv.fei.langroup.vista.cuenta.RecuperarContraseniaActivity;
import uv.fei.langroup.databinding.ActivityMainBinding;
import uv.fei.langroup.vistamodelo.MainViewModel;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private MainViewModel mainViewModel;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        mainViewModel = new ViewModelProvider(this).get(MainViewModel.class);

        progressBar = findViewById(R.id.progressBar);
        progressBar.setVisibility(View.GONE);

        binding.txtRegistro.setOnClickListener(v->{
            abrirCrearCuenta();
        });

        binding.btnLogin.setOnClickListener(v->{
            if (validarFormato()) {
                progressBar.setVisibility(View.VISIBLE);
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

    @SuppressLint("MissingSuperCall")
    @Override
    public void onBackPressed() {
        finishAffinity();
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
            progressBar.setVisibility(View.GONE);
        }

        if (!esContraseniaValida(binding.txtPassword.getText().toString())) {
            binding.lblContraseniaError.setVisibility(View.VISIBLE);
            esValido = false;
            progressBar.setVisibility(View.GONE);
        }
        return esValido;
    }

    private void iniciarSesion() {
        Auth auth = new Auth();
        auth.setCorreo(String.valueOf(binding.txtCorreo.getText()));
        auth.setContrasenia(String.valueOf(binding.txtPassword.getText()));

        mainViewModel.fetchIniciarSesion(auth);
        observeIniciarSesionCodigo();
    }

    private void observeIniciarSesion() {
        mainViewModel.getIniciarSesionResponse().observe(this, jsonResponse-> {
            if (jsonResponse != null) {
                SesionSingleton.getInstance().setToken(jsonResponse.getJwt());
            } else {
                showMessage("No hay conexión con el servidor. Intenta más tarde.");
            }
            progressBar.setVisibility(View.GONE);
        });
    }

    private void observeIniciarSesionCodigo() {
        mainViewModel.getAuthCodigo().observe(this, codigo-> {
            if (codigo != null) {
                if (esCodigoExitoso(codigo)) {
                    observeIniciarSesion();
                    buscarColaborador();
                } else {
                    manejarCodigoError(codigo);
                }
            } else {
                showMessage("No hay conexión con el servidor. Intenta más tarde.");
            }
            progressBar.setVisibility(View.GONE);
        });
    }

    private void observeColaboradorCodigo() {
        mainViewModel.getColaboradorCodigo().observe(this, codigo-> {
            if (codigo != null) {
                if (esCodigoExitoso(codigo)) {
                    observeColaborador();
                } else if (esCodigoErrorServidor(codigo)) {
                    showMessage("No hay conexión con el servidor. Intenta más tarde.");
                } else {
                    showMessage("Ocurrió un problema. Intenta más tarde.");
                }
            } else {
                showMessage("No hay conexión con el servidor. Intenta más tarde.");
            }
            progressBar.setVisibility(View.GONE);
        });
    }

    private void observeColaborador() {
        mainViewModel.getColaborador().observe(this, colaborador -> {
            if (colaborador != null) {
                guardarSingleton(colaborador);
                abrirMenuPrincipal();
            } else {
                showMessage("No hay conexión con el servidor. Intenta más tarde.");
            }
            progressBar.setVisibility(View.GONE);
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
                showMessage("No hay conexión con el servidor. Intenta más tarde.");
                break;
            default:
                showMessage("Ocurrió un problema. Intenta más tarde.");
                break;
        }
    }

    private boolean esCodigoExitoso(int code) {
        return code >= 200 && code < 300;
    }

    private boolean esCodigoErrorServidor(int code) {
        return code == 500;
    }

    private void guardarSingleton(Colaborador colaborador) {
        SesionSingleton.getInstance().setColaborador(colaborador);
    }

    private void buscarColaborador() {
        String correo = String.valueOf(binding.txtCorreo.getText());

        mainViewModel.fetchColaborador(correo);
        observeColaboradorCodigo();
    }
}