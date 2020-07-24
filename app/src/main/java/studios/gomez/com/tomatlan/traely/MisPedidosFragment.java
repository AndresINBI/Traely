package studios.gomez.com.tomatlan.traely;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.facebook.AccessToken;
import com.facebook.FacebookSdk;
import com.facebook.LoginStatusCallback;
import com.facebook.login.LoginManager;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.firebase.auth.FacebookAuthCredential;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.orm.query.Condition;
import com.orm.query.Select;

import java.util.ArrayList;
import java.util.List;

import studios.gomez.com.tomatlan.traely.Adaptadores.AdaptadorPedido;
import studios.gomez.com.tomatlan.traely.Objetos.Pedido;


public class MisPedidosFragment extends Fragment {

    public MisPedidosFragment() {
        // Required empty public constructor
    }

    ImageView foto_perfil;
    TextView nombre_usuario;
    Button boton_cerrar_sesion;

    RecyclerView recyclerView;
    AdaptadorPedido adaptadorPedido;
    List<Pedido> listaPedidos = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_mis_pedidos,container,false);

        foto_perfil = v.findViewById(R.id.foto_perfil);
        nombre_usuario = v.findViewById(R.id.nombre_usuario);
        boton_cerrar_sesion = v.findViewById(R.id.boton_cerrar_sesion);
        recyclerView = v.findViewById(R.id.recycler_pedidos);


        boton_cerrar_sesion.setOnClickListener(listenerCerrarSesion());

        ponerPerfil();



        // Inflate the layout for this fragment
        return v;
    }

    private View.OnClickListener listenerCerrarSesion() {

        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();

                if (AccessToken.getCurrentAccessToken()!=null){
                    LoginManager.getInstance().logOut();
                }else{
                    cerrarGoogle();

                }
                Intent irALogin = new Intent(getActivity(),Login.class);
                irALogin.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(irALogin);
                getActivity().finish();

            }

            private void cerrarGoogle() {
                GoogleSignInOptions gso = new GoogleSignInOptions
                        .Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                        .requestEmail()
                        .requestIdToken(getString(R.string.default_web_client_id))
                        .build();
                GoogleSignInClient gSC = GoogleSignIn.getClient(getContext(),gso);
                gSC.signOut();
            }
        };
    }

    private void ponerPerfil() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        Glide.with(getContext())
                .load(user.getPhotoUrl())
                .fitCenter()
                .into(foto_perfil);
        nombre_usuario.setText(user.getDisplayName());
    }

    @Override
    public void onStart() {
        super.onStart();

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        listaPedidos = Select.from(Pedido.class).orderBy("numero_de_pedido DESC").list();

        adaptadorPedido = new AdaptadorPedido(listaPedidos, new AdaptadorPedido.ClickPedido() {
            @Override
            public void onClick(int position) {

                Intent irAVerPedido = new Intent(getContext(),VerPedido.class);
                irAVerPedido.putExtra("position",VariablesGenerales.PEDIDO_ACTUAL-1-position);
                Toast.makeText(getContext(), ""+(VariablesGenerales.PEDIDO_ACTUAL-1-position), Toast.LENGTH_SHORT).show();
                startActivity(irAVerPedido);


            }
        });

        recyclerView.setAdapter(adaptadorPedido);


    }
}
