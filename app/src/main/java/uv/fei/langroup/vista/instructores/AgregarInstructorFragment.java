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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import org.checkerframework.checker.units.qual.A;

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
import uv.fei.langroup.vistamodelo.instructores.AgregarInstructorViewModel;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AgregarInstructorFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AgregarInstructorFragment extends Fragment {
    private AgregarInstructorViewModel agregarInstructorViewModel;

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
        final ListView listViewAprendices = root.findViewById(R.id.list_view_aprendices);

        agregarInstructorViewModel = new ViewModelProvider(this).get(AgregarInstructorViewModel.class);
        agregarInstructorViewModel.getAprendicesConSolicitudPendiente().observe(getViewLifecycleOwner(), new Observer<ArrayList<Colaborador>>() {
            @Override
            public void onChanged(ArrayList<Colaborador> aprendices) {
                ArrayAdapter<Colaborador> adapterAprendices = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, aprendices);
                listViewAprendices.setAdapter(adapterAprendices);
            }
        });
        agregarInstructorViewModel.fetchAprendicesConSolicitudPendiente();

        ArrayList<Solicitud> solicitudesPendientes = new ArrayList<>();
        ArrayList<Rol> roles = new ArrayList<>();

        SolicitudDAO.obtenerSolicitudesPorEstado("Pendiente", new Callback<ArrayList<Solicitud>>() {
            @Override
            public void onResponse(Call<ArrayList<Solicitud>> call, Response<ArrayList<Solicitud>> response) {
                if(response.isSuccessful()){
                    solicitudesPendientes.addAll(response.body());
                }else{
                    //TODO mostrar mensaje de conexión fallida
                    Log.e("Solicitud", "Error en la respuesta: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Solicitud>> call, Throwable t) {
                //TODO mostrar mensaje de conexión fallida
                Log.e("Solicitud", "Error en la conexión: " + t.getMessage());
            }
        });

        RolDAO.obtenerRoles(new Callback<ArrayList<Rol>>() {
            @Override
            public void onResponse(Call<ArrayList<Rol>> call, Response<ArrayList<Rol>> response) {
                if(response.isSuccessful()){
                    roles.addAll(response.body());
                }else{
                    //TODO mostrar mensaje de conexión fallida
                    Log.e("Rol", "Error en la respuesta: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Rol>> call, Throwable t) {
                //TODO mostrar mensaje de conexión fallida
                Log.e("Rol", "Error en la conexión: " + t.getMessage());
            }
        });

        listViewAprendices.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Colaborador colaborador = (Colaborador) parent.getItemAtPosition(position);

                buttonVerSolicitud.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //TODO
                    }
                });

                buttonAceptar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        AlertDialog confirmarAgregacion = new AlertDialog.Builder(getActivity())
                                .setPositiveButton("Sí", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        Solicitud solicitudActualizar = new Solicitud();

                                        for(Solicitud solicitud : solicitudesPendientes){
                                            if(solicitud.getColaborador().getId().equalsIgnoreCase(colaborador.getId())){
                                                solicitudActualizar = solicitud;
                                                break;
                                            }
                                        }

                                        solicitudActualizar.setEstado("Aceptado");

                                        SolicitudDAO.actualizarSolicitud(solicitudActualizar.getSolicitudId(), solicitudActualizar, new Callback<Solicitud>() {
                                            @Override
                                            public void onResponse(Call<Solicitud> call, Response<Solicitud> response) {
                                                if(response.isSuccessful()){
                                                    String rolInstructorId = "";

                                                    for(Rol rol : roles){
                                                        if(rol.getNombre().equalsIgnoreCase("Instructor")){
                                                            rolInstructorId = rol.getNombre();
                                                            break;
                                                        }
                                                    }

                                                    ColaboradorDAO.actualizarRolDeColaborador(colaborador.getId(), rolInstructorId, new Callback<Colaborador>() {
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
                                                            //TODO mostrar mensaje de conexión fallida
                                                            Log.e("Colaborador", "Error en la conexión: " + t.getMessage());
                                                        }
                                                    });
                                                }else{
                                                    //TODO mostrar mensaje de conexión fallida
                                                    Log.e("Solicitud", "Error en la respuesta: " + response.code());
                                                }
                                            }

                                            @Override
                                            public void onFailure(Call<Solicitud> call, Throwable t) {
                                                //TODO mostrar mensaje de conexión fallida
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
                                .setMessage("¿Deseas agregar a " + colaborador.getUsuario() + " como instructor?")
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
                                            if(solicitud.getColaborador().getId().equalsIgnoreCase(colaborador.getId())){
                                                solicitudActualizar = solicitud;
                                                break;
                                            }
                                        }

                                        solicitudActualizar.setEstado("Rechazado");

                                        SolicitudDAO.actualizarSolicitud(solicitudActualizar.getSolicitudId(), solicitudActualizar, new Callback<Solicitud>() {
                                            @Override
                                            public void onResponse(Call<Solicitud> call, Response<Solicitud> response) {
                                                if(response.isSuccessful()){
                                                    Toast.makeText(getContext(), "Se rechazó al aprendiz", Toast.LENGTH_LONG);
                                                }else{
                                                    //TODO mostrar mensaje de conexión fallida
                                                    Toast.makeText(getContext(), "Algo salió mal", Toast.LENGTH_LONG);
                                                    Log.e("Solicitud", "Error en la respuesta: " + response.code());
                                                }
                                            }

                                            @Override
                                            public void onFailure(Call<Solicitud> call, Throwable t) {
                                                //TODO mostrar mensaje de conexión fallida
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
                                .setMessage("¿Deseas rechazar a " + colaborador.getUsuario() + " como instructor?")
                                .create();

                        confirmarRechazo.show();
                    }
                });
            }
        });

        buttonRegresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                regresar();
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
}