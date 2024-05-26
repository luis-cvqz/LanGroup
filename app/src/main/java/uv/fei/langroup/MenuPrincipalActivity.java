package uv.fei.langroup;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

import uv.fei.langroup.publicaciones.PublicacionesFragment;

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

        btnDrawerToggle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.open();
            }
        });

        replaceFragment(new PublicacionesFragment());

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                int itemId = item.getItemId();

                if(itemId == R.id.nav_publicaciones){
                    Toast.makeText(MenuPrincipalActivity.this, "Publicaciones Clicked", Toast.LENGTH_SHORT).show();
                    replaceFragment(new PublicacionesFragment());
                }

                if(itemId == R.id.nav_idiomas){
                    Toast.makeText(MenuPrincipalActivity.this, "Idiomas Clicked", Toast.LENGTH_SHORT).show();

                }

                if(itemId == R.id.nav_ser_instructor){
                    Toast.makeText(MenuPrincipalActivity.this, "Ser instructor Clicked", Toast.LENGTH_SHORT).show();

                }

                if(itemId == R.id.nav_estadisticas){
                    Toast.makeText(MenuPrincipalActivity.this, "Estadisticas Clicked", Toast.LENGTH_SHORT).show();

                }

                if(itemId == R.id.nav_admin_instructores){
                    Toast.makeText(MenuPrincipalActivity.this, "Admin Instructores Clicked", Toast.LENGTH_SHORT).show();

                }

                if(itemId == R.id.nav_admin_moderadores){
                    Toast.makeText(MenuPrincipalActivity.this, "Admin Moderadores Clicked", Toast.LENGTH_SHORT).show();

                }

                if(itemId == R.id.nav_logout){
                    Intent intent = new Intent(MenuPrincipalActivity.this, MainActivity.class);
                    startActivity(intent);
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