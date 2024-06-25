package uv.fei.langroup.vista.idiomas;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import uv.fei.langroup.R;
import uv.fei.langroup.modelo.POJO.Idioma;
import uv.fei.langroup.servicio.DAO.IdiomaDAO;
import uv.fei.langroup.vista.grupos.SeleccionarGruposActivity;

public class SeleccionarIdiomaActivity extends AppCompatActivity {

    private String colaboradorId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seleccionar_idioma);

        // Obtén el colaboradorId desde la Intent
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            colaboradorId = extras.getString("colaboradorId");
        }

        // Verifica si colaboradorId se ha recibido correctamente
        if (colaboradorId != null) {
            Log.d("SeleccionarIdioma", "Colaborador ID recibido: " + colaboradorId);
        } else {
            Log.e("SeleccionarIdioma", "ERROR: Colaborador ID es nulo");
            showMessage("Error: Colaborador ID no recibido");
            finish(); // Finaliza la actividad si el colaboradorId es nulo
            return;
        }

        CheckBox cbEspanol = findViewById(R.id.cb_español);
        CheckBox cbIngles = findViewById(R.id.cb_ingles);
        CheckBox cbFrances = findViewById(R.id.cb_frances);
        Button btnGuardar = findViewById(R.id.btn_guardar);

        // Recuperar los IDs de los idiomas y asignarlos a los CheckBox
        recuperarIdiomas((idiomas) -> {
            for (Idioma idioma : idiomas) {
                switch (idioma.getNombre().toLowerCase()) {
                    case "español":
                        cbEspanol.setTag(idioma.getIdiomaId());
                        break;
                    case "inglés":
                        cbIngles.setTag(idioma.getIdiomaId());
                        break;
                    case "francés":
                        cbFrances.setTag(idioma.getIdiomaId());
                        break;
                }
            }
        });

        btnGuardar.setOnClickListener(v -> {
            List<String> idiomaIds = new ArrayList<>();

            if (cbEspanol.isChecked()) {
                idiomaIds.add((String) cbEspanol.getTag());
            }
            if (cbIngles.isChecked()) {
                idiomaIds.add((String) cbIngles.getTag());
            }
            if (cbFrances.isChecked()) {
                idiomaIds.add((String) cbFrances.getTag());
            }

            if (!idiomaIds.isEmpty()) {
                guardarIdiomas(colaboradorId, idiomaIds);
            } else {
                showMessage("Seleccione al menos un idioma.");
            }
        });
    }

    private void recuperarIdiomas(final IdiomasCallback callback) {
        IdiomaDAO.obtenerIdiomas(new Callback<ArrayList<Idioma>>() {
            @Override
            public void onResponse(Call<ArrayList<Idioma>> call, Response<ArrayList<Idioma>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    callback.onIdiomasRecibidos(response.body());
                } else {
                    showMessage("Error al recuperar los idiomas: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Idioma>> call, Throwable t) {
                showMessage("Error en la conexión: " + t.getMessage());
            }
        });
    }

    private void guardarIdiomas(String colaboradorId, List<String> idiomaIds) {
        IdiomaDAO.agregarIdiomaColaborador(colaboradorId, idiomaIds, new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    showMessage("Idiomas guardados exitosamente");
                    Intent intent = new Intent(SeleccionarIdiomaActivity.this, SeleccionarGruposActivity.class);
                    intent.putExtra("colaboradorId", colaboradorId);
                    startActivity(intent);
                } else {
                    showMessage("Error al guardar los idiomas: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                showMessage("Error en la conexión: " + t.getMessage());
            }
        });
    }

    private void showMessage(String msj) {
        Toast.makeText(this, msj, Toast.LENGTH_SHORT).show();
    }

    private interface IdiomasCallback {
        void onIdiomasRecibidos(List<Idioma> idiomas);
    }
}