package uv.fei.langroup.vistamodelo.publicaciones;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.io.File;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import uv.fei.langroup.modelo.POJO.ArchivoMultimedia;
import uv.fei.langroup.modelo.POJO.Grupo;
import uv.fei.langroup.modelo.POJO.Publicacion;
import uv.fei.langroup.modelo.POJO.PublicacionConArchivo;
import uv.fei.langroup.servicio.DAO.ArchivoMultimediaDAO;
import uv.fei.langroup.servicio.DAO.GrupoDAO;
import uv.fei.langroup.servicio.DAO.PublicacionDAO;


public class AgregarPublicacionViewModel extends ViewModel {
    private MutableLiveData<ArrayList<Grupo>> grupos;
    private MutableLiveData<Integer> codigoGrupos;
    private MutableLiveData<Integer> codigoPublicacion;
    private MutableLiveData<Integer> codigoArchivo;

    public AgregarPublicacionViewModel() {
        grupos = new MutableLiveData<>();
        codigoGrupos = new MutableLiveData<>();
        codigoPublicacion = new MutableLiveData<>();
        codigoArchivo = new MutableLiveData<>();
    }

    public LiveData<ArrayList<Grupo>> getGrupos() { return grupos; }
    public LiveData<Integer> getCodigoGrupos() { return codigoGrupos; }
    public LiveData<Integer> getCodigoPublicacion() { return codigoPublicacion; }
    public LiveData<Integer> getCodigoArchivo() { return codigoArchivo; }


    public void fetchGruposPorColaborador(String colaboradorID) {
        GrupoDAO.obtenerGruposPorColaborador(colaboradorID, new Callback<ArrayList<Grupo>>() {
            @Override
            public void onResponse(Call<ArrayList<Grupo>> call, Response<ArrayList<Grupo>> response) {
                if(response.isSuccessful()){
                    grupos.setValue(response.body());
                    codigoGrupos.setValue(response.code());
                }else{
                    codigoGrupos.setValue(response.code());
                    Log.e("InicioViewModel", "Código: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Grupo>> call, Throwable t) {
                codigoGrupos.setValue(500);
                Log.e("InicioViewModel", "Error en la conexión: " + t.getMessage());
            }
        });
    }

    public void fetchTransaccionPublicacionArchivoMultimedia(Publicacion publicacion, File imagen) {
        PublicacionDAO.crearPublicacionConImagen(imagen, publicacion, new Callback<PublicacionConArchivo>() {
            @Override
            public void onResponse(Call<PublicacionConArchivo> call, Response<PublicacionConArchivo> response) {
                if (response.isSuccessful()) {
                    codigoPublicacion.setValue(response.code());
                } else {
                    codigoPublicacion.setValue(response.code());
                    Log.e("CrearPublicacion", "Código: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<PublicacionConArchivo> call, Throwable t) {
                codigoPublicacion.setValue(500);
                Log.e("InicioViewModel", "Error en la conexión: " + t.getMessage());
            }
        });
    }

    public void fetchCrearPublicacion(Publicacion publicacion) {
        PublicacionDAO.crearPublicacion(publicacion, new Callback<Publicacion>() {
            @Override
            public void onResponse(Call<Publicacion> call, Response<Publicacion> response) {
                if (response.isSuccessful()) {
                    codigoPublicacion.setValue(response.code());
                } else {
                    codigoPublicacion.setValue(response.code());
                    Log.e("CrearPublicacion", "Código: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<Publicacion> call, Throwable t) {
                codigoPublicacion.setValue(500);
                Log.e("InicioViewModel", "Error en la conexión: " + t.getMessage());
            }
        });
    }

    public void fetchCrearArchivoMultimedia(File imagen, String pubid) {
        ArchivoMultimediaDAO.crearArchivoMultimedia(imagen, pubid, new Callback<ArchivoMultimedia>() {
            @Override
            public void onResponse(Call<ArchivoMultimedia> call, Response<ArchivoMultimedia> response) {

            }

            @Override
            public void onFailure(Call<ArchivoMultimedia> call, Throwable t) {

            }
        });
        
    }
}
