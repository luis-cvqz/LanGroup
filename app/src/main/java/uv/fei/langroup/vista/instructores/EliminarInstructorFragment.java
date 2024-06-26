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
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import uv.fei.langroup.R;
import uv.fei.langroup.modelo.POJO.Colaborador;
import uv.fei.langroup.modelo.POJO.Rol;
import uv.fei.langroup.servicio.DAO.ColaboradorDAO;
import uv.fei.langroup.servicio.DAO.RolDAO;
import uv.fei.langroup.vistamodelo.instructores.EliminarInstructorViewModel;

public class EliminarInstructorFragment extends Fragment {
    private EliminarInstructorViewModel eliminarInstructorViewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_eliminar_instructor, container, false);

        final ImageButton buttonRegresar = root.findViewById(R.id.button_regresar);
        final ListView listViewInstructores = root.findViewById(R.id.list_view_instructores);

        eliminarInstructorViewModel = new ViewModelProvider(this).get(EliminarInstructorViewModel.class);
        eliminarInstructorViewModel.getInstructores().observe(getViewLifecycleOwner(), new Observer<ArrayList<Colaborador>>() {
            @Override
            public void onChanged(ArrayList<Colaborador> instructores) {
                ArrayAdapter<Colaborador> adapterInstructores = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, instructores);
                listViewInstructores.setAdapter(adapterInstructores);
            }
        });

        eliminarInstructorViewModel.getCodigo().observe(getViewLifecycleOwner(), new Observer<Integer>() {
            @Override
            public void onChanged(Integer codigo) {
                if(codigo >= 400 && codigo < 500){
                    Toast.makeText(getContext(), "No hay instructores activos.", Toast.LENGTH_LONG).show();
                }else if(codigo >= 500){
                    Toast.makeText(getContext(), "No hay conexión al servidor.", Toast.LENGTH_LONG).show();
                }
            }
        });

        eliminarInstructorViewModel.fetchInstructores();

        ArrayList<Rol> roles = new ArrayList<>();
        RolDAO.obtenerRoles(new Callback<ArrayList<Rol>>() {
            @Override
            public void onResponse(Call<ArrayList<Rol>> call, Response<ArrayList<Rol>> response) {
                if (response.isSuccessful()) {
                    roles.addAll(response.body());
                }else {
                    Log.e("Rol", "Error en la respuesta: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Rol>> call, Throwable t) {
                Log.e("Rol", "Error en la conexión: " + t.getMessage());
            }
        });

        buttonRegresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                regresar();
            }
        });

        listViewInstructores.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Colaborador colaborador = (Colaborador) parent.getItemAtPosition(position);

                AlertDialog confirmarEliminacion = new AlertDialog.Builder(getActivity())
                        .setPositiveButton("Sí", new DialogInterface.OnClickListener() {
                            Colaborador colaboradorActualizar = new Colaborador();
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                for(Rol rol : roles){
                                    if(rol.getNombre().equalsIgnoreCase("Aprendiz")){
                                        colaboradorActualizar.setRolid(rol.getId());
                                        break;
                                    }
                                }

                                ColaboradorDAO.actualizarRolDeColaborador(colaborador.getColaboradorId(), colaboradorActualizar, new Callback<Colaborador>() {
                                    @Override
                                    public void onResponse(Call<Colaborador> call, Response<Colaborador> response) {
                                        if(response.isSuccessful()){
                                            eliminarInstructorViewModel.fetchInstructores();
                                            Toast.makeText(getContext(), "Se eliminó al instructor", Toast.LENGTH_LONG).show();
                                        }else{
                                            Toast.makeText(getContext(), "Algo salio mal", Toast.LENGTH_LONG).show();
                                            Log.e("Colaborador", "Error en la respuesta: " + response.code());
                                        }
                                    }

                                    @Override
                                    public void onFailure(Call<Colaborador> call, Throwable t) {
                                        Toast.makeText(getContext(), "No hay conexión al servidor.", Toast.LENGTH_LONG).show();
                                        Log.e("Colaborador", "Error en la conexión: " + t.getMessage());
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
                        .setTitle("Eliminar instructor")
                        .setMessage("¿Deseas eliminar a " + colaborador.getUsuario() + " como instructor?")
                        .create();

                confirmarEliminacion.show();
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