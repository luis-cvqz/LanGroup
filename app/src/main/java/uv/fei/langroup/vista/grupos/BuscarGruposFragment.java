package uv.fei.langroup.vista.grupos;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import uv.fei.langroup.R;
import uv.fei.langroup.modelo.POJO.Grupo;
import uv.fei.langroup.vistamodelo.grupos.BuscarGrupoAdapter;
import uv.fei.langroup.vistamodelo.grupos.GrupoViewModel;

public class BuscarGruposFragment extends Fragment {

    private RecyclerView recyclerView;
    private BuscarGrupoAdapter buscarGrupoAdapter;
    private GrupoViewModel grupoViewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_buscar_grupos, container, false);

        recyclerView = view.findViewById(R.id.eq_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        buscarGrupoAdapter = new BuscarGrupoAdapter(new ArrayList<>(), getViewLifecycleOwner());
        recyclerView.setAdapter(buscarGrupoAdapter);

        grupoViewModel = new ViewModelProvider(this).get(GrupoViewModel.class);
        grupoViewModel.getGrupos().observe(getViewLifecycleOwner(), new Observer<ArrayList<Grupo>>() {
            @Override
            public void onChanged(ArrayList<Grupo> grupos) {
                if (grupos != null) {
                    buscarGrupoAdapter.setGrupoList(grupos);
                }
            }
        });

        return view;
    }
}