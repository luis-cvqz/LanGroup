package uv.fei.langroup.vista.cuenta;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import uv.fei.langroup.MainActivity;
import uv.fei.langroup.databinding.ActivityCrearCuentaBinding;
import uv.fei.langroup.servicio.DAO.ColaboradorDAO;
import uv.fei.langroup.modelo.POJO.Colaborador;

public class CrearCuentaActivity extends AppCompatActivity {

    ActivityCrearCuentaBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCrearCuentaBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.buttonRegresar.setOnClickListener(v->{
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

            if(!camposVacios(nombresCrear, apellidosCrear, usuarioCrear, correoCrear, contraseniaCrear, repiteContra)){
                Log.d("CrearCuenta", nombresCrear);
                Log.d("CrearCuenta", apellidosCrear);
                Log.d("CrearCuenta", usuarioCrear);
                Log.d("CrearCuenta", correoCrear);
                Log.d("CrearCuenta", contraseniaCrear);
                crearCuenta(nombresCrear, apellidosCrear, usuarioCrear, correoCrear, contraseniaCrear);
            }else{
                showMessage("Ingrese datos para poder crear la cuenta");
            }
        });
    }

    private boolean camposVacios(String nombre, String apellido, String usuario, String correo, String contrasenia, String repitContrasenia){
        if(nombre.isEmpty() || apellido.isEmpty() || usuario.isEmpty() || correo.isEmpty() || contrasenia.isEmpty() || repitContrasenia.isEmpty()){
            return true;
        }else{
            return false;
        }
    }

    private void crearCuenta(String nombre, String apellido, String usuario, String correo, String contrasenia){
        Log.d("CrearCuenta", "Antes de llamar al método crearCuenta del DAO");
        ColaboradorDAO.crearCuenta(nombre, apellido, usuario, correo, contrasenia, new Callback<Colaborador>() {
            @Override
            public void onResponse(Call<Colaborador> call, Response<Colaborador> response) {
                Colaborador colaborador = response.body();
                if(colaborador != null){
                    showMessage("Cuenta Creada exitosamente");
                }else{
                    showMessage("Operación no completa, intente más tarde");
                }
            }

            @Override
            public void onFailure(Call<Colaborador> call, Throwable t) {
                t.printStackTrace();
                showMessage("Error en la conexión");
            }
        });
    }

    public void showMessage(String msj){
        Toast.makeText(this, msj, Toast.LENGTH_SHORT).show();
    }
}