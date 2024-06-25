package uv.fei.langroup.vista.grupos;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import uv.fei.langroup.R;
import uv.fei.langroup.modelo.POJO.Grupo;
import uv.fei.langroup.servicio.DAO.GrupoDAO;
import uv.fei.langroup.vista.MainActivity;
import uv.fei.langroup.vista.MenuPrincipalActivity;
import uv.fei.langroup.vistamodelo.grupos.BuscarGrupoAdapter;
import uv.fei.langroup.vistamodelo.grupos.GrupoViewModel;
import uv.fei.langroup.vistamodelo.grupos.SeleccionarGruposAdapter;

public class SeleccionarGruposActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private SeleccionarGruposAdapter seleccionarGruposAdapter;
    private GrupoViewModel grupoViewModel;
    private String colaboradorId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seleccionar_grupos);

        Button btn_Continuar = findViewById(R.id.btn_continuar);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            colaboradorId = extras.getString("colaboradorId");
        }

        if (colaboradorId == null) {
            Log.e("SeleccionarGrupos", "ERROR: Colaborador ID es nulo");
            finish();
            return;
        }

        btn_Continuar.setOnClickListener(v->{
            showMessage("Inicie Sesión para continuar");
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        });

        recyclerView = findViewById(R.id.eq_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        seleccionarGruposAdapter = new SeleccionarGruposAdapter(new ArrayList<>(), this, colaboradorId);
        recyclerView.setAdapter(seleccionarGruposAdapter);

        grupoViewModel = new ViewModelProvider(this).get(GrupoViewModel.class);
        grupoViewModel.getGrupos().observe(this, new Observer<ArrayList<Grupo>>() {
            @Override
            public void onChanged(ArrayList<Grupo> grupos) {
                if (grupos != null) {
                    seleccionarGruposAdapter.setGrupoList(grupos);
                }
            }
        });
    }

    private void showMessage(String msj){
        Toast.makeText(this, msj, Toast.LENGTH_SHORT).show();
    }
}