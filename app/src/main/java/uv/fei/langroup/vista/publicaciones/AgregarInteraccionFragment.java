package uv.fei.langroup.vista.publicaciones;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.ArrayList;

import uv.fei.langroup.databinding.FragmentAgregarInteraccionBinding;
import uv.fei.langroup.modelo.POJO.Interaccion;
import uv.fei.langroup.utilidades.SesionSingleton;
import uv.fei.langroup.vistamodelo.publicaciones.AgregarInteraccionViewModel;

public class AgregarInteraccionFragment extends Fragment {
    public static final String ID_PUBLICACION = "param1";
    private String idPublicacion;

    public AgregarInteraccionFragment() {
    }

    public static AgregarInteraccionFragment newInstance(String param1) {
        AgregarInteraccionFragment fragment = new AgregarInteraccionFragment();
        Bundle args = new Bundle();
        args.putString(ID_PUBLICACION, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            idPublicacion = getArguments().getString(ID_PUBLICACION);
        }
    }

    private FragmentAgregarInteraccionBinding binding;
    private AgregarInteraccionViewModel viewModel;
    private ProgressBar progressBar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentAgregarInteraccionBinding.inflate(getLayoutInflater());
        View root = binding.getRoot();
        viewModel = new ViewModelProvider(this).get(AgregarInteraccionViewModel.class);

        binding.interaccionRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
        InteraccionAdapter adapter = new InteraccionAdapter();
        binding.interaccionRecycler.setAdapter(adapter);

        progressBar = binding.progressBar;
        progressBar.setVisibility(View.VISIBLE);

        binding.etxComentario.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                binding.txvComentariosError.setVisibility(View.GONE);
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().trim().isEmpty()) {
                    binding.buttonEnviar.setAlpha((float) 0.5);
                    binding.buttonEnviar.setEnabled(false);
                } else {
                    binding.buttonEnviar.setAlpha((float) 1.0);
                    binding.buttonEnviar.setEnabled(true);
                }
            }
        });

        binding.buttonRegresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getActivity() != null) {
                    getActivity().getSupportFragmentManager().popBackStack();
                }
            }
        });

        binding.buttonEnviar.setOnClickListener(v -> {
            if (verificarInteraccion()) {
                agregarInteraccion(adapter);
            }
        });

        mostrarInteracciones(adapter);

        return root;
    }

    private void mostrarInteracciones(InteraccionAdapter adapter) {
        viewModel.fetchInteracciones(idPublicacion);

        viewModel.getInteracciones().observe(getViewLifecycleOwner(), interacciones -> {
            if (!interacciones.isEmpty()) {
                for (Interaccion interaccion : interacciones) {
                    String interaccionColaboradorId = interaccion.getColaborador().getColaboradorId();
                    String sesionColaboradorId = SesionSingleton.getInstance().getColaborador().getColaboradorId();

                    if (interaccionColaboradorId.equals(sesionColaboradorId)) {
                        ocultarNuevoComentario();
                        binding.txvMensaje.setText("Ya has realizado un comentario. No puedes realizar otro");
                        binding.txvMensaje.setVisibility(View.VISIBLE);
                    }
                }

                adapter.submitList(interacciones);
            } else {
                binding.txvMensaje.setText("Por el momento no hay comentarios. ¡Puedes agregar uno!");
                binding.txvMensaje.setVisibility(View.VISIBLE);
            }
        });

        progressBar.setVisibility(View.GONE);

        viewModel.getCodigoInteraccion().observe(getViewLifecycleOwner(), codigo -> {
            if (codigo != null) {
                if (!esCodigoExitoso(codigo)) {
                    showMessage("No hay conexión con el servidor. Intenta más tarde.");
                }
            } else {
                showMessage("No hay conexión con el servidor. Intenta más tarde.");
            }
        });
    }

    private boolean verificarInteraccion() {
        boolean esValido = true;

        if (binding.ratingBar.getRating() == 0) {
            esValido = false;
            binding.txvComentariosError.setText("Tienes que seleccionar un valor.");
            binding.txvComentariosError.setVisibility(View.VISIBLE);
        }

        return esValido;
    }

    private Interaccion obtenerInteraccion() {
        Interaccion interaccion = new Interaccion();

        interaccion.setComentario(binding.etxComentario.getText().toString());
        interaccion.setColaboradorid(SesionSingleton.getInstance().getColaborador().getColaboradorId());;
        interaccion.setPublicacionid(idPublicacion);
        interaccion.setValoracion((int) binding.ratingBar.getRating());

        return interaccion;
    }

    private void agregarInteraccion(InteraccionAdapter adapter) {
        Interaccion interaccion = obtenerInteraccion();

        viewModel.fetchAgregarInteraccion(interaccion);

        viewModel.getCodigoAgregarInteraccion().observe(getViewLifecycleOwner(), codigo -> {
            if (esCodigoExitoso(codigo)) {
                mostrarInteracciones(adapter);
                showMessage("Se ha agregado tu comentario ");
            } else {
                showMessage("No hay conexion con el servidor.Intenta más tarde");
            }
        });
    }

    private void ocultarNuevoComentario() {
        binding.ratingBar.setVisibility(View.GONE);
        binding.etxComentario.setVisibility(View.GONE);
        binding.buttonEnviar.setVisibility(View.GONE);
    }

    private void showMessage(String msj){
        Toast.makeText(getContext(), msj, Toast.LENGTH_SHORT).show();
    }

    private boolean esCodigoExitoso(int code) {
        return code >= 200 && code < 300;
    }
}