package studios.gomez.com.tomatlan.traely.Adaptadores;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import studios.gomez.com.tomatlan.traely.Objetos.ComidaMenu;
import studios.gomez.com.tomatlan.traely.R;

/**
 * Created by gomez on 22/11/17.
 */

public class AdaptadorComidaEnCarrito extends RecyclerView.Adapter<AdaptadorComidaEnCarrito.ComidaEnCarritoViewHolder> {

    List<ComidaMenu> listaComidas;
    Context context;

    public AdaptadorComidaEnCarrito(List<ComidaMenu> listaComidas, Context context) {
        this.listaComidas = listaComidas;
        this.context = context;
    }

    @Override
    public ComidaEnCarritoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(context).inflate(R.layout.comida_en_carrito,parent,false);


        return new ComidaEnCarritoViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ComidaEnCarritoViewHolder holder, int position) {

        holder.nombre_comida.setText(listaComidas.get(position).getNombre());
        holder.cantidad_comida.setText(listaComidas.get(position).getCantidad()+"");

        Glide.with(context)
                .load(listaComidas.get(position).getImagen())
                .fitCenter()
                .centerCrop()
                .into(holder.foto_comida);

    }

    @Override
    public int getItemCount() {
        return listaComidas.size();
    }


    public class ComidaEnCarritoViewHolder extends RecyclerView.ViewHolder{

        ImageView foto_comida;
        TextView nombre_comida,cantidad_comida;

        public ComidaEnCarritoViewHolder(View itemView) {
            super(itemView);

            foto_comida = itemView.findViewById(R.id.imagen_comida_en_carrito);
            nombre_comida = itemView.findViewById(R.id.nombre_comida_en_carrito);
            cantidad_comida = itemView.findViewById(R.id.cantidad_comida_en_carrito);



        }


    }






}
