package uv.fei.langroup.vista.idiomas;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import uv.fei.langroup.R;
import uv.fei.langroup.servicio.DAO.IdiomaDAO;

public class SeleccionarIdiomaActivity extends AppCompatActivity {

    private String colaboradorId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seleccionar_idioma);

        colaboradorId = getIntent().getStringExtra("colaboradorId");

        CheckBox cbEspanol = findViewById(R.id.cb_español);
        CheckBox cbIngles = findViewById(R.id.cb_ingles);
        CheckBox cbFrances = findViewById(R.id.cb_frances);
        Button btnGuardar = findViewById(R.id.btn_guardar);

        btnGuardar.setOnClickListener(v -> {
            StringBuilder nombresIdiomas = new StringBuilder();

            if (cbEspanol.isChecked()) {
                nombresIdiomas.append("Español,");
            }
            if (cbIngles.isChecked()) {
                nombresIdiomas.append("Inglés,");
            }
            if (cbFrances.isChecked()) {
                nombresIdiomas.append("Francés,");
            }

            if (nombresIdiomas.length() > 0) {
                nombresIdiomas.setLength(nombresIdiomas.length() - 1); // Quitar la última coma
                guardarIdiomas(colaboradorId, nombresIdiomas.toString());
            } else {
                Toast.makeText(this, "Seleccione al menos un idioma.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void guardarIdiomas(String colaboradorId, String nombresIdiomas) {
        IdiomaDAO.agregarIdiomaColaborador(colaboradorId, nombresIdiomas, new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(SeleccionarIdiomaActivity.this, "Idiomas guardados exitosamente", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(SeleccionarIdiomaActivity.this, "Error al guardar los idiomas: " + response.code(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(SeleccionarIdiomaActivity.this, "Error en la conexión: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}