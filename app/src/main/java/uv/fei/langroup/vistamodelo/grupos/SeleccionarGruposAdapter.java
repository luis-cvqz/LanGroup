package uv.fei.langroup.vistamodelo.grupos;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import uv.fei.langroup.R;
import uv.fei.langroup.modelo.POJO.Grupo;
import uv.fei.langroup.modelo.POJO.Idioma;
import uv.fei.langroup.servicio.DAO.GrupoDAO;
import uv.fei.langroup.servicio.DAO.IdiomaDAO;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SeleccionarGruposAdapter extends RecyclerView.Adapter<SeleccionarGruposAdapter.GrupoViewHolder> {

    private ArrayList<Grupo> grupoList;
    private LifecycleOwner lifecycleOwner;
    private String colaboradorId;

    public SeleccionarGruposAdapter(ArrayList<Grupo> grupoList, LifecycleOwner lifecycleOwner, String colaboradorId) {
        this.grupoList = grupoList;
        this.lifecycleOwner = lifecycleOwner;
        this.colaboradorId = colaboradorId;
    }

    public void setGrupoList(ArrayList<Grupo> grupoList) {
        this.grupoList = grupoList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public SeleccionarGruposAdapter.GrupoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_records_grupos, parent, false);
        return new SeleccionarGruposAdapter.GrupoViewHolder(view);
    }

    @Override
    public int getItemCount() {
        return grupoList.size();
    }

    @Override
    public void onBindViewHolder(@NonNull SeleccionarGruposAdapter.GrupoViewHolder holder, int position) {
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

        holder.btnUnirse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                unirseAGrupo(holder, colaboradorId, grupo.getId(), "Participante"); // Asumiendo rol "Participante"
            }
        });
    }

    // Método para unirse a un grupo
    private void unirseAGrupo(GrupoViewHolder holder, String colaboradorId, String grupoid, String rol) {
        GrupoDAO.unirseAGrupo(colaboradorId, grupoid, rol, new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(holder.itemView.getContext(), "Unido al grupo exitosamente", Toast.LENGTH_SHORT).show();
                } else {
                    Log.e("SeleccionarGrupos", "Error al unirse al grupo: " + response.code());
                    Toast.makeText(holder.itemView.getContext(), "Error al unirse al grupo", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.e("SeleccionarGrupos", "Error en la conexión: " + t.getMessage());
                Toast.makeText(holder.itemView.getContext(), "Error en la conexión", Toast.LENGTH_SHORT).show();
            }
        });
    }

    // ViewHolder
    public static class GrupoViewHolder extends RecyclerView.ViewHolder {
        public TextView txtNombreGrupo;
        public TextView txtIdiomaGrupo;
        public Button btnUnirse;

        public GrupoViewHolder(View itemView) {
            super(itemView);
            txtNombreGrupo = itemView.findViewById(R.id.txt_nombre_grupo);
            txtIdiomaGrupo = itemView.findViewById(R.id.txt_idioma_grupo);
            btnUnirse = itemView.findViewById(R.id.btn_unirse);
        }
    }
}
