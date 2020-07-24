package studios.gomez.com.tomatlan.traely;



import android.Manifest;
import android.arch.lifecycle.Lifecycle;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import studios.gomez.com.tomatlan.traely.Adaptadores.AdaptadorNegocios;
import studios.gomez.com.tomatlan.traely.Adaptadores.AdaptadorNegocios.ClickHandler;
import studios.gomez.com.tomatlan.traely.Objetos.Negocio;

import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import static android.support.v4.content.ContextCompat.checkSelfPermission;


public class NegociosFragment extends Fragment {

    RecyclerView recyclerNegocios;
    AdaptadorNegocios adaptadorNegocios;
    List<Negocio> listaNegocios = new ArrayList<>();
    FirebaseFirestore Firestore = FirebaseFirestore.getInstance();
    Boolean hayPermisoLlamar = false;


    public NegociosFragment() {
        // Required empty public constructor
    }




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_negocios,container,false);

        verificarPermisoLlamar();

        recyclerNegocios = v.findViewById(R.id.recycler_negocios);
        recyclerNegocios.setLayoutManager(new LinearLayoutManager(getContext()));



        adaptadorNegocios = new AdaptadorNegocios(listaNegocios,getContext(),
                declararClicks(1),
                declararClicks(2));
        recyclerNegocios.setAdapter(adaptadorNegocios);






        // Inflate the layout for this fragment
        return v;
    }

    private void agregarNegocios(Boolean con_categoria) {

        final Query Negocios;
        ListenerRegistration listenerNegocios;

        if (con_categoria){
            //todo cuando viene de una categoria

        Firestore.collection("Negocios")
                .whereEqualTo("categoria1",VariablesGenerales.CATEGORIA_ACTUAL)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(QuerySnapshot documentSnapshots, FirebaseFirestoreException e) {

                        listaNegocios.removeAll(listaNegocios);

                        for (DocumentSnapshot snapshot :
                                documentSnapshots) {

                            listaNegocios.add(new Negocio(snapshot.getString("nombre"),
                                    snapshot.getString("telefono"),
                                    snapshot.getString("categoria1"),
                                    snapshot.getString("categoria2"),
                                    snapshot.getString("imagen_producto_estrella"),
                                    snapshot.getString("imagen_negocio_fisico"),
                                    snapshot.getString("direccion"),
                                    snapshot.getString("telefono"),
                                    snapshot.getDouble("cantidad_de_productos").intValue(),
                                    snapshot.getDouble("tiempo_min").intValue(),
                                    snapshot.getDouble("tiempo_max").intValue(),
                                    snapshot.getDouble("horario_abre").intValue(),
                                    snapshot.getDouble("horario_cierra").intValue()));

                            adaptadorNegocios.notifyDataSetChanged();

                        }
                    }
                });



        }else {
            Negocios = Firestore.collection("Negocios").orderBy("nombre", Query.Direction.DESCENDING);
            listenerNegocios = Negocios.addSnapshotListener(new EventListener<QuerySnapshot>() {
                @Override
                public void onEvent(QuerySnapshot documentSnapshots, FirebaseFirestoreException e) {

                    listaNegocios.removeAll(listaNegocios);
                    for (DocumentSnapshot documentSnapshot :
                            documentSnapshots) {
                        Negocio negocio =
                                new Negocio(documentSnapshot.getString("nombre"),
                                        documentSnapshot.getString("telefono"),
                                        documentSnapshot.getString("categoria1"),
                                        documentSnapshot.getString("categoria2"),
                                        documentSnapshot.getString("imagen_producto_estrella"),
                                        documentSnapshot.getString("imagen_negocio_fisico"),
                                        documentSnapshot.getString("direccion"),
                                        documentSnapshot.getString("telefono"),
                                        documentSnapshot.getDouble("cantidad_de_productos").intValue(),
                                        documentSnapshot.getDouble("tiempo_min").intValue(),
                                        documentSnapshot.getDouble("tiempo_max").intValue(),
                                        documentSnapshot.getDouble("horario_abre").intValue(),
                                        documentSnapshot.getDouble("horario_cierra").intValue());
                        listaNegocios.add(negocio);

                        adaptadorNegocios.notifyDataSetChanged();
                    }

                }
            });
        }


    }

    private ClickHandler declararClicks(int llamar_menu) {

        ClickHandler clickHandler = null;


        switch (llamar_menu){

            case 1:

                clickHandler = new ClickHandler() {
                    @Override
                    public void onClick(int position) {

                        if (hayPermisoLlamar){
                            Intent llamar = new Intent(Intent.ACTION_CALL);
                            llamar.setData(Uri.parse("tel:"+listaNegocios.get(position).getTelefono()));
                            startActivity(llamar);
                        }else {
                            verificarPermisoLlamar();
                        }

                    }
                };

                break;

            case 2:

                clickHandler = new ClickHandler() {
                    @Override
                    public void onClick(int position) {

                        irAMenu(position);

                    }
                };

                break;


        }

        return clickHandler;

    }

    private void irAMenu(int position) {
        VariablesGenerales.NEGOCIO_ACTUAL = listaNegocios.get(position).getNombre();
        Intent irAMenu = new Intent(getActivity(),MenuNegocio.class);
        startActivity(irAMenu);

    }

    private void verificarPermisoLlamar() {

        int permisoLlamar = checkSelfPermission(getContext(),Manifest.permission.CALL_PHONE);

        if (permisoLlamar != PackageManager.PERMISSION_GRANTED){

            pedirPermisoLlamar();

        }else {
            hayPermisoLlamar=true;
        }

    }


    private void pedirPermisoLlamar() {
            requestPermissions(new String[]{Manifest.permission.CALL_PHONE},123);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if(requestCode == 123){
            if (grantResults.length > 0 && grantResults[0]==PackageManager.PERMISSION_GRANTED){
                hayPermisoLlamar=true;
            }else {
                verificarPermisoLlamar();
                hayPermisoLlamar=false;
            }
        }
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        agregarNegocios(VariablesGenerales.VIENE_DE_CATEGORIA);


    }

}
