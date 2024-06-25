package uv.fei.langroup.vista.publicaciones;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
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

import java.io.File;
import java.util.ArrayList;

import uv.fei.langroup.R;
import uv.fei.langroup.databinding.FragmentAgregarPublicacionBinding;
import uv.fei.langroup.modelo.POJO.Grupo;
import uv.fei.langroup.modelo.POJO.Publicacion;
import uv.fei.langroup.utilidades.SesionSingleton;
import uv.fei.langroup.vistamodelo.publicaciones.AgregarPublicacionViewModel;

public class AgregarPublicacionFragment extends Fragment {
    FragmentAgregarPublicacionBinding binding;
    private AgregarPublicacionViewModel viewModel;
    ActivityResultLauncher<Intent> resultLauncher;
    private Uri imagenPublicacionURI = null;
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
            public void onClick(View v) { regresar(); }
        });

        buttonSeleccionarImagen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /* Registers a photo picker activity launcher in single-select mode.
                ActivityResultLauncher<PickVisualMediaRequest> pickMedia =
                        registerForActivityResult(new PickVisualMedia(), uri -> {
                            // Callback is invoked after the user selects a media item or closes the
                            // photo picker.
                            if (uri != null) {
                                Log.d("PhotoPicker", "Selected URI: " + uri);
                            } else {
                                Log.d("PhotoPicker", "No media selected");
                            }
                        });

                // Launch the photo picker and let the user choose only images.
                pickMedia.launch(new PickVisualMediaRequest.Builder()
                        .setMediaType(PickVisualMedia.ImageOnly.INSTANCE)
                        .build());*/
                seleccionarImagen();
            }
        });

        buttonPublicar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (spinnerGrupos.getSelectedItem() == null ||  editTextTituloPublicacion.getText().toString().isEmpty() || editTextDescripcionPublicacion.getText().toString().isEmpty() || imagenPublicacionURI == null)
                    mostrarToast("Debe llenar todos los campos");

                AlertDialog confirmacionAgregarPublicacion = new AlertDialog.Builder(getActivity()).setPositiveButton("Si", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        crearPublicacion((Grupo) spinnerGrupos.getSelectedItem(), editTextTituloPublicacion.getText().toString(), editTextDescripcionPublicacion.getText().toString());
                        //crearArchivoMutimedia(imagenPublicacionURI);
                    }
                }).setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).setTitle("Agregar Publicación").setMessage("¿Agregar publicacion al grupo " + spinnerGrupos.getSelectedItem().toString() + "?").create();
                confirmacionAgregarPublicacion.show();
            }
        });

        registerResult(imageViewArchivo);

        return root;
    }

    private void llenarSpinner(Spinner spinnerGrupos) {
        viewModel.fetchGruposPorColaborador(SesionSingleton.getInstance().getColaborador().getColaboradorId());

        viewModel.getGrupos().observe(getViewLifecycleOwner(), new Observer<ArrayList<Grupo>>() {
            @Override
            public void onChanged(ArrayList<Grupo> grupos) {
                ArrayAdapter<Grupo> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, grupos);
                spinnerGrupos.setAdapter(adapter);
            }
        });

        viewModel.getCodigoGrupos().observe(getViewLifecycleOwner(), new Observer<Integer>() {
            @Override
            public void onChanged(Integer codigo) {
                if (codigo == 404){
                    mostrarToast("Debes pertenecer a un grupo para crear una publicaición.");
                } else if (codigo >= 500) {
                    mostrarToast("No se pudieron recuperar sus grupos, intentelo más tarde.");
                }
            }
        });
    }

    private void seleccionarImagen() {
        //Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        Intent intent = new Intent(MediaStore.ACTION_PICK_IMAGES);
        resultLauncher.launch(intent);
    }

    private void registerResult(ImageView imageViewArchivo) {
        resultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        imagenPublicacionURI = result.getData().getData();
                        imageViewArchivo.setImageURI(imagenPublicacionURI);
                    }
                }
        );
    }

    private void crearTransaccionPublicacionImagen(Grupo grupo, String titulo, String descripcion, Uri imagen) {

    }

    private void crearPublicacion(Grupo grupo, String titulo, String descripcion) {
        Publicacion nuevaPublicacion = new Publicacion();
        nuevaPublicacion.setGrupoId(grupo.getId());
        nuevaPublicacion.setTitulo(titulo);
        nuevaPublicacion.setDescripcion(descripcion);
        nuevaPublicacion.setColaboradorId(SesionSingleton.getInstance().getColaborador().getColaboradorId());

        viewModel.fetchCrearPublicacion(nuevaPublicacion);

        viewModel.getCodigoPublicacion().observe(getViewLifecycleOwner(), codigo -> {
            if (esCodigoExitoso(codigo)) {
                mostrarToast("Publicación agregada correctamente.");
            } else {
                mostrarToast("Ocurrió un problema al agregar la publicación.");
            }
        });
    }

    private void crearArchivoMutimedia(Uri imagenUri) {
        File imagen = new File(getPathFromUri(imagenUri));
        String pubid = "1133bddd-2cfc-426f-a121-d802d8f2218a";

        viewModel.fetchCrearArchivoMultimedia(imagen, pubid);
        viewModel.getCodigoArchivo().observe(getViewLifecycleOwner(), codigo -> {
            if (esCodigoExitoso(codigo)) {
                mostrarToast("Archivo agregado correctamente.");
            } else {
                mostrarToast("Ocurrió un problema al agregar el archivo.");
            }
        });
    }

    private void mostrarToast(String msj){
        Toast.makeText(getContext(), msj, Toast.LENGTH_SHORT).show();
    }

    private boolean esCodigoExitoso(int code) {
        return code >= 200 && code < 300;
    }

    private void regresar() {
        FragmentManager manager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = manager.beginTransaction();
        FragmentTransaction replace = fragmentTransaction.replace(R.id.frame_layout, new BuscarPublicacionFragment());
        fragmentTransaction.commit();
    }

    private String getPathFromUri(Uri uri) {
        String[] filePathColumn = { MediaStore.Images.Media.DATA };
        Cursor cursor = requireActivity().getContentResolver().query(uri, filePathColumn, null, null, null);
        cursor.moveToFirst();
        int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
        String picturePath = cursor.getString(columnIndex);
        cursor.close();

        return picturePath;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}