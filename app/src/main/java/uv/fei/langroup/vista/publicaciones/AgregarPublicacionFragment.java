package uv.fei.langroup.vista.publicaciones;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import java.util.ArrayList;

import uv.fei.langroup.R;
import uv.fei.langroup.databinding.FragmentAgregarPublicacionBinding;
import uv.fei.langroup.modelo.POJO.Grupo;
import uv.fei.langroup.utilidades.SesionSingleton;
import uv.fei.langroup.vistamodelo.publicaciones.AgregarPublicacionViewModel;

public class AgregarPublicacionFragment extends Fragment {
    FragmentAgregarPublicacionBinding binding;
    private AgregarPublicacionViewModel viewModel;
    ActivityResultLauncher<Intent> resultLauncher;
    private Uri imagenPublicacionURI;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentAgregarPublicacionBinding.inflate(getLayoutInflater());
        View root = binding.getRoot();

        viewModel = new ViewModelProvider(this).get(AgregarPublicacionViewModel.class);

        final ImageButton buttonRegresar = binding.buttonRegresar;
        final Spinner spinnerGrupos = binding.spinnerGrupos;
        final EditText editTextTituloPublicacion = binding.editTextTituloPublicacion;
        final Button buttonSeleccionarImagen = binding.buttonSeleccionarImagen;
        final ImageView imageViewArchivo = binding.imageViewArchivo;
        final EditText editTextDescripcionPublicacion = binding.editTextDescripcionPublicacion;
        final Button buttonPublicar = binding.buttonPublicar;

        llenarSpinner(spinnerGrupos);

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