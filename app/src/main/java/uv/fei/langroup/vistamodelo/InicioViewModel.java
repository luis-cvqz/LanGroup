package uv.fei.langroup.vistamodelo;

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
import uv.fei.langroup.modelo.POJO.Rol;
import uv.fei.langroup.servicio.DAO.GrupoDAO;
import uv.fei.langroup.servicio.DAO.PublicacionDAO;
import uv.fei.langroup.servicio.DAO.RolDAO;

public class InicioViewModel extends ViewModel {
    private MutableLiveData<ArrayList<Publicacion>> publicaciones;
    private MutableLiveData<ArrayList<Grupo>> grupos;
    private MutableLiveData<Integer> codigoPublicacion;
    private MutableLiveData<Integer> codigoGrupo;

    private MutableLiveData<Rol> rolColaborador;

    private MutableLiveData<Integer> codigoRol;

    private MutableLiveData<Integer> codigoEliminarPublicacion;

    public InicioViewModel() {
        publicaciones = new MutableLiveData<>();
        codigoPublicacion = new MutableLiveData<>();
        grupos = new MutableLiveData<>();
        codigoGrupo = new MutableLiveData<>();
        rolColaborador = new MutableLiveData<>();
        codigoRol = new MutableLiveData<>();
        codigoEliminarPublicacion = new MutableLiveData<>();
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

    public LiveData<Integer> getCodigoRol() {
        return codigoRol;
    }

    public LiveData<Integer> getCodigoEliminarPublicacion() {
        return codigoEliminarPublicacion;
    }

    public LiveData<ArrayList<Grupo>> getGrupos() {
        return grupos;
    }

    public LiveData<Rol> getRolColaborador() {
        return rolColaborador;
    }

    public void fetchPublicaciones(String idGrupo) {
        PublicacionDAO.obtenerPublicacionesPorGrupo(idGrupo, new Callback<ArrayList<Publicacion>>() {
            @Override
            public void onResponse(Call<ArrayList<Publicacion>> call, Response<ArrayList<Publicacion>> response) {
                if(response.isSuccessful()){
                    publicaciones.setValue(response.body());
                    codigoPublicacion.setValue(response.code());
                }else{
                    codigoPublicacion.setValue(response.code());
                    Log.e("InicioViewModel", "Código: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Publicacion>> call, Throwable t) {
                codigoPublicacion.setValue(500);
                Log.e("InicioViewModel", "Error en la conexión: " + t.getMessage());
            }
        });
    }

    public void fetchGrupos(String colaborador) {
        GrupoDAO.obtenerGruposPorColaborador(colaborador, new Callback<ArrayList<Grupo>>() {
            @Override
            public void onResponse(Call<ArrayList<Grupo>> call, Response<ArrayList<Grupo>> response) {
                if(response.isSuccessful()){
                    grupos.setValue(response.body());
                    codigoGrupo.setValue(response.code());
                }else{
                    codigoGrupo.setValue(response.code());
                    Log.e("InicioViewModel", "Código: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Grupo>> call, Throwable t) {
                codigoGrupo.setValue(500);
                Log.e("InicioViewModel", "Error en la conexión: " + t.getMessage());
            }
        });
    }

    public void fetchRolColaborador(String rol) {
        RolDAO.obtenerRolPorId(rol, new Callback<Rol>() {
            @Override
            public void onResponse(Call<Rol> call, Response<Rol> response) {
                if(response.isSuccessful()){
                    rolColaborador.setValue(response.body());
                    codigoRol.setValue(response.code());
                }else{
                    codigoRol.setValue(response.code());
                    Log.e("InicioViewModel", "Código: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<Rol> call, Throwable t) {
                codigoRol.setValue(500);
                Log.e("InicioViewModel", "Error en la conexión: " + t.getMessage());
            }
        });
    }

    public void fetchEliminarPublicacion(String idPublicacion) {
        PublicacionDAO.eliminarPublicacion(idPublicacion, new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    codigoEliminarPublicacion.setValue(response.code());
                }
                else {
                    codigoEliminarPublicacion.setValue(response.code());
                    Log.e("InicioViewModel", "Código: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                codigoEliminarPublicacion.setValue(500);
                Log.e("InicioViewModel", "Error en la conexión: " + t.getMessage());
            }
        });
    }

    public void clearLiveData() {
        publicaciones = null;
        grupos = null;
        codigoPublicacion = null;
        codigoGrupo = null;
        rolColaborador = null;
        codigoRol = null;
        codigoEliminarPublicacion = null;
    }
}
