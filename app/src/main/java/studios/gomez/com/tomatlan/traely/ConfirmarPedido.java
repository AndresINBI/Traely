package studios.gomez.com.tomatlan.traely;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.orm.query.Condition;
import com.orm.query.Select;

import java.util.List;

import studios.gomez.com.tomatlan.traely.Adaptadores.AdaptadorPlatosConfirmarPedido;
import studios.gomez.com.tomatlan.traely.Objetos.NombresDeComidaEnPlato;
import studios.gomez.com.tomatlan.traely.Objetos.Pedido;
import studios.gomez.com.tomatlan.traely.Objetos.PlatoBD;

public class ConfirmarPedido extends AppCompatActivity {

    TextView texto_ubicacion,texto_precio;
    RecyclerView recyclerPlatos;
    AdaptadorPlatosConfirmarPedido adaptadorPlatosConfirmarPedido;
    List<PlatoBD> listaPlatos;
    FloatingActionButton boton_confirmar_pedido;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirmar_pedido);
        Toolbar toolbar = findViewById(R.id.toolbar_confirmar_pedido);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        setTitle("Confirma tu pedido");

        Toast.makeText(this, ""+ VariablesGenerales.PLATO_ACTUAL, Toast.LENGTH_SHORT).show();

        instanciarObjetos();



    }

    private void ponerInformacion() {

        texto_ubicacion.setText(getIntent().getExtras().getString("ubicacion"));
        recyclerPlatos.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        listaPlatos = Select.from(PlatoBD.class).where(Condition.prop("numero_de_pedido").eq(VariablesGenerales.PEDIDO_ACTUAL)).list();

        int precio = 0;
        for (PlatoBD platoBD:
             listaPlatos) {

            precio=precio+platoBD.getPrecio();
        }

        texto_precio.setText("Total:$"+precio);

        adaptadorPlatosConfirmarPedido = new AdaptadorPlatosConfirmarPedido(listaPlatos,getApplicationContext());
        recyclerPlatos.setAdapter(adaptadorPlatosConfirmarPedido);


    }

    private void instanciarObjetos() {

        texto_precio = findViewById(R.id.texto_precio_final);
        texto_ubicacion = findViewById(R.id.texto_ubicacion_confirmar_pedido);
        recyclerPlatos = findViewById(R.id.recycler_platos_confirmar_pedido);
        boton_confirmar_pedido = findViewById(R.id.boton_confirmar_pedido);

    }

    @Override
    protected void onStart() {
        super.onStart();

        ponerInformacion();

        boton_confirmar_pedido.setOnClickListener(clickConfirmar());

    }

    private View.OnClickListener clickConfirmar() {

        mandarMensaje();


        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String plato;

                if (listaPlatos.size()>1) {
                    plato = " platos";

                }else {
                    plato = " plato";
                }

                completarListas();

                Pedido pedido = new Pedido(listaPlatos.size() + plato
                        ,texto_precio.getText().toString()
                        ,texto_ubicacion.getText().toString()
                        ,"Fecha" //todo Detectar fecha y hora
                        ,VariablesGenerales.PEDIDO_ACTUAL,true);

                pedido.save();

                irAHome();
                VariablesGenerales.PLATO_ACTUAL=0;

            }

            private void irAHome() {
                Intent irAHome = new Intent(ConfirmarPedido.this,Home.class);
                irAHome.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(irAHome);
            }

        };

    }

    private void completarListas() {

        List<PlatoBD> listaPlatos = Select.from(PlatoBD.class)
                .where(Condition.prop("numero_de_pedido").eq(VariablesGenerales.PEDIDO_ACTUAL)).list();

        for (PlatoBD plato :
                listaPlatos) {
            plato.setCompletado(true);
        }

        List<NombresDeComidaEnPlato> listaNombres =
                Select.from(NombresDeComidaEnPlato.class)
                        .where(Condition.prop("numero_de_pedido").eq(VariablesGenerales.PEDIDO_ACTUAL)).list();

        for (NombresDeComidaEnPlato nombresDeComidaEnPlato :
                listaNombres) {

            nombresDeComidaEnPlato.setCompletado(true);

        }

    }

    private void mandarMensaje() {



    }
}
