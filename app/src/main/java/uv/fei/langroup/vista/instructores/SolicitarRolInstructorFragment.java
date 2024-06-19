package uv.fei.langroup.vista.instructores;

import android.app.AlertDialog;
import android.content.DialogInterface;
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
import uv.fei.langroup.vista.publicaciones.BuscarPublicacionFragment;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SolicitarRolInstructorFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SolicitarRolInstructorFragment extends Fragment {

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

        if(!tieneSolicitudPendiente("TODO")){
            ArrayList<Idioma> idiomas = recuperarIdiomas();

            if(idiomas != null && !idiomas.isEmpty()){
                ArrayAdapter<Idioma> adapterIdiomas = new ArrayAdapter<Idioma>(getContext(), android.R.layout.simple_spinner_item, idiomas);
                spinnerIdiomas.setAdapter(adapterIdiomas);
            }

            buttonGuardar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(!editTextMotivo.getText().toString().isEmpty() && !editTextContenido.getText().toString().isEmpty()){
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
                                        solicitud.setIdIdioma(idiomaSeleccionado.getIdIdioma());
                                        solicitud.setIdColaborador("TODO");

                                        SolicitudDAO.crearSolicitud(solicitud, new Callback<Solicitud>() {
                                            @Override
                                            public void onResponse(Call<Solicitud> call, Response<Solicitud> response) {
                                                if(response.isSuccessful()){
                                                    Toast.makeText(getContext(), "Se guardó la solicitud", Toast.LENGTH_LONG);
                                                    regresar();
                                                }else{
                                                    Log.e("Solicitud", "Error en la respuesta: " + response.code());
                                                }
                                            }

                                            @Override
                                            public void onFailure(Call<Solicitud> call, Throwable t) {
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
                }
            });
        }else{
            Toast.makeText(getContext(), "Ya tienes una solicitud pendiente", Toast.LENGTH_LONG);
            regresar();
        }

        return root;
    }

    private ArrayList<Idioma> recuperarIdiomas(){
        ArrayList<Idioma> idiomas = new ArrayList<>();
        IdiomaDAO.obtenerIdiomas(new Callback<ArrayList<Idioma>>() {
            @Override
            public void onResponse(Call<ArrayList<Idioma>> call, Response<ArrayList<Idioma>> response) {
                if(response.isSuccessful()){
                    if(response.body() != null && !response.body().isEmpty()){
                        idiomas.addAll(response.body());
                    }
                }else{
                    Log.e("Idioma", "Error en la respuesta: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Idioma>> call, Throwable t) {
                Log.e("Idioma", "Error en la conexión: " + t.getMessage());
            }
        });

        return idiomas;
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