package uv.fei.langroup.vistamodelo.grupos;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import uv.fei.langroup.R;
import uv.fei.langroup.modelo.POJO.Grupo;
import uv.fei.langroup.modelo.POJO.Idioma;
import uv.fei.langroup.servicio.DAO.IdiomaDAO;

public class BuscarGrupoAdapter extends RecyclerView.Adapter<BuscarGrupoAdapter.GrupoViewHolder> {

    private ArrayList<Grupo> grupoList;
    private LifecycleOwner lifecycleOwner;

    public BuscarGrupoAdapter(ArrayList<Grupo> grupoList, LifecycleOwner lifecycleOwner) {
        this.grupoList = grupoList;
        this.lifecycleOwner = lifecycleOwner;
    }

    public void setGrupoList(ArrayList<Grupo> grupoList) {
        this.grupoList = grupoList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public GrupoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_records_grupos, parent, false);
        return new GrupoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GrupoViewHolder holder, int position) {
        Grupo grupo = grupoList.get(position);
        holder.txtNombreGrupo.setText(grupo.getNombre());

        // Obtener el nombre del idioma basado en el idiomaId
        String idiomaId = grupo.getIdiomaid();
        Log.d("GrupoAdapter", "onBindViewHolder: idiomaId = " + idiomaId);

        if (idiomaId != null && !idiomaId.isEmpty()) {
            IdiomaDAO idiomaDAO = new IdiomaDAO();
            idiomaDAO.obtenerIdiomaPorId(idiomaId).observe(lifecycleOwner, new Observer<Idioma>() {
                @Override
                public void onChanged(Idioma idioma) {
                    if (idioma != null) {
                        holder.txtIdiomaGrupo.setText(idioma.getNombre());
                    } else {
                        holder.txtIdiomaGrupo.setText("Idioma desconocido");
                    }
                }
            });
        } else {
            holder.txtIdiomaGrupo.setText("Idioma desconocido");
        }
    }

    @Override
    public int getItemCount() {
        return grupoList.size();
    }

    public static class GrupoViewHolder extends RecyclerView.ViewHolder {
        public TextView txtNombreGrupo;
        public TextView txtIdiomaGrupo;

        public GrupoViewHolder(View itemView) {
            super(itemView);
            txtNombreGrupo = itemView.findViewById(R.id.txt_nombre_grupo);
            txtIdiomaGrupo = itemView.findViewById(R.id.txt_idioma_grupo);
        }
    }
}