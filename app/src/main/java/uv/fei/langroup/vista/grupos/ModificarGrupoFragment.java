package uv.fei.langroup.vista.grupos;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import uv.fei.langroup.R;
import uv.fei.langroup.vista.publicaciones.BuscarPublicacionFragment;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ModificarGrupoFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ModificarGrupoFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ModificarGrupoFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ModificarGrupoFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ModificarGrupoFragment newInstance(String param1, String param2) {
        ModificarGrupoFragment fragment = new ModificarGrupoFragment();
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
        View root = inflater.inflate(R.layout.fragment_modificar_grupo, container, false);

        final ImageButton buttonRegresar = root.findViewById(R.id.button_regresar);

        buttonRegresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager manager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = manager.beginTransaction();
                FragmentTransaction replace = fragmentTransaction.replace(R.id.frame_layout, new BuscarPublicacionFragment());
                fragmentTransaction.commit();
            }
        });

        return root;
    }
}