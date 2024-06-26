package uv.fei.langroup.vista.publicaciones;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import uv.fei.langroup.R;
import uv.fei.langroup.databinding.FragmentBuscarPublicacionBinding;
import uv.fei.langroup.modelo.POJO.Grupo;
import uv.fei.langroup.modelo.POJO.Publicacion;
import uv.fei.langroup.utilidades.SesionSingleton;
import uv.fei.langroup.vista.InicioFragment;
import uv.fei.langroup.vista.grupos.CrearGrupoFragment;
import uv.fei.langroup.vistamodelo.MainViewModel;
import uv.fei.langroup.vistamodelo.publicaciones.BuscarPublicacionViewModel;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link BuscarPublicacionFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BuscarPublicacionFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public BuscarPublicacionFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment BuscarPublicacionFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static BuscarPublicacionFragment newInstance(String param1, String param2) {
        BuscarPublicacionFragment fragment = new BuscarPublicacionFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    private BuscarPublicacionViewModel viewModel;
    private FragmentBuscarPublicacionBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentBuscarPublicacionBinding.inflate(getLayoutInflater());
        View root = binding.getRoot();
        viewModel = new ViewModelProvider(this).get(BuscarPublicacionViewModel.class);

        binding.eqRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
        PublicacionAdapter adapter = new PublicacionAdapter();
        binding.eqRecycler.setAdapter(adapter);

        adapter.setOnButtonComentariosClickListener(new PublicacionAdapter.OnButtonComentariosClickListener() {
            @Override
            public void onButtonComentariosClickListener(Publicacion publicacion, int position) {
                mostrarComentarios(publicacion);
            }
        });

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
                                FragmentTransaction replace = fragmentTransaction.replace(binding.frameLayout.getId(), new BuscarPublicacionFragment());
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

        final Button buttonAgregarPublicacion = binding.buttonAgregarPublicacion;
        final Button buttonCrearGrupo = binding.buttonCrearGrupo;

        buttonAgregarPublicacion.setOnClickListener(v -> {
            FragmentManager manager = getActivity().getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = manager.beginTransaction();
            FragmentTransaction replace = fragmentTransaction.replace(R.id.frame_layout, new AgregarPublicacionFragment());
            fragmentTransaction.commit();
        });

        buttonCrearGrupo.setOnClickListener(v -> {
            FragmentManager manager = getActivity().getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = manager.beginTransaction();
            FragmentTransaction replace = fragmentTransaction.replace(R.id.frame_layout, new CrearGrupoFragment());
            fragmentTransaction.commit();
        });

        mostrarPublicaciones(adapter);

        return root;
    }

    private void mostrarPublicaciones(PublicacionAdapter adapter) {
        String rol = "Administrador";

        viewModel.fetchGrupos(SesionSingleton.getInstance().getColaborador().getColaboradorId(), rol);

        viewModel.getGrupos().observe(getViewLifecycleOwner(), grupos -> {
            ArrayList<Publicacion> publicaciones = new ArrayList<>();
            int totalGrupos = grupos.size();
            int[] gruposProcessed = {0};

            if (grupos.size() == 0) {
                binding.txvMensajeError.setText("No has realizado publicaciones.");
                binding.txvMensajeError.setVisibility(View.VISIBLE);
            }

            for (Grupo grupo : grupos) {
                viewModel.fetchPublicaciones(grupo.getId(), SesionSingleton.getInstance().getColaborador().getColaboradorId());

                viewModel.getPublicaciones().observe(getViewLifecycleOwner(), grupoPublicaciones -> {
                    if (grupoPublicaciones != null) {
                        publicaciones.addAll(grupoPublicaciones);
                    }

                    gruposProcessed[0]++;
                    if (gruposProcessed[0] == totalGrupos) {
                        if (!publicaciones.isEmpty()) {
                            adapter.submitList(publicaciones);
                        } else {
                            binding.txvMensajeError.setText("No has realizado publicaciones.");
                            binding.txvMensajeError.setVisibility(View.VISIBLE);
                        }
                    }
                });
            }
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