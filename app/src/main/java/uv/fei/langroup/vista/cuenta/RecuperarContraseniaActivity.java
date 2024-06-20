package uv.fei.langroup.vista.cuenta;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import uv.fei.langroup.vista.MainActivity;
import uv.fei.langroup.R;
import uv.fei.langroup.databinding.ActivityRecuperarContraseniaBinding;

public class RecuperarContraseniaActivity extends AppCompatActivity {

    ActivityRecuperarContraseniaBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRecuperarContraseniaBinding.inflate(getLayoutInflater());
        setContentView(R.layout.activity_recuperar_contrasenia);

        binding.buttonRegresar.setOnClickListener(v -> {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        });
    }
}