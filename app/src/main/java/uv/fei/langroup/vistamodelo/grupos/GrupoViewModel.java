package uv.fei.langroup.vistamodelo.grupos;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import java.util.ArrayList;
import uv.fei.langroup.modelo.POJO.Grupo;
import uv.fei.langroup.servicio.DAO.GrupoDAO;

public class GrupoViewModel extends ViewModel {

    private MutableLiveData<ArrayList<Grupo>> grupos;
    private GrupoDAO grupoDAO;

    public GrupoViewModel() {
        grupoDAO = new GrupoDAO();
    }

    public LiveData<ArrayList<Grupo>> getGrupos() {
        if (grupos == null) {
            grupos = new MutableLiveData<>();
            cargarGrupos();
        }
        return grupos;
    }

    private void cargarGrupos() {
        grupoDAO.obtenerGrupos().observeForever(newGroups -> {
            if (newGroups != null) {
                grupos.setValue(newGroups);
            }
        });
    }

    public LiveData<ArrayList<Grupo>> getGruposColaborador(String colaboradorId) {
        return grupoDAO.obtenerGruposColaborador(colaboradorId);
    }
}
