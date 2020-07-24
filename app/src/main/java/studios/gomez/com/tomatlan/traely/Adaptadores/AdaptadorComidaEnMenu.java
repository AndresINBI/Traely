package studios.gomez.com.tomatlan.traely.Adaptadores;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;

import java.util.List;

import studios.gomez.com.tomatlan.traely.Objetos.ComidaMenu;
import studios.gomez.com.tomatlan.traely.R;

/**
 * Created by gomez on 17/11/17.
 */

public class AdaptadorComidaEnMenu extends RecyclerView.Adapter<AdaptadorComidaEnMenu.ComidaViewHolder>{

    List<ComidaMenu> listaComida;
    Context context;
    public final clickHandler clickHandler;

    public AdaptadorComidaEnMenu(List<ComidaMenu> listaComida, Context context, clickHandler clickHandler) {
        this.listaComida = listaComida;
        this.context = context;
        this.clickHandler = clickHandler;
    }

    @Override
    public ComidaViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {


        View v = LayoutInflater.from(context).inflate(R.layout.comida_del_menu,parent,false);

        return new ComidaViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ComidaViewHolder holder, int position) {

        holder.nombre.setText(listaComida.get(position).getNombre());
        holder.precio.setText("$"+listaComida.get(position).getPrecio());

        Glide.with(context)
                .load(listaComida.get(position).getImagen())
                .fitCenter()
                .centerCrop()
                .into(holder.imagen);

        holder.numberButton.setNumber(String.valueOf(listaComida.get(position).getCantidad()));
        holder.clickHandler = this.clickHandler;

    }

    @Override
    public int getItemCount() {
        return listaComida.size();
    }



    public class ComidaViewHolder extends RecyclerView.ViewHolder implements ElegantNumberButton.OnValueChangeListener {

        ImageView imagen;
        TextView nombre,precio;
        public ElegantNumberButton numberButton;
        public clickHandler clickHandler;




        public ComidaViewHolder(View itemView) {
            super(itemView);

            imagen = itemView.findViewById(R.id.imagen_comida_menu);
            nombre = itemView.findViewById(R.id.nombre_comida_menu);
            precio = itemView.findViewById(R.id.precio_comida_menu);
            numberButton = itemView.findViewById(R.id.number_button_menu);

            numberButton.setOnValueChangeListener(this);

        }


        @Override
        public void onValueChange(ElegantNumberButton view, int oldValue, int newValue) {
            clickHandler.onClick(getAdapterPosition(),oldValue,newValue);
        }

    }

    public interface clickHandler{
        void onClick(final int position,int oldValue,int newValue);
    }


}
