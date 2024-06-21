package uv.fei.langroup.vista.cuenta;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import uv.fei.langroup.utilidades.Validador;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import uv.fei.langroup.databinding.ActivityCrearCuentaBinding;
import uv.fei.langroup.modelo.POJO.Colaborador;
import uv.fei.langroup.vista.idiomas.SeleccionarIdiomaActivity;
import uv.fei.langroup.vistamodelo.cuenta.CrearCuentaViewModel;
import uv.fei.langroup.vista.MainActivity;

public class CrearCuentaActivity extends AppCompatActivity {

    private ActivityCrearCuentaBinding binding;
    private CrearCuentaViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCrearCuentaBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        viewModel = new ViewModelProvider(this).get(CrearCuentaViewModel.class);

        binding.buttonRegresar.setOnClickListener(v -> {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        });

        EditText nombres = binding.edtNombres;
        EditText apellidos = binding.editApellidos;
        EditText usuario = binding.edtUsuario;
        EditText correo = binding.editCorreo;
        EditText contrasenia = binding.editContrasena;
        EditText repitContrasenia = binding.editConfirmarContrasena;

        binding.btnCrear.setOnClickListener(v -> {
            String nombresCrear = nombres.getText().toString();
            String apellidosCrear = apellidos.getText().toString();
            String usuarioCrear = usuario.getText().toString();
            String correoCrear = correo.getText().toString();
            String contraseniaCrear = contrasenia.getText().toString();
            String repiteContra = repitContrasenia.getText().toString();

            if (validarCampos(nombresCrear, apellidosCrear, usuarioCrear, correoCrear, contraseniaCrear, repiteContra)) {
                Colaborador colaborador = new Colaborador(
                        null,
                        usuarioCrear,
                        correoCrear,
                        contraseniaCrear,
                        nombresCrear,
                        apellidosCrear,
                        null, // descripción vacía por defecto
                        "icon_perfil_1.png", // icono por defecto
                        "Aprendiz" // rol por defecto
                );
                viewModel.crearCuenta(colaborador);
            }
        });

        // Observa los cambios en los datos del colaborador
        viewModel.getColaboradorLiveData().observe(this, new Observer<Colaborador>() {
            @Override
            public void onChanged(Colaborador colaborador) {
                if (colaborador != null) {
                    showMessage("Cuenta Creada exitosamente");
                    Intent intent = new Intent(CrearCuentaActivity.this, SeleccionarIdiomaActivity.class);
                    intent.putExtra("colaboradorId", colaborador.getColaboradorId());
                    startActivity(intent);
                }
            }
        });

        // Observa los cambios en los mensajes de error
        viewModel.getErrorMessageLiveData().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String errorMessage) {
                if (errorMessage != null) {
                    showMessage(errorMessage);
                }
            }
        });
    }

    private boolean validarCampos(String nombre, String apellido, String usuario, String correo, String contrasenia, String repitContrasenia) {
        if (nombre.isEmpty() || apellido.isEmpty() || usuario.isEmpty() || correo.isEmpty() || contrasenia.isEmpty() || repitContrasenia.isEmpty()) {
            showMessage("Todos los campos son obligatorios.");
            return false;
        }

        if (!Validador.esCorreoValido(correo)) {
            showMessage("Correo no válido.");
            return false;
        }

        if (!Validador.esContraseniaValida(contrasenia)) {
            showMessage("La contraseña debe tener entre 8 y 16 caracteres, incluir al menos una letra mayúscula, una minúscula, un número y un carácter especial.");
            return false;
        }

        if (!contrasenia.equals(repitContrasenia)) {
            showMessage("Las contraseñas no coinciden.");
            return false;
        }

        return true;
    }

    private void showMessage(String msj) {
        Toast.makeText(this, msj, Toast.LENGTH_SHORT).show();
    }
}