package uv.fei.langroup.vistamodelo.publicaciones;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import uv.fei.langroup.modelo.POJO.Grupo;
import uv.fei.langroup.modelo.POJO.Publicacion;
import uv.fei.langroup.servicio.DAO.GrupoDAO;
import uv.fei.langroup.servicio.DAO.PublicacionDAO;

public class BuscarPublicacionViewModel extends ViewModel {
    private MutableLiveData<ArrayList<Publicacion>> publicaciones;
    private MutableLiveData<ArrayList<Grupo>> grupos;
    private MutableLiveData<Integer> codigoPublicacion;
    private MutableLiveData<Integer> codigoGrupo;

    public BuscarPublicacionViewModel() {
        publicaciones = new MutableLiveData<>();
        codigoPublicacion = new MutableLiveData<>();
        grupos = new MutableLiveData<>();
        codigoGrupo = new MutableLiveData<>();
    }

    public LiveData<ArrayList<Publicacion>> getPublicaciones() {
        return publicaciones;
    }

    public LiveData<Integer> getCodigoPublicacion() {
        return codigoPublicacion;
    }

    public LiveData<Integer> getCodigoGrupo() {
        return codigoGrupo;
    }

    public LiveData<ArrayList<Grupo>> getGrupos() {
        return grupos;
    }

    public void fetchPublicaciones(String idGrupo, String idColaborador) {
        PublicacionDAO.obtenerPublicacionesPorGrupoColaborador(idGrupo, idColaborador, new Callback<ArrayList<Publicacion>>() {
            @Override
            public void onResponse(Call<ArrayList<Publicacion>> call, Response<ArrayList<Publicacion>> response) {
                if(response.isSuccessful()){
                    publicaciones.setValue(response.body());
                    codigoPublicacion.setValue(response.code());
                }else{
                    codigoPublicacion.setValue(response.code());
                    Log.e("BuscarPublicacionViewModel", "Código: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Publicacion>> call, Throwable t) {
                codigoPublicacion.setValue(500);
                Log.e("BuscarPublicacionViewModel", "Error en la conexión: " + t.getMessage());
            }
        });
    }

    public void fetchGrupos(String colaborador, String rol) {
        GrupoDAO.obtenerGruposPorRolColaborador(colaborador, rol, new Callback<ArrayList<Grupo>>() {
            @Override
            public void onResponse(Call<ArrayList<Grupo>> call, Response<ArrayList<Grupo>> response) {
                if(response.isSuccessful()){
                    grupos.setValue(response.body());
                    codigoGrupo.setValue(response.code());
                }else{
                    codigoGrupo.setValue(response.code());
                    Log.e("BuscarPublicacionViewModel", "Código: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Grupo>> call, Throwable t) {
                codigoPublicacion.setValue(500);
                Log.e("BuscarPublicacionViewModel", "Error en la conexión: " + t.getMessage());
            }
        });
    }
}
