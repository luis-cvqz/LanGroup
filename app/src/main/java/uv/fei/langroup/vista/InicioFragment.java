package uv.fei.langroup.vista;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link InicioFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class InicioFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public InicioFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment InicioFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static InicioFragment newInstance(String param1, String param2) {
        InicioFragment fragment = new InicioFragment();
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

    private InicioViewModel viewModel;
    private FragmentInicioBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentInicioBinding.inflate(getLayoutInflater());
        View root = binding.getRoot();
        viewModel = new ViewModelProvider(this).get(InicioViewModel.class);

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