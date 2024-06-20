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

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link EliminarInstructorFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EliminarInstructorFragment extends Fragment {
    private EliminarInstructorViewModel eliminarInstructorViewModel;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public EliminarInstructorFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment EliminarInstructorFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static EliminarInstructorFragment newInstance(String param1, String param2) {
        EliminarInstructorFragment fragment = new EliminarInstructorFragment();
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

        eliminarInstructorViewModel.fetchInstructores();

        ArrayList<Rol> roles = new ArrayList<>();
        RolDAO.obtenerRoles(new Callback<ArrayList<Rol>>() {
            @Override
            public void onResponse(Call<ArrayList<Rol>> call, Response<ArrayList<Rol>> response) {
                if (response.isSuccessful()) {
                    roles.addAll(response.body());
                }else {
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
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                String rolAprendizId ="";

                                for(Rol rol : roles){
                                    if(rol.getNombre().equalsIgnoreCase("Aprendiz")){
                                        rolAprendizId = rol.getId();
                                        break;
                                    }
                                }

                                ColaboradorDAO.actualizarRolDeColaborador(colaborador.getId(), rolAprendizId, new Callback<Colaborador>() {
                                    @Override
                                    public void onResponse(Call<Colaborador> call, Response<Colaborador> response) {
                                        if(response.isSuccessful()){
                                            Toast.makeText(getContext(), "Se eliminó al instructor", Toast.LENGTH_LONG);
                                        }else{
                                            //TODO mostrar mensaje de conexión fallida
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