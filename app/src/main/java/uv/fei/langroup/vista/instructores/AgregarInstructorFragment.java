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
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import uv.fei.langroup.R;
import uv.fei.langroup.modelo.POJO.Colaborador;
import uv.fei.langroup.modelo.POJO.Rol;
import uv.fei.langroup.modelo.POJO.Solicitud;
import uv.fei.langroup.servicio.DAO.ColaboradorDAO;
import uv.fei.langroup.servicio.DAO.RolDAO;
import uv.fei.langroup.servicio.DAO.SolicitudDAO;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AgregarInstructorFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AgregarInstructorFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public AgregarInstructorFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AgregarInstructorFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AgregarInstructorFragment newInstance(String param1, String param2) {
        AgregarInstructorFragment fragment = new AgregarInstructorFragment();
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
        View root = inflater.inflate(R.layout.fragment_agregar_instructor, container, false);

        final ImageButton buttonRegresar = root.findViewById(R.id.button_regresar);
        final Button buttonVerSolicitud = root.findViewById(R.id.button_ver_solicitud);
        final Button buttonAceptar = root.findViewById(R.id.button_aceptar);
        final Button buttonRechazar = root.findViewById(R.id.button_rechazar);
        final TableLayout tableAprendices = root.findViewById(R.id.table_aprendices);

        ArrayList<Solicitud> solicitudesPendientes = obtenerSolicitudesPendientes();
        ArrayList<Colaborador> aprendices = obtenerAprendicesConSolicitudPendiente(solicitudesPendientes);
        ArrayList<Rol> roles = obtenerRoles();


        String rolInstructorId = "";
        final int[] indiceSeleccionado = {0};

        if(roles != null && !roles.isEmpty()){
            for(Rol rol : roles){
                if(rol.getNombre().equalsIgnoreCase("Instructor")){
                    rolInstructorId = rol.getId();
                    break;
                }
            }
        }

        if(aprendices != null && !aprendices.isEmpty()){
            for(Colaborador aprendiz : aprendices){
                TableRow fila = new TableRow(this.getContext());
                fila.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

                TextView textViewUsuario = new TextView(this.getContext());
                textViewUsuario.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                textViewUsuario.setTextSize(16);
                textViewUsuario.setText(aprendiz.getUsuario());

                fila.addView(textViewUsuario);

                TextView textViewCorreo = new TextView(this.getContext());
                textViewCorreo.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                textViewCorreo.setTextSize(16);
                textViewCorreo.setText(aprendiz.getCorreo());

                fila.addView(textViewCorreo);

                tableAprendices.addView(fila);
            }

            for(int i = 0; i < tableAprendices.getChildCount(); i++){
                final TableRow fila = (TableRow) tableAprendices.getChildAt(i);

                fila.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int indiceFila = tableAprendices.indexOfChild(fila);

                        indiceSeleccionado[0] = indiceFila;
                        Toast.makeText(getContext(), "Indice seleccionado: " + indiceFila, Toast.LENGTH_SHORT);
                    }
                });
            }
        }

        buttonRegresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                regresar();
            }
        });

        buttonVerSolicitud.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO
            }
        });

        String finalRolInstructorId = rolInstructorId;
        buttonAceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog confirmarAgregacion = new AlertDialog.Builder(getActivity())
                        .setPositiveButton("Sí", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Solicitud solicitudActualizar = new Solicitud();
                                for(Solicitud solicitud : solicitudesPendientes){
                                    if(solicitud.getIdColaborador().equalsIgnoreCase(aprendices.get(indiceSeleccionado[0]).getId())){
                                        solicitudActualizar = solicitud;
                                        break;
                                    }
                                }

                                solicitudActualizar.setEstado("Aceptado");

                                SolicitudDAO.actualizarSolicitud(solicitudActualizar.getId(), solicitudActualizar, new Callback<Solicitud>() {
                                    @Override
                                    public void onResponse(Call<Solicitud> call, Response<Solicitud> response) {
                                        if(response.isSuccessful()){
                                            ColaboradorDAO.actualizarRolDeColaborador(aprendices.get(indiceSeleccionado[0]).getId(), finalRolInstructorId, new Callback<Colaborador>() {
                                                @Override
                                                public void onResponse(Call<Colaborador> call, Response<Colaborador> response) {
                                                    if(response.isSuccessful()){
                                                        Toast.makeText(getContext(), "Se aceptó al aprendiz", Toast.LENGTH_LONG);
                                                    }else{
                                                        Toast.makeText(getContext(), "Algo salio mal", Toast.LENGTH_LONG);
                                                        Log.e("Colaborador", "Error en la respuesta: " + response.code());
                                                    }
                                                }

                                                @Override
                                                public void onFailure(Call<Colaborador> call, Throwable t) {
                                                    Log.e("Colaborador", "Error en la conexión: " + t.getMessage());
                                                }
                                            });
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
                        .setTitle("Agregar instructor")
                        .setMessage("¿Deseas agregar a {} como instructor?")
                        .create();

                confirmarAgregacion.show();
            }
        });

        buttonRechazar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog confirmarRechazo = new AlertDialog.Builder(getActivity())
                        .setPositiveButton("Sí", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Solicitud solicitudActualizar = new Solicitud();
                                for(Solicitud solicitud : solicitudesPendientes){
                                    if(solicitud.getIdColaborador().equalsIgnoreCase(aprendices.get(indiceSeleccionado[0]).getId())){
                                        solicitudActualizar = solicitud;
                                        break;
                                    }
                                }

                                solicitudActualizar.setEstado("Rechazado");

                                SolicitudDAO.actualizarSolicitud(solicitudActualizar.getId(), solicitudActualizar, new Callback<Solicitud>() {
                                    @Override
                                    public void onResponse(Call<Solicitud> call, Response<Solicitud> response) {
                                        if(response.isSuccessful()){
                                            Toast.makeText(getContext(), "Se rechazó al aprendiz", Toast.LENGTH_LONG);
                                        }else{
                                            Toast.makeText(getContext(), "Algo salió mal", Toast.LENGTH_LONG);
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
                        .setTitle("Rechazar instructor")
                        .setMessage("¿Deseas rechazar a {} como instructor?")
                        .create();

                confirmarRechazo.show();
            }
        });

        return root;
    }

    private void regresar(){
        FragmentManager manager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = manager.beginTransaction();
        FragmentTransaction replace = fragmentTransaction.replace(R.id.frame_layout, new AdministrarInstructoresFragment());
        fragmentTransaction.commit();
    }

    private ArrayList<Colaborador> obtenerAprendicesConSolicitudPendiente(ArrayList<Solicitud> solicitudesPendientes){
        ArrayList<Colaborador> aprendices = new ArrayList<>();

        if(solicitudesPendientes != null && !solicitudesPendientes.isEmpty()){
            ColaboradorDAO.obtenerColaboradoresPorNombreRol("Aprendiz", new Callback<ArrayList<Colaborador>>() {
                @Override
                public void onResponse(Call<ArrayList<Colaborador>> call, Response<ArrayList<Colaborador>> response) {
                    if(response.isSuccessful()){
                        if(response.body() != null && !response.body().isEmpty()){
                            for(Colaborador aprendiz : response.body()){
                                for(Solicitud solicitud : solicitudesPendientes){
                                    if(solicitud.getIdColaborador().equalsIgnoreCase(aprendiz.getId())){
                                        aprendices.add(aprendiz);
                                    }
                                }
                            }
                        }
                    }else{
                        Log.e("Colaborador", "Error en la respuesta: " + response.code());
                    }
                }

                @Override
                public void onFailure(Call<ArrayList<Colaborador>> call, Throwable t) {
                    Log.e("Colaborador", "Error en la conexión: " + t.getMessage());
                }
            });
        }

        return aprendices;
    }

    private ArrayList<Solicitud> obtenerSolicitudesPendientes(){
        ArrayList<Solicitud> solicitudes = new ArrayList<>();

        SolicitudDAO.obtenerSolicitudesPorEstado("Pendiente", new Callback<ArrayList<Solicitud>>() {
            @Override
            public void onResponse(Call<ArrayList<Solicitud>> call, Response<ArrayList<Solicitud>> response) {
                if(response.isSuccessful()){
                    if(response.body() != null && !response.body().isEmpty()){
                        solicitudes.addAll(response.body());
                    }
                }else{
                    Log.e("Colaborador", "Error en la respuesta: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Solicitud>> call, Throwable t) {
                Log.e("Colaborador", "Error en la conexión: " + t.getMessage());
            }
        });

        return solicitudes;
    }

    private ArrayList<Rol> obtenerRoles(){
        ArrayList<Rol> roles = new ArrayList<>();

        RolDAO.obtenerRoles(new Callback<ArrayList<Rol>>() {
            @Override
            public void onResponse(Call<ArrayList<Rol>> call, Response<ArrayList<Rol>> response) {
                if(response.isSuccessful()){
                    if(response.body() != null && !response.body().isEmpty()){
                        roles.addAll(response.body());
                    }else{
                        Log.e("Rol", "Error en la respuesta: " + response.code());
                    }
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Rol>> call, Throwable t) {
                Log.e("Rol", "Error en la conexión: " + t.getMessage());
            }
        });

        return roles;
    }
}