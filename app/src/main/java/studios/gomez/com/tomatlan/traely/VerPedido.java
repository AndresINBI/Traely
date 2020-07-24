package studios.gomez.com.tomatlan.traely;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.orm.query.Condition;
import com.orm.query.Select;

import java.util.ArrayList;
import java.util.List;

import studios.gomez.com.tomatlan.traely.Adaptadores.AdaptadorPlatosConfirmarPedido;
import studios.gomez.com.tomatlan.traely.Objetos.Pedido;
import studios.gomez.com.tomatlan.traely.Objetos.PlatoBD;

public class VerPedido extends AppCompatActivity {

    RecyclerView recyclerView;
    AdaptadorPlatosConfirmarPedido adaptadorPlatos;
    List<PlatoBD> listaPlatos = new ArrayList<>();
    TextView textoPrecio,textoUbicacion;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_pedido);
        Toolbar toolbar = findViewById(R.id.toolbar_ver_pedido);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        recyclerView = findViewById(R.id.recycler_platos_ver_pedido);
        textoPrecio = findViewById(R.id.texto_precio_ver_pedido);
        textoUbicacion = findViewById(R.id.texto_ubicacion_ver_pedido);


    }

    @Override
    protected void onStart() {
        super.onStart();

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        VariablesGenerales.PEDIDO_ACTUAL = getIntent().getExtras().getInt("position");

        listaPlatos = Select.from(PlatoBD.class)
                .where(Condition.prop("numero_de_pedido").eq(getIntent().getExtras().getInt("position"))).list();
        int precio = 0;

        for (PlatoBD plato :
                listaPlatos) {
            precio= precio+plato.getPrecio();
        }

        Pedido pedido = Pedido.findById(Pedido.class,VariablesGenerales.PEDIDO_ACTUAL);
        textoPrecio.setText("Total $:"+precio);
        textoUbicacion.setText(pedido.getUbicacion());




        adaptadorPlatos = new AdaptadorPlatosConfirmarPedido(listaPlatos,this);

        recyclerView.setAdapter(adaptadorPlatos);


    }
}
