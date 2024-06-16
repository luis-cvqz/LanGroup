package uv.fei.langroup.vista.cuenta;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import uv.fei.langroup.MainActivity;
import uv.fei.langroup.databinding.ActivityCrearCuentaBinding;

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