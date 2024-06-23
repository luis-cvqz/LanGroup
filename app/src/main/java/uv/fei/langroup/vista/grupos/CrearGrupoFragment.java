package uv.fei.langroup.vista.grupos;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import uv.fei.langroup.R;
import uv.fei.langroup.databinding.FragmentCrearGrupoBinding;
import uv.fei.langroup.modelo.POJO.Grupo;
import uv.fei.langroup.modelo.POJO.Idioma;
import uv.fei.langroup.servicio.DAO.GrupoDAO;
import uv.fei.langroup.vista.publicaciones.BuscarPublicacionFragment;
import uv.fei.langroup.vistamodelo.grupos.CrearGrupoViewModel;

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
    private CrearGrupoViewModel crearGrupoViewModel;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentCrearGrupoBinding.inflate(getLayoutInflater());
        View root = binding.getRoot();

        crearGrupoViewModel = new ViewModelProvider(this).get(CrearGrupoViewModel.class);

        final ImageButton buttonRegresar = binding.buttonRegresar;
        final Spinner spinnerIdiomas = binding.spinnerIdiomas;
        final EditText editTextNombreGrupo = binding.editTextNombreGrupo;
        final EditText editTextDescripcion = binding.editTextDescripcion;
        final Button buttonGuardar = binding.buttonGuardar;

        buttonRegresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                regresar();
            }
        });

        buttonGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (editTextNombreGrupo.getText().toString().isEmpty() || editTextDescripcion.getText().toString().isEmpty() || spinnerIdiomas.getSelectedItemPosition() == AdapterView.INVALID_POSITION)
                    Toast.makeText(getContext(), "Debe llenar todos los campos", Toast.LENGTH_LONG).show();

                AlertDialog confirmacionCrearGrupo = new AlertDialog.Builder(getActivity()).setPositiveButton("Si", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        crearGrupo(editTextNombreGrupo.getText().toString(), (Idioma) spinnerIdiomas.getSelectedItem(), editTextDescripcion.getText().toString());
                    }
                }).setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).setTitle("Crear Grupo").setMessage("¿Crear grupo?").create();
                confirmacionCrearGrupo.show();
            }
        });

        crearGrupoViewModel.getIdiomas().observe(getViewLifecycleOwner(), new Observer<ArrayList<Idioma>>() {
            @Override
            public void onChanged(ArrayList<Idioma> idiomas) {
                llenarSpinner(spinnerIdiomas, idiomas);
            }
        });

        crearGrupoViewModel.getCodigo().observe(getViewLifecycleOwner(), new Observer<Integer>() {
            @Override
            public void onChanged(Integer codigo) {
                if (codigo == 404) {
                    Toast.makeText(getContext(), "No se encontraron idiomas.", Toast.LENGTH_LONG).show();
                } else if (codigo >= 500) {
                    Toast.makeText(getContext(), "No hay conexión al servidor", Toast.LENGTH_LONG).show();
                }
            }
        });

        crearGrupoViewModel.fetchIdiomas();

        return root;
    }

    private void llenarSpinner(Spinner spinner, ArrayList<Idioma> idiomas) {
        ArrayAdapter<Idioma> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, idiomas);
        spinner.setAdapter(adapter);
    }

    private void crearGrupo(String nombreGrupo, Idioma idioma, String descripcion) {
        Grupo nuevoGrupo = new Grupo();
        nuevoGrupo.setNombre(nombreGrupo);
        nuevoGrupo.setIdIdioma(idioma.getIdiomaId());
        nuevoGrupo.setDescripcion(descripcion);
        GrupoDAO.crearGrupo(nuevoGrupo, new Callback<Grupo>() {
            @Override
            public void onResponse(Call<Grupo> call, Response<Grupo> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(getContext(), "Se creó el grupo.", Toast.LENGTH_LONG).show();
                    regresar();
                } else {
                    Toast.makeText(getContext(), "Ocurrio un momento al crear el grupo.", Toast.LENGTH_LONG).show();
                    Log.e("CrearGrupo","Error en la respuesta: " + response.message() + response.code());
                }
            }

            @Override
            public void onFailure(Call<Grupo> call, Throwable t) {
                Toast.makeText(getContext(), "No hay conexión al servidor.", Toast.LENGTH_LONG).show();
                Log.e("CrearGrupo", "Error en la conexión: " + t.getMessage());
            }
        });
    }

    private void regresar() {
        FragmentManager manager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = manager.beginTransaction();
        FragmentTransaction replace = fragmentTransaction.replace(R.id.frame_layout, new BuscarPublicacionFragment());
        fragmentTransaction.commit();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}