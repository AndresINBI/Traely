package studios.gomez.com.tomatlan.traely.Adaptadores;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.orm.query.Condition;
import com.orm.query.Select;

import java.util.ArrayList;
import java.util.List;

import studios.gomez.com.tomatlan.traely.Objetos.NombresDeComidaEnPlato;
import studios.gomez.com.tomatlan.traely.Objetos.PlatoBD;
import studios.gomez.com.tomatlan.traely.R;
import studios.gomez.com.tomatlan.traely.VariablesGenerales;

/**
 * Created by gomez on 28/11/17.
 */

public class AdaptadorPlatosConfirmarPedido extends RecyclerView.Adapter<AdaptadorPlatosConfirmarPedido.PlatosConfirmarPedidoViewHolder>  {

    List<PlatoBD> listaPlatos;

    Context contexto;


    public AdaptadorPlatosConfirmarPedido(List<PlatoBD> listaPlatos,Context contexto) {
        this.listaPlatos = listaPlatos;
        this.contexto = contexto;
    }

    @Override
    public PlatosConfirmarPedidoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.plato_en_recycler,parent,false);

        return new PlatosConfirmarPedidoViewHolder(v);
    }

    @Override
    public void onBindViewHolder(PlatosConfirmarPedidoViewHolder holder, int position) {

        holder.recyclerComidasEnPlato.setLayoutManager(new LinearLayoutManager(contexto));

            holder.list = Select.from(NombresDeComidaEnPlato.class)
                    .where(Condition.prop("numero_de_pedido").eq(VariablesGenerales.PEDIDO_ACTUAL))
                    .where(Condition.prop("numero_de_plato").eq(position)).list();


        holder.adaptadorPlatosConfirmarPedido = new AdaptadorComidaEnPlato(holder.list,contexto);

        holder.recyclerComidasEnPlato.setAdapter(holder.adaptadorPlatosConfirmarPedido);
        holder.textoPrecio.setText(listaPlatos.get(position).getPrecio()+"$");

    }

    @Override
    public int getItemCount() {
        return listaPlatos.size();
    }

    public class PlatosConfirmarPedidoViewHolder extends RecyclerView.ViewHolder{

        RecyclerView recyclerComidasEnPlato;
        TextView textoPrecio;
        AdaptadorComidaEnPlato adaptadorPlatosConfirmarPedido;
        List<NombresDeComidaEnPlato> list = new ArrayList<>();


        public PlatosConfirmarPedidoViewHolder(View itemView) {
            super(itemView);

            recyclerComidasEnPlato = itemView.findViewById(R.id.recycler_comidas_en_plato);
            textoPrecio = itemView.findViewById(R.id.texto_precio_por_plato);



            recyclerComidasEnPlato.setLayoutManager(new LinearLayoutManager(contexto));



        }

    }



}
