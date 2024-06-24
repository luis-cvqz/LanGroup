package uv.fei.langroup.vista;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import uv.fei.langroup.R;
import uv.fei.langroup.databinding.FragmentInicioBinding;
import uv.fei.langroup.modelo.POJO.Grupo;
import uv.fei.langroup.modelo.POJO.Publicacion;
import uv.fei.langroup.utilidades.SesionSingleton;
import uv.fei.langroup.vista.publicaciones.PublicacionAdapter;
import uv.fei.langroup.vistamodelo.InicioViewModel;

public class InicioFragment extends Fragment {

    private InicioViewModel viewModel;
    private FragmentInicioBinding binding;
    private ProgressBar progressBar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentInicioBinding.inflate(getLayoutInflater());
        View root = binding.getRoot();
        viewModel = new ViewModelProvider(this).get(InicioViewModel.class);

        progressBar = root.findViewById(R.id.progressBar);
        progressBar.setVisibility(View.VISIBLE);

        mostrarPublicaciones();

        return root;
    }

    private void mostrarPublicaciones() {
        binding.eqRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
        PublicacionAdapter adapter = new PublicacionAdapter();
        binding.eqRecycler.setAdapter(adapter);

        viewModel.fetchGrupos(SesionSingleton.getInstance().getColaborador().getColaboradorId());

        viewModel.getGrupos().observe(getViewLifecycleOwner(), grupos -> {
            ArrayList<Publicacion> publicaciones = new ArrayList<>();
            int totalGrupos = grupos.size();
            int[] gruposProcessed = {0};

            if (grupos.size() == 0) {
                binding.txvMensajeError.setText("Necesitas unirte a un grupo para ver publicaciones.");
                binding.txvMensajeError.setVisibility(View.VISIBLE);
            }

            for (Grupo grupo : grupos) {
                viewModel.fetchPublicaciones(grupo.getId());

                viewModel.getPublicaciones().observe(getViewLifecycleOwner(), grupoPublicaciones -> {
                    if (grupoPublicaciones != null) {
                        publicaciones.addAll(grupoPublicaciones);
                    }

                    gruposProcessed[0]++;
                    if (gruposProcessed[0] == totalGrupos) {
                        if (!publicaciones.isEmpty()) {
                            adapter.submitList(publicaciones);
                        } else {
                            binding.txvMensajeError.setText("Por el momento no hay publicaciones.");
                            binding.txvMensajeError.setVisibility(View.VISIBLE);
                        }
                    }
                });
            }
            progressBar.setVisibility(View.GONE);
        });

        viewModel.getCodigoGrupo().observe(getViewLifecycleOwner(), codigo -> {
            if (codigo != null) {
                if (!esCodigoExitoso(codigo)) {
                    showMessage("No hay conexión con el servidor. Intenta más tarde.");
                }
            } else {
                showMessage("No hay conexión con el servidor. Intenta más tarde.");
            }
        });
    }
    private void showMessage(String msj){
        Toast.makeText(getContext(), msj, Toast.LENGTH_SHORT).show();
    }

    private boolean esCodigoExitoso(int code) {
        return code >= 200 && code < 300;
    }
}