package uv.fei.langroup.vista.publicaciones;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import uv.fei.langroup.modelo.POJO.Publicacion;
import uv.fei.langroup.databinding.ItemRecordsPublicacionBinding;

public class PublicacionAdapter extends ListAdapter<Publicacion, PublicacionAdapter.PublicacionViewHolder> {
    public static final DiffUtil.ItemCallback<Publicacion> DIFF_CALLBACK = new DiffUtil.ItemCallback<Publicacion>() {
        @Override
        public boolean areItemsTheSame(@NonNull Publicacion oldItem, @NonNull Publicacion newItem) {
            return oldItem.getId().equals(newItem.getId());
        }

        @Override
        public boolean areContentsTheSame(@NonNull Publicacion oldItem, @NonNull Publicacion newItem) {
            return oldItem.equals(newItem);
        }
    };

    public PublicacionAdapter() {
        super(DIFF_CALLBACK);
    }

    @NonNull
    @Override
    public PublicacionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemRecordsPublicacionBinding binding = ItemRecordsPublicacionBinding.inflate(LayoutInflater.from(parent.getContext()));
        return new PublicacionViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull PublicacionViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Publicacion publicacion = getItem(position);
        holder.bind(publicacion);

        if (onButtonEliminarVisibilityListener != null) {
            boolean isVisible = onButtonEliminarVisibilityListener.setButtonEliminarVisibility(publicacion);
            holder.setButtonAjustarVisibility(isVisible ? View.VISIBLE : View.GONE);
        }

        holder.buttonEliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onButtonEliminarClickListener != null) {
                    onButtonEliminarClickListener.onButtonEliminarClickListener(publicacion, position);
                }
            }
        });
    }

    private OnButtonEliminarClickListener onButtonEliminarClickListener;

    public void setOnButtonEliminarClickListener(OnButtonEliminarClickListener onButtonEliminarClickListener) {
        this.onButtonEliminarClickListener = onButtonEliminarClickListener;
    }

    public interface OnButtonEliminarVisibilityListener {
        boolean setButtonEliminarVisibility(Publicacion publicacion);
    }

    private OnButtonEliminarVisibilityListener onButtonEliminarVisibilityListener;

    public void setOnButtonEliminarVisibilityListener(OnButtonEliminarVisibilityListener onButtonEliminarVisibilityListener) {
        this.onButtonEliminarVisibilityListener = onButtonEliminarVisibilityListener;
    }

    class PublicacionViewHolder extends RecyclerView.ViewHolder {
        private final ItemRecordsPublicacionBinding binding;
        private final ImageButton buttonEliminar;

        public PublicacionViewHolder(@NonNull ItemRecordsPublicacionBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
            this.buttonEliminar = binding.buttonEliminar;
        }

        public void bind(Publicacion publicacion) {
            binding.txvTituloPublicacion.setText(publicacion.getTitulo());
            binding.txvTitular.setText(String.format("%s %s", publicacion.getColaborador().getNombre(), publicacion.getColaborador().getApellido()));
            binding.txvNombreGrupo.setText(publicacion.getGrupo().getNombre());
            binding.txvNombreIdioma.setText(publicacion.getGrupo().getIdioma().getNombre());
            binding.txvFecha.setText(publicacion.getFecha().toString());
            binding.editTextDescripcionPublicacion.setText(publicacion.getDescripcion());
        }

        public void setButtonAjustarVisibility(int visibility) {
            buttonEliminar.setVisibility(visibility);
        }
    }

    public interface OnButtonEliminarClickListener {
        void onButtonEliminarClickListener(Publicacion publicacion, int position);
    }
}
