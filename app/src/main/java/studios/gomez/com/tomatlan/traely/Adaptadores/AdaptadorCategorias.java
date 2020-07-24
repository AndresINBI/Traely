package studios.gomez.com.tomatlan.traely.Adaptadores;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import studios.gomez.com.tomatlan.traely.Objetos.Categoria;
import studios.gomez.com.tomatlan.traely.R;

/**
 * Created by gomez on 5/11/17.
 */

public class AdaptadorCategorias extends RecyclerView.Adapter<AdaptadorCategorias.CategoriasViewHolder> {

    List<Categoria> listaCategoria;
    Context context;
    public final ClickHandler clickHandler;


    public AdaptadorCategorias(List<Categoria> listaCategoria, Context context,ClickHandler clickHandler) {
        this.listaCategoria = listaCategoria;
        this.context = context;
        this.clickHandler = clickHandler;
    }

    @Override
    public CategoriasViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.categoria,parent,false);


        return new CategoriasViewHolder(v);
    }

    @Override
    public void onBindViewHolder(CategoriasViewHolder holder, int position) {

        holder.NombreCategoria.setText(listaCategoria.get(position).getNombre());

        Glide.with(context)
                .load(listaCategoria.get(position).getIcono())
                .fitCenter()
                .into(holder.icono);

        holder.clickHandler = this.clickHandler;

    }


    @Override
    public int getItemCount() {
        return listaCategoria.size();
    }



    public class CategoriasViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView NombreCategoria;
        ImageView icono;
        public ClickHandler clickHandler;
        CardView cartaBoton;


        public CategoriasViewHolder(View itemView) {
            super(itemView);

            NombreCategoria = itemView.findViewById(R.id.nombre_categoria);
            icono = itemView.findViewById(R.id.icono_categoria);
            cartaBoton = itemView.findViewById(R.id.card_categoria);

            cartaBoton.setOnClickListener(this);



        }

        @Override
        public void onClick(View view) {
            clickHandler.onClick(getAdapterPosition());
        }
    }


    public interface ClickHandler{
        void onClick(final int position);
    }


}
