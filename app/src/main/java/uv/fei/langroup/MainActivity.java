package uv.fei.langroup;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

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
            Intent intent = new Intent(this, CrearCuentaActivity.class);
            startActivity(intent);
        });

        binding.btnLogin.setOnClickListener(v->{
            Intent intent = new Intent(this, MenuPrincipalActivity.class);
            startActivity(intent);
        });

        binding.txtRecuperarContrasenia.setOnClickListener(v->{
            Intent intent = new Intent(this, RecuperarContraseniaActivity.class);
            startActivity(intent);
        });
    }
}