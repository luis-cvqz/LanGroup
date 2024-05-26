package uv.fei.langroup;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import uv.fei.langroup.databinding.ActivityCrearCuentaBinding;
import uv.fei.langroup.databinding.ActivityMainBinding;

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
    }
}