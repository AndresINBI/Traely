package studios.gomez.com.tomatlan.traely;


import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginManager;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import studios.gomez.com.tomatlan.traely.Objetos.Pedido;


public class Home extends AppCompatActivity {

    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

    TextView titulo;
    int FragmentActivo=3;
    BottomNavigationView navigation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(this);

        Toolbar toolbarHome = findViewById(R.id.toolbar_home);
        setSupportActionBar(toolbarHome);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        titulo = findViewById(R.id.titulo_home);


        iniciarBottomNV();

        borrarNoCompletados();

    }

    private void borrarNoCompletados() {




    }


    private void detectarCambiosEnElUser() {

            if (user!=null) {
                user.getIdToken(true)
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {

                                if (e.getMessage().contains("USER_DISABLED")) {
                                    Intent irALogin = new Intent(Home.this, Login.class);
                                    irALogin.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

                                    FirebaseAuth.getInstance().signOut();

                                    if (AccessToken.getCurrentAccessToken() != null) {
                                        LoginManager.getInstance().logOut();
                                    }
                                    startActivity(irALogin);


                                    Toast.makeText(Home.this,
                                            "Has sido inhabilitado temporalmente por mal uso de la app",
                                            Toast.LENGTH_LONG).show();

                                    finish();
                                }

                            }
                        });
            }

    }

    private void iniciarBottomNV() {
        navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        navigation.setSelectedItemId(R.id.home_bottom_home);
    }


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {


            switch (item.getItemId()) {

                case R.id.home_bottom_negocios:

                    detectarCambiosEnElUser();
                    reestablecerListaDeNegocios();

                    setFragment(0);

                    FragmentActivo=0;

                    return true;

                case R.id.home_bottom_home:

                    detectarCambiosEnElUser();
                    reestablecerListaDeNegocios();
                    setFragment(1);
                    FragmentActivo=1;

                return true;

                case R.id.home_bottom_carrito:

                    detectarCambiosEnElUser();
                    reestablecerListaDeNegocios();
                    setFragment(2);
                    FragmentActivo=2;

                    return true;

            }
            return false;
        }
    };

    private void reestablecerListaDeNegocios() {

        VariablesGenerales.VIENE_DE_CATEGORIA=false;
        VariablesGenerales.CATEGORIA_ACTUAL="";


    }

    private void setFragment(int position) {

        FragmentManager fragmentManager = getSupportFragmentManager();

        FragmentTransaction fragmentTransaction;

        if (FragmentActivo==position){

        }else {
            Animation fade_in = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.fade_in);
            Animation fade_out = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.fade_out);
            fade_out.reset();
            fade_in.reset();

            fragmentTransaction = fragmentManager.beginTransaction();

            switch (position){

                case 0:
                    NegociosFragment negociosFragment = new NegociosFragment();
                    fragmentTransaction.replace(R.id.contenedor_home,negociosFragment);

                    titulo.startAnimation(fade_out);
                    titulo.setText("Negocios");
                    titulo.startAnimation(fade_in);

                    fragmentTransaction.commit();

                    break;

                case 1:
                    Inicio inicioFragment = new Inicio();
                    fragmentTransaction.replace(R.id.contenedor_home,inicioFragment);

                    titulo.startAnimation(fade_out);
                    titulo.setText("Inicio");
                    titulo.startAnimation(fade_in);

                    fragmentTransaction.commit();

                    break;

                case 2:
                    MisPedidosFragment misPedidosFragment = new MisPedidosFragment();
                    fragmentTransaction.replace(R.id.contenedor_home,misPedidosFragment);

                    titulo.startAnimation(fade_out);
                    titulo.setText("Mis Pedidos");
                    titulo.startAnimation(fade_in);

                    fragmentTransaction.commit();

                    break;

            }

        }






    }


    @Override
    protected void onStart() {
        super.onStart();

        detectarCambiosEnElUser();

        if (getFirstTimeRun()==0){
            Toast.makeText(this, "Es 1", Toast.LENGTH_SHORT).show();
            VariablesGenerales.PEDIDO_ACTUAL = 1;
        }else {

            if (Pedido.last(Pedido.class) != null) {
                Toast.makeText(this, "+1", Toast.LENGTH_SHORT).show();
                VariablesGenerales.PEDIDO_ACTUAL = Pedido.last(Pedido.class).getNumeroDePedido() + 1;
            }else{
                Toast.makeText(this, "Sigue siendo 1", Toast.LENGTH_SHORT).show();
                VariablesGenerales.PEDIDO_ACTUAL=1;
            }

        }

    }


    private int getFirstTimeRun() {
        SharedPreferences sp = getSharedPreferences("Traely", 0);
        int result, currentVersionCode = BuildConfig.VERSION_CODE;
        int lastVersionCode = sp.getInt("FIRSTTIMERUN", -1);
        if (lastVersionCode == -1) result = 0; else
            result = (lastVersionCode == currentVersionCode) ? 1 : 2;
        sp.edit().putInt("FIRSTTIMERUN", currentVersionCode).apply();
        return result;
    }


    @Override
    public void onBackPressed() {

        if (navigation.getSelectedItemId() == R.id.home_bottom_home) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.contenedor_home,new Inicio()).commit();

        }else {
        navigation.setSelectedItemId(R.id.home_bottom_home);

        }


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_home,menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        Intent irAAyuda = new Intent(Home.this,OnBoardingPage.class);

        startActivity(irAAyuda);


        return super.onOptionsItemSelected(item);
    }
}
