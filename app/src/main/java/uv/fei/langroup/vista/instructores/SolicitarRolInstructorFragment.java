package uv.fei.langroup.vista.instructores;

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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import uv.fei.langroup.R;
import uv.fei.langroup.modelo.POJO.Idioma;
import uv.fei.langroup.modelo.POJO.Solicitud;
import uv.fei.langroup.servicio.DAO.IdiomaDAO;
import uv.fei.langroup.servicio.DAO.SolicitudDAO;
import uv.fei.langroup.utilidades.SesionSingleton;
import uv.fei.langroup.vista.publicaciones.BuscarPublicacionFragment;
import uv.fei.langroup.vistamodelo.instructores.SolicitarRolInstructorViewModel;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SolicitarRolInstructorFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SolicitarRolInstructorFragment extends Fragment {
    private SolicitarRolInstructorViewModel solicitarRolInstructorViewModel;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public SolicitarRolInstructorFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SolicitarRolInstructorFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SolicitarRolInstructorFragment newInstance(String param1, String param2) {
        SolicitarRolInstructorFragment fragment = new SolicitarRolInstructorFragment();
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_solicitar_rol_instructor, container, false);

        final Button buttonGuardar = root.findViewById(R.id.button_guardar);
        final Button buttonAgregarConstancia = root.findViewById(R.id.button_agregar_constancia);
        final Spinner spinnerIdiomas = root.findViewById(R.id.spinner_idioma);
        final EditText editTextMotivo = root.findViewById(R.id.edit_text_causa);
        final EditText editTextContenido = root.findViewById(R.id.edit_text_tipo_contenido);
        final TextView textViewNombreArchivo = root.findViewById(R.id.txt_nombre_archivo);

        solicitarRolInstructorViewModel = new ViewModelProvider(this).get(SolicitarRolInstructorViewModel.class);
        solicitarRolInstructorViewModel.getIdiomas().observe(getViewLifecycleOwner(), new Observer<ArrayList<Idioma>>() {
            @Override
            public void onChanged(ArrayList<Idioma> idiomas) {
                ArrayAdapter<Idioma> adapterIdiomas = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, idiomas);
                spinnerIdiomas.setAdapter(adapterIdiomas);
            }
        });

        solicitarRolInstructorViewModel.getCodigo().observe(getViewLifecycleOwner(), new Observer<Integer>() {
            @Override
            public void onChanged(Integer codigo) {
                if(codigo >= 400 && codigo < 500){
                    Toast.makeText(getContext(), "No hay idiomas registrados.", Toast.LENGTH_LONG).show();
                }else if(codigo >= 500){
                    Toast.makeText(getContext(), "No hay conexión al servidor.", Toast.LENGTH_LONG).show();
                }
            }
        });

        solicitarRolInstructorViewModel.fetchIdiomas();

        buttonGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!editTextMotivo.getText().toString().isEmpty() && !editTextContenido.getText().toString().isEmpty() /*&& !textViewNombreArchivo.getText().toString().isEmpty()*/){
                    AlertDialog confirmarGuardar = new AlertDialog.Builder(getActivity())
                            .setPositiveButton("Sí", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Solicitud solicitud = new Solicitud();
                                    solicitud.setEstado("Pendiente");
                                    solicitud.setMotivo(editTextMotivo.getText().toString());
                                    solicitud.setContenido(editTextContenido.getText().toString());
                                    solicitud.setNombreArchivo(textViewNombreArchivo.getText().toString());
                                    Idioma idiomaSeleccionado = (Idioma) spinnerIdiomas.getSelectedItem();
                                    solicitud.setIdiomaid(idiomaSeleccionado.getIdiomaId());
                                    solicitud.setColaboradorid(SesionSingleton.getInstance().getColaborador().getColaboradorId());

                                    SolicitudDAO.crearSolicitud(solicitud, new Callback<Solicitud>() {
                                        @Override
                                        public void onResponse(Call<Solicitud> call, Response<Solicitud> response) {
                                            if(response.isSuccessful()){
                                                Toast.makeText(getContext(), "Se guardó la solicitud", Toast.LENGTH_LONG).show();
                                                regresar();
                                            }else if(response.code() == 500){
                                                Toast.makeText(getContext(), "No hay conexión al servidor.", Toast.LENGTH_LONG).show();
                                                Log.e("Solicitud", "Error en la respuesta: " + response.code());
                                            }else{
                                                Toast.makeText(getContext(), "Algo salió mal.", Toast.LENGTH_LONG).show();
                                                Log.e("Solicitud", "Error en la respuesta: " + response.code());
                                            }
                                        }

                                        @Override
                                        public void onFailure(Call<Solicitud> call, Throwable t) {
                                            Toast.makeText(getContext(), "Algo salió mal.", Toast.LENGTH_LONG).show();
                                            Log.e("Solicitud", "Error en la conexión: " + t.getMessage());
                                        }
                                    });
                                }
                            })
                            .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            })
                            .setTitle("Guardar solicitud")
                            .setMessage("¿Desea guardar la solicitud?")
                            .create();

                    confirmarGuardar.show();
                }else{
                    Toast.makeText(getContext(), "Debe llenar todos los campos", Toast.LENGTH_LONG);
                }
            }
        });

        buttonAgregarConstancia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO
                Toast.makeText(getContext(), "Función no disponible.", Toast.LENGTH_SHORT).show();
            }
        });

        return root;
    }

    private void regresar(){
        FragmentManager manager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = manager.beginTransaction();
        FragmentTransaction replace = fragmentTransaction.replace(R.id.frame_layout, new BuscarPublicacionFragment());
        fragmentTransaction.commit();
    }

    private boolean tieneSolicitudPendiente(String colaboradorId){
        final boolean[] tieneSolicitudPendiente = {false};

        SolicitudDAO.obtenerSolicitudesPorColaboradorId(colaboradorId, new Callback<ArrayList<Solicitud>>() {
            @Override
            public void onResponse(Call<ArrayList<Solicitud>> call, Response<ArrayList<Solicitud>> response) {
                if(response.isSuccessful()){
                    if(response.body() != null && !response.body().isEmpty()){
                        for(Solicitud solicitud : response.body()){
                            if(solicitud.getEstado().equalsIgnoreCase("Pendiente")){
                                tieneSolicitudPendiente[0] = true;
                                break;
                            }
                        }
                    }
                }else{
                    Log.e("Solicitud", "Error en la respuesta: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Solicitud>> call, Throwable t) {
                Log.e("Solicitud", "Error en la conexión: " + t.getMessage());
            }
        });

        return tieneSolicitudPendiente[0];
    }
}