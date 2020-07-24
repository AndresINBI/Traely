package studios.gomez.com.tomatlan.traely.Adaptadores;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import studios.gomez.com.tomatlan.traely.Objetos.Pedido;
import studios.gomez.com.tomatlan.traely.R;

/**
 * Created by gomez on 3/12/17.
 */

public class AdaptadorPedido extends RecyclerView.Adapter<AdaptadorPedido.PedidoViewHolder> {

    List<Pedido> pedidoList;
    public final ClickPedido clickPedido;

    public AdaptadorPedido(List<Pedido> pedidoList, ClickPedido clickPedido) {
        this.pedidoList = pedidoList;
        this.clickPedido = clickPedido;
    }

    @Override
    public PedidoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.pedido_en_recycler,parent,false);

        return new PedidoViewHolder(v);
    }

    @Override
    public void onBindViewHolder(PedidoViewHolder holder, int position) {

        holder.precio.setText(pedidoList.get(position).getPrecioTotal());
        holder.titulo.setText(pedidoList.get(position).getNombre());

        holder.clickPedido = this.clickPedido;

    }

    @Override
    public int getItemCount() {
        return pedidoList.size();
    }


    public class PedidoViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView titulo,precio;
        CardView carta;
        public ClickPedido clickPedido;


        public PedidoViewHolder(View itemView) {
            super(itemView);

            carta = itemView.findViewById(R.id.carta_pedido);
            titulo = itemView.findViewById(R.id.titulo_de_pedido);
            precio = itemView.findViewById(R.id.texto_precio_por_pedido);

            carta.setOnClickListener(this);


        }

        @Override
        public void onClick(View view) {
            clickPedido.onClick(getAdapterPosition());
        }
    }

    public interface ClickPedido{
        void onClick(final int position);
    }




}
