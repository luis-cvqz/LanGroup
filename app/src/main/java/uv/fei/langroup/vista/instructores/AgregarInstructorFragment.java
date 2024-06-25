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


public class AgregarInstructorFragment extends Fragment {
    private AgregarInstructorViewModel agregarInstructorViewModel;
    private Colaborador colaborador;

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

        agregarInstructorViewModel.getCodigo().observe(getViewLifecycleOwner(), new Observer<Integer>() {
            @Override
            public void onChanged(Integer codigo) {
                if(codigo >= 400 && codigo < 500){
                    Toast.makeText(getContext(), "No hay solicitudes pendientes.", Toast.LENGTH_LONG).show();
                }else if(codigo >= 500){
                    Toast.makeText(getContext(), "No hay conexión al servidor.", Toast.LENGTH_LONG).show();
                }
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
                    Log.e("Solicitud", "Error en la respuesta: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Solicitud>> call, Throwable t) {
                Log.e("Solicitud", "Error en la conexión: " + t.getMessage());
            }
        });

        RolDAO.obtenerRoles(new Callback<ArrayList<Rol>>() {
            @Override
            public void onResponse(Call<ArrayList<Rol>> call, Response<ArrayList<Rol>> response) {
                if(response.isSuccessful()){
                    roles.addAll(response.body());
                }else{
                    Log.e("Rol", "Error en la respuesta: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Rol>> call, Throwable t) {
                Log.e("Rol", "Error en la conexión: " + t.getMessage());
            }
        });

        listViewAprendices.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                colaborador = (Colaborador) parent.getItemAtPosition(position);
            }
        });

        buttonVerSolicitud.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager manager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = manager.beginTransaction();
                FragmentTransaction replace = fragmentTransaction.replace(R.id.frame_layout, new VerSolicitudFragment(colaborador));
                fragmentTransaction.commit();
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
                                    if(solicitud.getColaborador().getColaboradorId().equalsIgnoreCase(colaborador.getColaboradorId())){
                                        solicitudActualizar = solicitud;
                                        break;
                                    }
                                }

                                solicitudActualizar.setEstado("Aceptado");

                                SolicitudDAO.actualizarSolicitud(solicitudActualizar.getSolicitudId(), solicitudActualizar, new Callback<Solicitud>() {
                                    @Override
                                    public void onResponse(Call<Solicitud> call, Response<Solicitud> response) {
                                        if(response.isSuccessful()){
                                            Colaborador colaboradorActualizar = new Colaborador();

                                            for(Rol rol : roles){
                                                if(rol.getNombre().equalsIgnoreCase("Instructor")){
                                                    colaboradorActualizar.setRolid(rol.getId());
                                                    break;
                                                }
                                            }

                                            ColaboradorDAO.actualizarRolDeColaborador(colaborador.getColaboradorId(), colaboradorActualizar, new Callback<Colaborador>() {
                                                @Override
                                                public void onResponse(Call<Colaborador> call, Response<Colaborador> response) {
                                                    if(response.isSuccessful()){
                                                        agregarInstructorViewModel.fetchAprendicesConSolicitudPendiente();
                                                        Toast.makeText(getContext(), "Se aceptó al aprendiz", Toast.LENGTH_LONG).show();
                                                    }else{
                                                        Toast.makeText(getContext(), "Algo salio mal.", Toast.LENGTH_LONG).show();
                                                        Log.e("Colaborador", "Error en la respuesta: " + response.code());
                                                    }
                                                }

                                                @Override
                                                public void onFailure(Call<Colaborador> call, Throwable t) {
                                                    Toast.makeText(getContext(), "No hay conexión al servidor.", Toast.LENGTH_LONG).show();
                                                    Log.e("Colaborador", "Error en la conexión: " + t.getMessage());
                                                }
                                            });
                                        }else{
                                            Toast.makeText(getContext(), "Algo salió mal", Toast.LENGTH_LONG).show();
                                            Log.e("Solicitud", "Error en la respuesta: " + response.code());
                                        }
                                    }

                                    @Override
                                    public void onFailure(Call<Solicitud> call, Throwable t) {
                                        Toast.makeText(getContext(), "No hay conexión al servidor.", Toast.LENGTH_LONG).show();
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
                                    if(solicitud.getColaborador().getColaboradorId().equalsIgnoreCase(colaborador.getColaboradorId())){
                                        solicitudActualizar = solicitud;
                                        break;
                                    }
                                }

                                solicitudActualizar.setEstado("Rechazado");

                                SolicitudDAO.actualizarSolicitud(solicitudActualizar.getSolicitudId(), solicitudActualizar, new Callback<Solicitud>() {
                                    @Override
                                    public void onResponse(Call<Solicitud> call, Response<Solicitud> response) {
                                        if(response.isSuccessful()){
                                            agregarInstructorViewModel.fetchAprendicesConSolicitudPendiente();
                                            Toast.makeText(getContext(), "Se rechazó al aprendiz", Toast.LENGTH_LONG).show();
                                        }else{
                                            Toast.makeText(getContext(), "Algo salió mal", Toast.LENGTH_LONG).show();
                                            Log.e("Solicitud", "Error en la respuesta: " + response.code());
                                        }
                                    }

                                    @Override
                                    public void onFailure(Call<Solicitud> call, Throwable t) {
                                        Toast.makeText(getContext(), "No hay conexión al servidor.", Toast.LENGTH_LONG).show();
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