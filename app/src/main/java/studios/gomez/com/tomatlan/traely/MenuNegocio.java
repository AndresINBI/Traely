package studios.gomez.com.tomatlan.traely;

import android.content.pm.ActivityInfo;
import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.mancj.slideup.SlideUp;
import com.mancj.slideup.SlideUpBuilder;
import com.orm.query.Condition;
import com.orm.query.Select;
import com.robinhood.ticker.TickerUtils;
import com.robinhood.ticker.TickerView;

import java.util.ArrayList;
import java.util.List;

import me.relex.circleindicator.CircleIndicator;
import studios.gomez.com.tomatlan.traely.Adaptadores.AdaptadorComidaEnMenu;
import studios.gomez.com.tomatlan.traely.Adaptadores.AdaptadorFotosDelNegocio;
import studios.gomez.com.tomatlan.traely.Objetos.ComidaMenu;
import studios.gomez.com.tomatlan.traely.Objetos.FotoVPMenu;
import studios.gomez.com.tomatlan.traely.Objetos.NombresDeComidaEnPlato;
import studios.gomez.com.tomatlan.traely.Objetos.PlatoBD;


public class MenuNegocio extends AppCompatActivity {

    AdaptadorFotosDelNegocio adaptadorViewPager;
    CircleIndicator indicadorVP;
    ViewPager viewPagerFotos;
    List<FotoVPMenu> listaFotos = new ArrayList<>();
    FirebaseFirestore firestore = FirebaseFirestore.getInstance();
    RecyclerView recyclerComida;
    AdaptadorComidaEnMenu adaptadorComidaEnMenu;
    protected static List<ComidaMenu> listaComida = new ArrayList<>();
    public static List<ComidaMenu> listaCarrito = new ArrayList<>();
    CardView carta_info_negocio,carta_ver_carrito;
    TextView nombre_carta,horario_carta,tiempo_espera;
    CollapsingToolbarLayout ctlLayout;
    View fragment_carrito,v;
    SlideUp slideUp;
    Toolbar toolbar;
    TickerView ticker_ver_carrito;
    public static int precio = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_negocio);
        toolbar = findViewById(R.id.toolbar_menu);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        instanciarObjetos();
        ticker_ver_carrito.setCharacterList(TickerUtils.getDefaultNumberList());
        ticker_ver_carrito.setTextSize(58);
        ticker_ver_carrito.setTextColor(R.color.colorAccent);
        ticker_ver_carrito.setText("$"+precio);
        eliminarNoCompletados();
        Toast.makeText(this, ""+VariablesGenerales.PEDIDO_ACTUAL, Toast.LENGTH_SHORT).show();

    }

    private void eliminarNoCompletados() {

        List<PlatoBD> platosNoCompletados =
                Select.from(PlatoBD.class).where(Condition.prop("numero_de_pedido").eq(VariablesGenerales.PEDIDO_ACTUAL)).list();

        if (!platosNoCompletados.isEmpty()){
            Toast.makeText(this, "Hay platos NC", Toast.LENGTH_SHORT).show();
        }

        for (PlatoBD plato :
                platosNoCompletados) {
            plato.delete();
        }

        List<NombresDeComidaEnPlato> nombreNoCompletado =
                Select.from(NombresDeComidaEnPlato.class)
                        .where(Condition.prop("numero_de_pedido").eq(VariablesGenerales.PEDIDO_ACTUAL)).list();

        if (!nombreNoCompletado.isEmpty()){
            Toast.makeText(this, "Hay Nombre NC", Toast.LENGTH_SHORT).show();
        }

        for (NombresDeComidaEnPlato nombre :
                nombreNoCompletado) {
            nombre.delete();
        }

    }


    public static void reiniciarCantidades(FragmentActivity activity){

        RecyclerView recyclerView = activity.findViewById(R.id.recycler_menu);

        for (ComidaMenu a :
                listaComida) {
            a.setCantidad(0);

        }

        recyclerView.getAdapter().notifyDataSetChanged();

        TickerView a = activity.findViewById(R.id.ticker_ver_carrito);
        a.setText("$"+precio);

    }

    private void instanciarObjetos() {

        fragment_carrito = findViewById(R.id.fragment_carrito);
        nombre_carta = findViewById(R.id.nombre_negocio_menu);
        recyclerComida = findViewById(R.id.recycler_menu);
        viewPagerFotos = findViewById(R.id.view_pager_menu);
        indicadorVP = findViewById(R.id.circle_menu);
        carta_info_negocio = findViewById(R.id.carta_app_bar);
        ctlLayout = findViewById(R.id.ctl_menu);
        horario_carta = findViewById(R.id.texto_horario_menu);
        tiempo_espera = findViewById(R.id.tiempo_espera_menu_card);
        carta_ver_carrito = findViewById(R.id.carta_carrito);
        v = findViewById(R.id.bloqueador_menu);
        ticker_ver_carrito = findViewById(R.id.ticker_ver_carrito);

    }

    private void bloquearScroll() {

        AppBarLayout appBarLayout = findViewById(R.id.appbar_menu);
        appBarLayout.setExpanded(false);
        v.setVisibility(View.VISIBLE);
        CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams)
                appBarLayout.getLayoutParams();

        if (appBarLayout.getLayoutParams()!=null) {
            AppBarLayout.Behavior behavior = (AppBarLayout.Behavior) params.getBehavior();
            behavior.setDragCallback(new AppBarLayout.Behavior.DragCallback() {
                @Override
                public boolean canDrag(@NonNull AppBarLayout appBarLayout) {
                    return false;
                }
            });
        }

    }

    private void Slide() {

        slideUp = new SlideUpBuilder(fragment_carrito)
                .withStartGravity(Gravity.BOTTOM)
                .withStartState(SlideUp.State.HIDDEN)
                .withGesturesEnabled(true)
                .build();

        slideUp.addSlideListener(new SlideUp.Listener.Visibility() {
            @Override
            public void onVisibilityChanged(int visibility) {
                if (visibility == 8 ){
                    View v = findViewById(R.id.bloqueador_menu);
                    v.setVisibility(View.INVISIBLE);
                }
            }
        });
        slideUp.addSlideListener(new SlideUp.Listener.Slide() {
            @Override
            public void onSlide(float percent) {
                v.setAlpha(.5f-(percent/100));
                fragment_carrito.setAlpha(1-(percent/100));
                carta_ver_carrito.setAlpha(0+(percent/100));
            }
        });
    }

    private AdaptadorComidaEnMenu.clickHandler clickAgregar() {

        return new AdaptadorComidaEnMenu.clickHandler() {
            @Override
            public void onClick(int position, int oldValue, int newValue) {

                if (newValue > oldValue) {
                    precio = precio + listaComida.get(position).getPrecio();
                    int cantidad = listaComida.get(position).getCantidad()+1;
                    listaComida.get(position).setCantidad(cantidad);
                    agregarACarrito();

                }else if (newValue<oldValue){
                    precio = precio -listaComida.get(position).getPrecio();
                    int cantidad = listaComida.get(position).getCantidad()-1;
                    listaComida.get(position).setCantidad(cantidad);
                    agregarACarrito();
                }
                ticker_ver_carrito.setText(precio+"$");

            }
        };

    }

    private void agregarACarrito() {

        listaCarrito.removeAll(listaCarrito);

        for (ComidaMenu comida :
                listaComida) {

            if (comida.getCantidad()>0){
                listaCarrito.add(comida);
                CarritoFragment.adaptadorComidaEnCarrito.notifyDataSetChanged();
            }else {
                listaCarrito.remove(comida);
                CarritoFragment.adaptadorComidaEnCarrito.notifyDataSetChanged();
            }

        }

    }

    private void agregarComidas() {

        Query comidas = FirebaseFirestore.getInstance()
                .collection("Menu "+VariablesGenerales.NEGOCIO_ACTUAL);

        comidas.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(QuerySnapshot documentSnapshots, FirebaseFirestoreException e) {

                listaComida.removeAll(listaComida);
                for (DocumentSnapshot snapshot :
                        documentSnapshots) {
                    String a = "";
                    if (snapshot.getString("nombre").length() >=28){
                        a = snapshot.getString("nombre").substring(0,24)+"...";
                    }else{
                        a= snapshot.getString("nombre");
                    }
                    ComidaMenu comida = new ComidaMenu(snapshot.getString("imagen")
                                , a
                                , snapshot.getDouble("precio").intValue(),0);

                    listaComida.add(comida);
                    adaptadorComidaEnMenu.notifyDataSetChanged();

                }
            }
        });

    }

    private void agregarFotos() {
        Query fotos = firestore.collection("Negocios").whereEqualTo("nombre",VariablesGenerales.NEGOCIO_ACTUAL);
        fotos.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(QuerySnapshot documentSnapshots, FirebaseFirestoreException e) {

                listaFotos.removeAll(listaFotos);

                for (DocumentSnapshot snapshot :
                        documentSnapshots) {
                    FotoVPMenu foto = new FotoVPMenu(snapshot.getString("imagen_producto_estrella"));
                    FotoVPMenu foto2 = new FotoVPMenu(snapshot.getString("imagen_negocio_fisico"));
                    listaFotos.add(foto);
                    listaFotos.add(foto2);
                    adaptadorViewPager.notifyDataSetChanged();
                }
            }
        });

    }


    @Override
    protected void onStart() {
        super.onStart();


        Slide();

        ctlLayout.setTitle("");

        recyclerComida.setLayoutManager(new GridLayoutManager(this,2));

        agregarComidas();
        agregarFotos();

        adaptadorComidaEnMenu = new AdaptadorComidaEnMenu(listaComida,this,clickAgregar());
        adaptadorViewPager = new AdaptadorFotosDelNegocio(listaFotos,this);



        recyclerComida.setAdapter(adaptadorComidaEnMenu);
        viewPagerFotos.setAdapter(adaptadorViewPager);
        indicadorVP.setViewPager(viewPagerFotos);
        adaptadorViewPager.registerDataSetObserver(indicadorVP.getDataSetObserver());

        personalizarToolbar();
        implementarInformacion();

        carta_ver_carrito.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bloquearScroll();
                carta_ver_carrito.setAlpha(0);
                slideUp.show();
            }
        });


    }

    private void implementarInformacion() {

        nombre_carta.setText(VariablesGenerales.NEGOCIO_ACTUAL);

        FirebaseFirestore.getInstance()
                .collection("Negocios")
                .whereEqualTo("nombre",VariablesGenerales.NEGOCIO_ACTUAL)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(QuerySnapshot documentSnapshots, FirebaseFirestoreException e) {

                        for (DocumentSnapshot snapshot :
                                documentSnapshots) {

                            horario_carta.setText(new StringBuilder()
                                    .append(snapshot.getDouble("horario_abre").intValue())
                                    .append(":00 - ")
                                    .append(snapshot.getDouble("horario_cierra").intValue())
                                    .append(":00").toString());
                            tiempo_espera.setText(new StringBuilder()
                                    .append(snapshot.getDouble("tiempo_min")
                                            .intValue()).append(" - ")
                                    .append(snapshot.getDouble("tiempo_max").intValue())
                                    .append(" min").toString());

                        }
                    }
                });
    }

    private void personalizarToolbar() {

        AppBarLayout appBarLayout = findViewById(R.id.appbar_menu);

        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {

            boolean isShow = false;
            int scrollRange = -1;

            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {

                if (scrollRange == -1){
                    scrollRange = appBarLayout.getTotalScrollRange();
                }if (scrollRange+verticalOffset == 0){
                    ctlLayout.setTitle(VariablesGenerales.NEGOCIO_ACTUAL);
                    carta_info_negocio.setAlpha(0);
                    isShow=true;
                }else if(isShow){
                    ctlLayout.setTitle("");
                    carta_info_negocio.setAlpha(1);
                    isShow=false;
                }
            }
        });

    }

    @Override
    public boolean onSupportNavigateUp() {

        onBackPressed();

        return true;
    }

    @Override
    public void onBackPressed() {

        if (slideUp.isVisible()){
            slideUp.hide();
        }else {

            CarritoFragment.listaComidas.removeAll(CarritoFragment.listaComidas);
            listaComida.removeAll(listaComida);
            precio  = 0;
            VariablesGenerales.PLATO_ACTUAL=0;
            ticker_ver_carrito.setText(precio+"$",false);

            List<PlatoBD> listaPlatos = Select.from(PlatoBD.class)
                    .where(Condition.prop("numero_de_pedido").eq(VariablesGenerales.PEDIDO_ACTUAL)).list();

            for (PlatoBD plato :
                    listaPlatos) {
                plato.delete();
            }
            List<NombresDeComidaEnPlato> listaNombres =
                    Select.from(NombresDeComidaEnPlato.class)
                            .where(Condition.prop("numero_de_pedido").eq(VariablesGenerales.PEDIDO_ACTUAL)).list();

            for (NombresDeComidaEnPlato nombresDeComidaEnPlato :
                    listaNombres) {

                nombresDeComidaEnPlato.delete();

            }

            super.onBackPressed();
        }

    }

}

