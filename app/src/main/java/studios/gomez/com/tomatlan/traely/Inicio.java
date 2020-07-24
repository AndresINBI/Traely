package studios.gomez.com.tomatlan.traely;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.DocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

import me.relex.circleindicator.CircleIndicator;
import studios.gomez.com.tomatlan.traely.Adaptadores.AdaptadorCategorias;
import studios.gomez.com.tomatlan.traely.Adaptadores.AdaptadorPromociones;
import studios.gomez.com.tomatlan.traely.Objetos.Categoria;
import studios.gomez.com.tomatlan.traely.Objetos.Promocion;


public class Inicio extends Fragment {

    RecyclerView recyclerCategorias;
    List<Categoria> listaCategorias = new ArrayList<>();
    AdaptadorCategorias adaptadorCategorias;
    List<Promocion> listaPromociones = new ArrayList<>();
    AdaptadorPromociones adaptadorPromociones;
    ViewPager viewPagerPromociones;
    FirebaseFirestore Firestore = FirebaseFirestore.getInstance();
    CircleIndicator circleIndicator;


    public Inicio() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_inicio,container,false);

        recyclerCategorias = view.findViewById(R.id.recycler_categorias);
        viewPagerPromociones = view.findViewById(R.id.view_pager_inicio);
        circleIndicator = view.findViewById(R.id.circle_inicio);


        agregarPromociones();

        adaptadorPromociones = new AdaptadorPromociones(listaPromociones,getContext());

        viewPagerPromociones.setAdapter(adaptadorPromociones);
        circleIndicator.setViewPager(viewPagerPromociones);
        adaptadorPromociones.registerDataSetObserver(circleIndicator.getDataSetObserver());

        agregarCategorias();

        implementarRecyclerView();

        return view;
    }

    private void agregarPromociones() {

        Query promociones = Firestore.collection("Promociones");

        final ListenerRegistration listenerPromociones = promociones.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(QuerySnapshot documentSnapshots, FirebaseFirestoreException e) {

                listaPromociones.removeAll(listaPromociones);

                for (DocumentSnapshot documentSnapshot:
                        documentSnapshots) {
                    Promocion promocion = new Promocion(documentSnapshot.getString("imagen"));
                    listaPromociones.add(promocion);

                    adaptadorPromociones.notifyDataSetChanged();

                }
            }
        });


    }

    private void implementarRecyclerView() {

        recyclerCategorias.setLayoutManager(new GridLayoutManager(getContext(),2));



        adaptadorCategorias = new AdaptadorCategorias(listaCategorias,getContext(),implementarClickCategoria());

        recyclerCategorias.setAdapter(adaptadorCategorias);

    }

    private AdaptadorCategorias.ClickHandler implementarClickCategoria() {

        AdaptadorCategorias.ClickHandler clickCategoria = new AdaptadorCategorias.ClickHandler() {
            @Override
            public void onClick(int position) {

                mostrarNegocios(listaCategorias.get(position).getNombre());

            }
        };

        return clickCategoria;
    }

    private void mostrarNegocios(String nombre) {

        VariablesGenerales.VIENE_DE_CATEGORIA = true;
        VariablesGenerales.CATEGORIA_ACTUAL =  nombre;

        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        NegociosFragment negociosFragment = new NegociosFragment();
        fragmentTransaction.replace(R.id.contenedor_home,negociosFragment);
        fragmentTransaction.commit();

    }

    private void agregarCategorias() {

        Query categorias = Firestore.collection("Categorias");

        final ListenerRegistration listenerCategorias = categorias.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(QuerySnapshot documentSnapshots, FirebaseFirestoreException e) {

                listaCategorias.removeAll(listaCategorias);

                for (DocumentSnapshot documentSnapshot :
                        documentSnapshots) {

                    Categoria categoria =
                            new Categoria(documentSnapshot.getString("nombre"),
                                    documentSnapshot.getString("icono"));
                    listaCategorias.add(categoria);

                    adaptadorCategorias.notifyDataSetChanged();

                }

            }
        });


    }






}
