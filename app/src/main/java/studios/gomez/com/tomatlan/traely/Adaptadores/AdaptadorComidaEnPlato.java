package studios.gomez.com.tomatlan.traely.Adaptadores;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import studios.gomez.com.tomatlan.traely.Objetos.NombresDeComidaEnPlato;
import studios.gomez.com.tomatlan.traely.R;

/**
 * Created by gomez on 28/11/17.
 */

public class AdaptadorComidaEnPlato extends RecyclerView.Adapter<AdaptadorComidaEnPlato.ComidaEnPlatoViewHolder> {

    List<NombresDeComidaEnPlato> list;
    Context context;

    public AdaptadorComidaEnPlato(List<NombresDeComidaEnPlato> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public ComidaEnPlatoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.comida_en_plato,parent,false);
        return new ComidaEnPlatoViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ComidaEnPlatoViewHolder holder, int position) {

        holder.nombre.setText(list.get(position).getNombre());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    public class ComidaEnPlatoViewHolder extends RecyclerView.ViewHolder{

        TextView nombre;

        public ComidaEnPlatoViewHolder(View itemView) {
            super(itemView);

            nombre = itemView.findViewById(R.id.nombre_comida_en_plato);

        }
    }


}
