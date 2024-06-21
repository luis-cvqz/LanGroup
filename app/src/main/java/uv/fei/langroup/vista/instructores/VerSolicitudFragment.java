package uv.fei.langroup.vista.instructores;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.protobuf.ByteString;
import com.proto.archivos.Archivos;

import org.checkerframework.checker.units.qual.A;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import uv.fei.langroup.R;
import uv.fei.langroup.clientegrpc.clientegrpc.ArchivosServiceCliente;
import uv.fei.langroup.modelo.POJO.ArchivoMultimedia;
import uv.fei.langroup.modelo.POJO.Colaborador;
import uv.fei.langroup.modelo.POJO.Solicitud;
import uv.fei.langroup.vistamodelo.instructores.VerSolicitudViewModel;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link VerSolicitudFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class VerSolicitudFragment extends Fragment {
    private VerSolicitudViewModel verSolicitudViewModel;
    private Colaborador colaborador;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public VerSolicitudFragment() {
        // Required empty public constructor
    }

    public VerSolicitudFragment(Colaborador colaborador){
        this.colaborador = colaborador;
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment VerSolicitudFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static VerSolicitudFragment newInstance(String param1, String param2) {
        VerSolicitudFragment fragment = new VerSolicitudFragment();
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
        View root = inflater.inflate(R.layout.fragment_ver_solicitud, container, false);

        final ImageButton buttonRegresar = root.findViewById(R.id.button_regresar);
        final Button buttonDescargarConstancia = root.findViewById(R.id.button_descargar_constancia);
        final TextView textViewSolicitudDe = root.findViewById(R.id.txt_solicitud_de);
        final TextView textViewNombreArchivo = root.findViewById(R.id.txt_nombre_archivo);
        final TextView textViewIdioma = root.findViewById(R.id.txt_idioma);
        final TextView textViewMotivo = root.findViewById(R.id.txt_motivo);
        final TextView textViewContenido = root.findViewById(R.id.txt_contenido);

        verSolicitudViewModel = new ViewModelProvider(this).get(VerSolicitudViewModel.class);
        verSolicitudViewModel.getSolicitud().observe(getViewLifecycleOwner(), new Observer<Solicitud>() {
            @Override
            public void onChanged(Solicitud solicitud) {
                textViewSolicitudDe.setText("Solicitud de " + colaborador.getUsuario());
                textViewNombreArchivo.setText(solicitud.getNombreArchivo());
                textViewIdioma.setText(solicitud.getIdioma().getNombre());
                textViewMotivo.setText(solicitud.getMotivo());
                textViewContenido.setText(solicitud.getContenido());
            }
        });

        verSolicitudViewModel.getCodigo().observe(getViewLifecycleOwner(), new Observer<Integer>() {
            @Override
            public void onChanged(Integer codigo) {
                if(codigo >= 400 && codigo < 500){
                    Toast.makeText(getContext(), "No hay instructores activos.", Toast.LENGTH_LONG).show();
                }else if(codigo >= 500){
                    Toast.makeText(getContext(), "No hay conexión al servidor.", Toast.LENGTH_LONG).show();
                }
            }
        });

        verSolicitudViewModel.fetchSolicitud(colaborador.getColaboradorId());

        buttonRegresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                regresar();
            }
        });

        buttonDescargarConstancia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO
                Toast.makeText(getContext(), "Función no disponible.", Toast.LENGTH_SHORT).show();
                /*ArchivosServiceCliente archivosServiceCliente = new ArchivosServiceCliente();
                ArchivoMultimedia archivoMultimedia = archivosServiceCliente.descargarConstancia(textViewNombreArchivo.getText().toString());
                guardarArchivo(ByteString.copyFrom(archivoMultimedia.getArchivo()), archivoMultimedia.getNombre());*/
            }
        });

        return root;
    }

    private void regresar(){
        FragmentManager manager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = manager.beginTransaction();
        FragmentTransaction replace = fragmentTransaction.replace(R.id.frame_layout, new AgregarInstructorFragment());
        fragmentTransaction.commit();
    }

    private void guardarArchivo(ByteString archivoBytes, String nombreArchivo) {
        try {
            File directorioDescargas = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
            File archivo = new File(directorioDescargas, nombreArchivo);
            FileOutputStream outputStream = new FileOutputStream(archivo);
            archivoBytes.writeTo(outputStream);
            outputStream.close();

            // Notificar a la galería para que escanee el archivo descargado
            Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
            Uri contentUri = Uri.fromFile(archivo);
            mediaScanIntent.setData(contentUri);
            getActivity().sendBroadcast(mediaScanIntent);

            // Mostrar mensaje de éxito
            Toast.makeText(getContext(), "Archivo descargado en: " + directorioDescargas.getAbsolutePath(), Toast.LENGTH_LONG).show();
        } catch (IOException e) {
            Toast.makeText(getContext(), "Error al descargar el archivo.", Toast.LENGTH_LONG).show();
            Log.e("ClienteGrpc", "Error al guardar el archivo: " + e.getMessage());
        }
    }
}