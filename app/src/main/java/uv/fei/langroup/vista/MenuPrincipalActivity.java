package uv.fei.langroup.vista;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import uv.fei.langroup.R;
import uv.fei.langroup.modelo.POJO.Colaborador;
import uv.fei.langroup.modelo.POJO.Rol;
import uv.fei.langroup.servicio.DAO.ColaboradorDAO;
import uv.fei.langroup.servicio.DAO.RolDAO;
import uv.fei.langroup.utilidades.SesionSingleton;
import uv.fei.langroup.vista.grupos.BuscarGruposFragment;
import uv.fei.langroup.vista.grupos.ConsultarGruposFragment;
import uv.fei.langroup.vista.instructores.AdministrarInstructoresFragment;
import uv.fei.langroup.vista.instructores.SolicitarRolInstructorFragment;
import uv.fei.langroup.vista.publicaciones.BuscarPublicacionFragment;
import uv.fei.langroup.vista.publicaciones.MisEstadisticasFragment;

public class MenuPrincipalActivity extends AppCompatActivity{

    DrawerLayout drawerLayout;
    ImageButton btnDrawerToggle;
    NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_principal);

        drawerLayout = findViewById(R.id.drawerLayout);
        btnDrawerToggle = findViewById(R.id.btn_drawer_toggle);
        navigationView = findViewById(R.id.nav_view);

        final MenuItem navSerInstructor = navigationView.getMenu().findItem(R.id.nav_ser_instructor);
        final MenuItem navAdministrarInstructores = navigationView.getMenu().findItem(R.id.nav_admin_instructores);
        final MenuItem navEstadisticas = navigationView.getMenu().findItem(R.id.nav_estadisticas);
        final MenuItem navPublicaciones = navigationView.getMenu().findItem(R.id.nav_publicaciones);
        final MenuItem navIdiomas = navigationView.getMenu().findItem(R.id.nav_idiomas);

        navIdiomas.setVisible(false);

        replaceFragment(new InicioFragment());

        String correo = SesionSingleton.getInstance().getColaborador().getCorreo();

        ColaboradorDAO.obtenerColaboradorPorCorreo(correo, new Callback<Colaborador>() {
            @Override
            public void onResponse(Call<Colaborador> call, Response<Colaborador> response) {
                if(response.isSuccessful()){
                    RolDAO.obtenerRolPorId(response.body().getRolid(), new Callback<Rol>() {
                        @Override
                        public void onResponse(Call<Rol> call, Response<Rol> response) {
                            if(response.isSuccessful()){
                                if(response.body().getNombre().equalsIgnoreCase("Aprendiz")){
                                    navEstadisticas.setVisible(false);
                                    navAdministrarInstructores.setVisible(false);
                                    navPublicaciones.setVisible(false);
                                }else if(response.body().getNombre().equalsIgnoreCase("Instructor")){
                                    navAdministrarInstructores.setVisible(false);
                                    navSerInstructor.setVisible(false);
                                }else if(response.body().getNombre().equalsIgnoreCase("Administrador")){
                                    navSerInstructor.setVisible(false);
                                }
                            }else{
                                Log.e("MenuPrincipalActivity", "Error en la conexión: " + response.code());
                            }
                        }

                        @Override
                        public void onFailure(Call<Rol> call, Throwable t) {
                            Log.e("MenuPrincipalActivity", "Error en la conexión: " + t.getMessage());
                        }
                    });
                }else{
                    Log.e("MenuPrincipalActivity", "Error en la conexión: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<Colaborador> call, Throwable t) {
                Log.e("MenuPrincipalActivity", "Error en la conexión: " + t.getMessage());
            }
        });

        btnDrawerToggle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.open();
            }
        });

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                int itemId = item.getItemId();

                if(itemId == R.id.nav_inicio){
                    Toast.makeText(MenuPrincipalActivity.this, "Inicio Clicked", Toast.LENGTH_SHORT).show();
                    replaceFragment(new InicioFragment());
                }

                if(itemId == R.id.nav_publicaciones){
                    Toast.makeText(MenuPrincipalActivity.this, "Publicaciones Clicked", Toast.LENGTH_SHORT).show();
                    replaceFragment(new BuscarPublicacionFragment());
                }

                if(itemId == R.id.nav_grupos){
                    Toast.makeText(MenuPrincipalActivity.this, "Grupos Clicked", Toast.LENGTH_SHORT).show();
                    replaceFragment(new ConsultarGruposFragment());
                }

                if(itemId == R.id.nav_idiomas){
                    Toast.makeText(MenuPrincipalActivity.this, "Idiomas Clicked", Toast.LENGTH_SHORT).show();
                }

                if(itemId == R.id.nav_ser_instructor){
                    Toast.makeText(MenuPrincipalActivity.this, "Ser instructor Clicked", Toast.LENGTH_SHORT).show();
                    replaceFragment(new SolicitarRolInstructorFragment());
                }

                if(itemId == R.id.nav_estadisticas){
                    replaceFragment(new MisEstadisticasFragment());
                }

                if(itemId == R.id.nav_admin_instructores){
                    replaceFragment(new AdministrarInstructoresFragment());
                }

                if(itemId == R.id.nav_logout){
                    Intent intent = new Intent(MenuPrincipalActivity.this, MainActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    finish();
                }
                return false;
            }
        });
    }

    private void replaceFragment(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        FragmentTransaction replace = fragmentTransaction.replace(R.id.frame_layout, fragment);
        fragmentTransaction.commit();
    }
}