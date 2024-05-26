package uv.fei.langroup;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

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
    }
}