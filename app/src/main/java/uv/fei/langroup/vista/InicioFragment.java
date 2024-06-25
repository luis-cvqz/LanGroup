package uv.fei.langroup.vista;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import uv.fei.langroup.R;
import uv.fei.langroup.databinding.FragmentInicioBinding;
import uv.fei.langroup.modelo.POJO.Grupo;
import uv.fei.langroup.modelo.POJO.Publicacion;
import uv.fei.langroup.utilidades.SesionSingleton;
import uv.fei.langroup.vista.publicaciones.AgregarInteraccionFragment;
import uv.fei.langroup.vista.publicaciones.BuscarPublicacionFragment;
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

        binding.eqRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
        PublicacionAdapter adapter = new PublicacionAdapter();
        adapter.setOnButtonEliminarClickListener(new PublicacionAdapter.OnButtonEliminarClickListener() {
            @Override
            public void onButtonEliminarClickListener(Publicacion publicacion, int position) {
                AlertDialog confirmacionEliminarPublicacion = new AlertDialog.Builder(getContext()).setTitle("Eliminar Publicación").setMessage("¿Estás seguro de que deseas eliminar esta publicación?").setPositiveButton("Eliminar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        viewModel.fetchEliminarPublicacion(publicacion.getId());

                        viewModel.getCodigoEliminarPublicacion().observe(getViewLifecycleOwner(), codigo -> {
                            if (esCodigoExitoso(codigo)) {
                                showMessage("Publicación eliminada correctamente");

                                FragmentManager manager = getActivity().getSupportFragmentManager();
                                FragmentTransaction fragmentTransaction = manager.beginTransaction();
                                FragmentTransaction replace = fragmentTransaction.replace(binding.frameLayout.getId(), new InicioFragment());
                                fragmentTransaction.commit();
                            } else {
                                showMessage("No hay conexión con el servidor. Intenta más tarde.");
                            }
                        });
                    }
                }).setNegativeButton("Cancelar", null).create();

                confirmacionEliminarPublicacion.show();
            }
        });

        adapter.setOnButtonComentariosClickListener(new PublicacionAdapter.OnButtonComentariosClickListener() {
            @Override
            public void onButtonComentariosClickListener(Publicacion publicacion, int position) {
                mostrarComentarios(publicacion);
            }
        });

        binding.eqRecycler.setAdapter(adapter);

        ajustarVisibilidad(adapter);

        progressBar = binding.progressBar;
        progressBar.setVisibility(View.VISIBLE);

        mostrarPublicaciones(adapter);

        return root;
    }

    private void ajustarVisibilidad (PublicacionAdapter adapter) {
        viewModel.fetchRolColaborador(SesionSingleton.getInstance().getColaborador().getRolid());

        viewModel.getRolColaborador().observe(getViewLifecycleOwner(), rol -> {
            if (rol.getNombre().equals("Aprendiz") || rol.getNombre().equals("Instructor")) {
                adapter.setOnButtonEliminarVisibilityListener(new PublicacionAdapter.OnButtonEliminarVisibilityListener() {
                    @Override
                    public boolean setButtonEliminarVisibility(Publicacion publicacion) {
                        return false;
                    }
                });
            }
        });

    }

    private void mostrarPublicaciones(PublicacionAdapter adapter) {
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
                    for (Publicacion publicacion : grupoPublicaciones) {
                        String publicacionColaboradorId = publicacion.getColaborador().getColaboradorId();
                        String colaboradorId = SesionSingleton.getInstance().getColaborador().getColaboradorId();

                        if (publicacionColaboradorId.equals(colaboradorId)) {
                            adapter.setOnButtonEliminarVisibilityListener(new PublicacionAdapter.OnButtonEliminarVisibilityListener() {
                                @Override
                                public boolean setButtonEliminarVisibility(Publicacion publicacion) {
                                    return true;
                                }
                            });
                        }
                    }

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

    private void mostrarComentarios(Publicacion publicacion) {
        AgregarInteraccionFragment fragment = AgregarInteraccionFragment.newInstance(publicacion.getId());

        FragmentManager manager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = manager.beginTransaction();
        FragmentTransaction replace = fragmentTransaction.replace(binding.frameLayout.getId(), fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    private boolean esCodigoExitoso(int code) {
        return code >= 200 && code < 300;
    }
}