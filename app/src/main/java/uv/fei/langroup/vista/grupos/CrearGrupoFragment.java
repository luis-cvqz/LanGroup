package uv.fei.langroup.vista.grupos;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import uv.fei.langroup.R;
import uv.fei.langroup.databinding.FragmentCrearGrupoBinding;
import uv.fei.langroup.modelo.POJO.Idioma;
import uv.fei.langroup.servicio.DAO.IdiomaDAO;
import uv.fei.langroup.vista.publicaciones.BuscarPublicacionFragment;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CrearGrupoFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CrearGrupoFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public CrearGrupoFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CrearGrupoFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CrearGrupoFragment newInstance(String param1, String param2) {
        CrearGrupoFragment fragment = new CrearGrupoFragment();
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

    FragmentCrearGrupoBinding binding;
    private ArrayList<Idioma> idiomas;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentCrearGrupoBinding.inflate(getLayoutInflater());
        View root = binding.getRoot();

        final ImageButton buttonRegresar = binding.buttonRegresar;
        final Spinner spinnerIdiomas = binding.spinnerIdiomas;
        final Button buttonGuardar = binding.buttonGuardar;

        buttonRegresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager manager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = manager.beginTransaction();
                FragmentTransaction replace = fragmentTransaction.replace(R.id.frame_layout, new BuscarPublicacionFragment());
                fragmentTransaction.commit();
            }
        });

        IdiomaDAO.obtenerIdiomas(new Callback<ArrayList<Idioma>>() {
            @Override
            public void onResponse(Call<ArrayList<Idioma>> call, Response<ArrayList<Idioma>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    idiomas = response.body();
                    llenarSpinner(spinnerIdiomas, idiomas);
                } else {
                    Log.e("CrearGrupoFragment", "Error en la respuesta:" + response.code());
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Idioma>> call, Throwable t) {
                Log.e("CrearGrupoFragment", "Error en la conexión: " + t.getMessage());
            }
        });

        return root;
    }

    private void llenarSpinner(Spinner spinner, ArrayList<Idioma> idiomas) {
        ArrayAdapter<Idioma> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, idiomas);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
    }

    private boolean camposVacios() {
        return true;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}