package studios.gomez.com.tomatlan.traely.Adaptadores;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import studios.gomez.com.tomatlan.traely.Objetos.Negocio;
import studios.gomez.com.tomatlan.traely.R;

/**
 * Created by gomez on 5/11/17.
 */

public class AdaptadorNegocios extends RecyclerView.Adapter<AdaptadorNegocios.NegociosViewHolder>  {

    List<Negocio> listaNegocios;
    Context context;
    public final ClickHandler clickLlamar;
    public final ClickHandler clickVerMenu;

    public AdaptadorNegocios(List<Negocio> listaNegocios, Context context, ClickHandler clickLlamar, ClickHandler clickVerMenu) {
        this.listaNegocios = listaNegocios;
        this.context = context;
        this.clickLlamar = clickLlamar;
        this.clickVerMenu = clickVerMenu;
    }

    @Override
    public NegociosViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(context).inflate(R.layout.negocio_en_fragment,parent,false);

        return new NegociosViewHolder(v);
    }

    @Override
    public void onBindViewHolder(NegociosViewHolder holder, int position) {

        holder.nombre.setText(listaNegocios.get(position).getNombre());

        Glide.with(context)
                .load(listaNegocios.get(position).getImagen_producto_estrella())
                .fitCenter()
                .centerCrop()
                .into(holder.imagen);
        holder.clickllamar = this.clickLlamar;
        holder.clickVerMenu = this.clickVerMenu;

    }

    @Override
    public int getItemCount() {
        return listaNegocios.size();
    }


    public class NegociosViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView imagen;
        TextView nombre;
        Button verMenu,llamar;
        public ClickHandler clickllamar;
        public ClickHandler clickVerMenu;
        RecyclerView rv;


        public NegociosViewHolder(View itemView) {
            super(itemView);
            imagen = itemView.findViewById(R.id.imagen_negocio);
            nombre = itemView.findViewById(R.id.nombre_negocio_card);
            verMenu = itemView.findViewById(R.id.boton_ver_menu);
            llamar = itemView.findViewById(R.id.boton_llamar);

            llamar.setOnClickListener(this);
            verMenu.setOnClickListener(this);

        }


        @Override
        public void onClick(View view) {

            switch (view.getId()){

                case R.id.boton_llamar:
                    clickLlamar.onClick(getAdapterPosition());
                    break;

                case R.id.boton_ver_menu:
                    clickVerMenu.onClick(getAdapterPosition());
                    break;

            }
        }
    }

    public interface ClickHandler{
        void onClick(final int position);
    }


}
