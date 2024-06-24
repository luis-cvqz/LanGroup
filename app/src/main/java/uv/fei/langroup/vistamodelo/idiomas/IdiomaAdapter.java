package uv.fei.langroup.vistamodelo.idiomas;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import uv.fei.langroup.R;
import uv.fei.langroup.modelo.POJO.Idioma;

public class IdiomaAdapter extends RecyclerView.Adapter<IdiomaAdapter.IdiomaViewHolder> {

    private List<Idioma> idiomas;
    private Context context;

    public IdiomaAdapter(List<Idioma> idiomas, Context context) {
        this.idiomas = idiomas;
        this.context = context;
    }

    @NonNull
    @Override
    public IdiomaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_records_idiomas, parent, false);
        return new IdiomaViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull IdiomaViewHolder holder, int position) {
        Idioma idioma = idiomas.get(position);
        holder.txtIdioma.setText(idioma.getNombre());

        // Puedes cambiar esto para asignar las imágenes según el id del idioma
        switch (idioma.getIdiomaId()) {
            case "0ba66cf4-1765-43ab-a762-141e28f034f8":
                holder.imgIdioma.setImageResource(R.drawable.ingles); // Imagen de inglés
                break;
            case "91485633-6efa-4710-a5e6-04560868f761":
                holder.imgIdioma.setImageResource(R.drawable.espanol); // Imagen de español
                break;
            // Añade más idiomas aquí
            default:
                holder.imgIdioma.setImageResource(R.drawable.france); // Imagen por defecto
                break;
        }
    }

    @Override
    public int getItemCount() {
        return idiomas.size();
    }

    public static class IdiomaViewHolder extends RecyclerView.ViewHolder {

        TextView txtIdioma;
        ImageView imgIdioma;

        public IdiomaViewHolder(@NonNull View itemView) {
            super(itemView);
            txtIdioma = itemView.findViewById(R.id.txt_idioma);
            imgIdioma = itemView.findViewById(R.id.img_idioma);
        }
    }
}
