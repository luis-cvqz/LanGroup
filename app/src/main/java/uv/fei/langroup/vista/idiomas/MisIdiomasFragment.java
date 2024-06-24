package uv.fei.langroup.vista.idiomas;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import java.util.List;
import uv.fei.langroup.R;
import uv.fei.langroup.modelo.POJO.Idioma;
import uv.fei.langroup.utilidades.SesionSingleton;
import uv.fei.langroup.vistamodelo.idiomas.IdiomaAdapter;
import uv.fei.langroup.vistamodelo.idiomas.IdiomaViewModel;

public class MisIdiomasFragment extends Fragment {

    private RecyclerView recyclerView;
    private IdiomaAdapter adapter;
    private IdiomaViewModel viewModel;
    private ProgressBar progressBar;

    public MisIdiomasFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mis_idiomas, container, false);

        recyclerView = view.findViewById(R.id.eq_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        String colaboradorId = SesionSingleton.getInstance().getColaborador().getColaboradorId();

        progressBar = view.findViewById(R.id.progressBar);

        viewModel = new ViewModelProvider(this).get(IdiomaViewModel.class);
        viewModel.getIdiomas(colaboradorId).observe(getViewLifecycleOwner(), new Observer<List<Idioma>>() {
            @Override
            public void onChanged(List<Idioma> idiomas) {
                adapter = new IdiomaAdapter(idiomas, getContext());
                recyclerView.setAdapter(adapter);
                progressBar.setVisibility(View.GONE);
            }
        });

        return view;
    }
}