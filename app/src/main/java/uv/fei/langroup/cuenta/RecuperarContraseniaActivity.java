package uv.fei.langroup.cuenta;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import uv.fei.langroup.MainActivity;
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