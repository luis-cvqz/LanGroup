package uv.fei.langroup.vista.publicaciones;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import uv.fei.langroup.databinding.ItemRecordsInteraccionesBinding;
import uv.fei.langroup.databinding.ItemRecordsPublicacionBinding;
import uv.fei.langroup.modelo.POJO.Interaccion;

public class InteraccionAdapter extends ListAdapter<Interaccion, InteraccionAdapter.InteraccionViewHolder> {
    public static final DiffUtil.ItemCallback<Interaccion> DIFF_CALLBACK = new DiffUtil.ItemCallback<Interaccion>() {
        @Override
        public boolean areItemsTheSame(@NonNull Interaccion oldItem, @NonNull Interaccion newItem) {
            return oldItem.getId().equals(newItem.getId());
        }

        @Override
        public boolean areContentsTheSame(@NonNull Interaccion oldItem, @NonNull Interaccion newItem) {
            return oldItem.equals(newItem);
        }
    };

    protected InteraccionAdapter() {
        super(DIFF_CALLBACK);
    }

    @NonNull
    @Override
    public InteraccionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemRecordsInteraccionesBinding binding = ItemRecordsInteraccionesBinding.inflate(LayoutInflater.from(parent.getContext()));
        return new InteraccionViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull InteraccionViewHolder holder, int position) {
        Interaccion interaccion = getItem(position);
        holder.bind(interaccion);
    }

    class InteraccionViewHolder extends RecyclerView.ViewHolder {
        private final ItemRecordsInteraccionesBinding binding;

        public InteraccionViewHolder(@NonNull ItemRecordsInteraccionesBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(Interaccion interaccion) {
            binding.txvFecha.setText(interaccion.getFecha().toString());
            binding.txvComentario.setText(interaccion.getComentario());
            binding.txvNombreUsuario.setText(String.format("%s %s", interaccion.getColaborador().getNombre(), interaccion.getColaborador().getApellido()));
            binding.ratingBar.setRating(interaccion.getValoracion());
            binding.ratingBar.setIsIndicator(true);
        }
    }
}
