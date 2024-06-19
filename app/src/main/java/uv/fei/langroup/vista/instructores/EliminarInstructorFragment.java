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

import com.google.common.collect.Table;

import org.checkerframework.checker.units.qual.C;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import uv.fei.langroup.R;
import uv.fei.langroup.modelo.POJO.Colaborador;
import uv.fei.langroup.modelo.POJO.Rol;
import uv.fei.langroup.servicio.DAO.ColaboradorDAO;
import uv.fei.langroup.servicio.DAO.RolDAO;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link EliminarInstructorFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EliminarInstructorFragment extends Fragment {

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
        final Button buttonEliminar = root.findViewById(R.id.button_eliminar);
        final TableLayout tableInstructores = root.findViewById(R.id.table_instructores);

        ArrayList<Colaborador> instructores = obtenerInstructores();
        ArrayList<Rol> roles = obtenerRoles();
        String rolAprendizId = "";
        final int[] indiceSeleccionado = {0};

        if(roles != null && !roles.isEmpty()){
            for(Rol rol : roles){
                if(rol.getNombre().equalsIgnoreCase("Aprendiz")){
                    rolAprendizId = rol.getId();
                    break;
                }
            }
        }

        if(!instructores.isEmpty()){
            for(Colaborador instructor : instructores){
                TableRow fila = new TableRow(this.getContext());
                fila.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

                TextView textViewUsuario = new TextView(this.getContext());
                textViewUsuario.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                textViewUsuario.setTextSize(16);
                textViewUsuario.setText(instructor.getUsuario());

                fila.addView(textViewUsuario);

                TextView textViewCorreo = new TextView(this.getContext());
                textViewCorreo.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                textViewCorreo.setTextSize(16);
                textViewCorreo.setText(instructor.getCorreo());

                fila.addView(textViewCorreo);

                tableInstructores.addView(fila);
            }

            for(int i = 0; i < tableInstructores.getChildCount(); i++){
                final TableRow fila = (TableRow) tableInstructores.getChildAt(i);

                fila.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int indiceFila = tableInstructores.indexOfChild(fila);

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

        String finalRolAprendizId = rolAprendizId;
        buttonEliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog confirmarEliminacion = new AlertDialog.Builder(getActivity())
                        .setPositiveButton("Sí", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                ColaboradorDAO.actualizarRolDeColaborador(instructores.get(indiceSeleccionado[0]).getId(), finalRolAprendizId, new Callback<Colaborador>() {
                                    @Override
                                    public void onResponse(Call<Colaborador> call, Response<Colaborador> response) {
                                        if(response.isSuccessful()){
                                            Toast.makeText(getContext(), "Se eliminó al instructor", Toast.LENGTH_LONG);
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
                            }
                        })
                        .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        })
                        .setTitle("Eliminar instructor")
                        .setMessage("¿Deseas eliminar a {} como instructor?")
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

    private ArrayList<Colaborador> obtenerInstructores(){
        ArrayList<Colaborador> instructores = new ArrayList<>();

        ColaboradorDAO.obtenerColaboradoresPorNombreRol("Instructor", new Callback<ArrayList<Colaborador>>() {
            @Override
            public void onResponse(Call<ArrayList<Colaborador>> call, Response<ArrayList<Colaborador>> response) {
                if(response.isSuccessful()){
                    if(response.body() != null && !response.body().isEmpty()){
                        instructores.addAll(response.body());
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

        return instructores;
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