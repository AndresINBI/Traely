package studios.gomez.com.tomatlan.traely;


import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.orm.SugarContext;
import com.orm.query.Condition;
import com.orm.query.Select;

import java.util.ArrayList;
import java.util.List;

import studios.gomez.com.tomatlan.traely.Adaptadores.AdaptadorComidaEnCarrito;
import studios.gomez.com.tomatlan.traely.Objetos.ComidaMenu;
import studios.gomez.com.tomatlan.traely.Objetos.NombresDeComidaEnPlato;
import studios.gomez.com.tomatlan.traely.Objetos.PlatoBD;


public class CarritoFragment extends Fragment {


    public CarritoFragment() {
        // Required empty public constructor
    }

    public static List<ComidaMenu> listaComidas = new ArrayList<>();
    RecyclerView recyclerComida;
    public static AdaptadorComidaEnCarrito adaptadorComidaEnCarrito;
    FloatingActionButton completar;
    Button agregar_plato;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_carrito, container, false);
        SugarContext.init(getContext());



        instanciarObjetos(v);
        recyclerComida.setLayoutManager(new LinearLayoutManager(getContext()));
        adaptadorComidaEnCarrito = new AdaptadorComidaEnCarrito(MenuNegocio.listaCarrito,getContext());
        recyclerComida.setAdapter(adaptadorComidaEnCarrito);
        recyclerComida.setNestedScrollingEnabled(false);

        completar.setOnClickListener(clickCompletar());
        agregar_plato.setOnClickListener(clickAgregarPlato());



        return v;
    }

    private View.OnClickListener clickAgregarPlato() {

        View.OnClickListener c = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (MenuNegocio.listaCarrito.isEmpty()){
                    Toast.makeText(getContext(), "Agrega una comida", Toast.LENGTH_SHORT).show();
                }else{

                    guardarPlatoBD();
                }

            }
        };

        return c;
    }

    private void guardarPlatoBD() {


        PlatoBD plato = new PlatoBD(VariablesGenerales.PEDIDO_ACTUAL,MenuNegocio.precio,VariablesGenerales.PLATO_ACTUAL,false);

        for (ComidaMenu comidaMenu:
             MenuNegocio.listaCarrito) {

            NombresDeComidaEnPlato a = new NombresDeComidaEnPlato(comidaMenu.getCantidad()+" "+comidaMenu.getNombre()
                    ,VariablesGenerales.PEDIDO_ACTUAL
                    ,VariablesGenerales.PLATO_ACTUAL,false);
            a.save();

        }

        plato.save();

        VariablesGenerales.PLATO_ACTUAL++;

        MenuNegocio.precio=0;
        MenuNegocio.listaCarrito.removeAll(MenuNegocio.listaCarrito);
        MenuNegocio.reiniciarCantidades(getActivity());

        adaptadorComidaEnCarrito.notifyDataSetChanged();

    }

    private View.OnClickListener clickCompletar() {

        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                List<PlatoBD> platos = Select.from(PlatoBD.class).where(Condition.prop("numero_de_pedido").eq(VariablesGenerales.PEDIDO_ACTUAL)).list();


                if (MenuNegocio.listaCarrito.isEmpty()){

                    if (platos.isEmpty()){
                        Toast.makeText(getContext(), "Agrega una comida", Toast.LENGTH_SHORT).show();
                    }else {
                        irAUbicacion();
                    }

                }else{
                    guardarPlatoBD();
                    irAUbicacion();
                }

            }
        };

    }

    private void irAUbicacion() {
        Intent irAUbicacion = new Intent(getActivity(),UbicacionExtras.class);
        startActivity(irAUbicacion);
    }

    private void instanciarObjetos(View v) {

        recyclerComida = v.findViewById(R.id.recycler_carrito);
        completar = v.findViewById(R.id.boton_completar_carrito);
        agregar_plato = v.findViewById(R.id.boton_agregar_plato);


    }


}
