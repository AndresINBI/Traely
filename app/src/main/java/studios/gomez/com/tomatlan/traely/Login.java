package studios.gomez.com.tomatlan.traely;


import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.dx.dxloadingbutton.lib.LoadingButton;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GoogleAuthCredential;
import com.google.firebase.auth.GoogleAuthProvider;

import java.util.Arrays;


public class Login extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener {

    Button FBLoginButton,GLoginButton;
    LoadingButton loadingButton;
    CardView cardLoginFB,cardLoginG;
    Handler handler = new Handler();

    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();

    CallbackManager callback = CallbackManager.Factory.create();

    GoogleApiClient googleApiClient;
    GoogleSignInClient googleSignInClient;

    TextView titulo;
    ImageView icono;

    private final int SIGN_IN_CODE = 777;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_login);

        FBLoginButton = findViewById(R.id.login_facebook_button);
        titulo = findViewById(R.id.titulo_Login);
        icono = findViewById(R.id.icono_Login);
        loadingButton = findViewById(R.id.loading_login);
        cardLoginFB = findViewById(R.id.cardLoginFB);
        GLoginButton = findViewById(R.id.boton_login_google);
        cardLoginG = findViewById(R.id.cardLoginGoogle);
        loadingButton.startLoading();


        habilitarLoginManagerFB();
        habilitarLoginGoogle();

        FBLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LoginManager.getInstance()
                        .logInWithReadPermissions(Login.this,Arrays.asList("public_profile","email"));
                ocultarLogo();

            }
        });

        GLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ocultarLogo();
                Intent intent = Auth.GoogleSignInApi.getSignInIntent(googleApiClient);
                startActivityForResult(intent,SIGN_IN_CODE);
            }
        });

        }

    private void habilitarLoginGoogle() {

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        googleApiClient = new GoogleApiClient.Builder(getApplicationContext())
                .enableAutoManage(Login.this,this)
                .addApi(Auth.GOOGLE_SIGN_IN_API,gso)
                .build();
        googleSignInClient = GoogleSignIn.getClient(Login.this,gso);

    }

    private void habilitarLoginManagerFB() {

        LoginManager.getInstance().registerCallback(callback, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {

                LoginFirebaseFB(loginResult.getAccessToken());

            }

            @Override
            public void onCancel() {
                ocultarProgressBar();
            }

            @Override
            public void onError(FacebookException error) {

                loadingButton.loadingFailed();

                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        LoginManager.getInstance().logOut();
                        ocultarProgressBar();
                    }
                },1500);

            }
        });

    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try{

            GoogleSignInAccount account = completedTask.getResult(ApiException.class);

            LoginFirebaseGoogle(account);


        }catch (ApiException e){

        }


    }

    private void LoginFirebaseFB(AccessToken accessToken) {
        AuthCredential credentialFB = FacebookAuthProvider.getCredential(accessToken.getToken());

        firebaseAuth.signInWithCredential(credentialFB).addOnFailureListener(this, new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                final Exception a = e;
                loadingButton.loadingFailed();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        if (a.getMessage().contains("USER_DISABLED")){

                            Intent irALogin = new Intent(Login.this,Login.class);
                            irALogin.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

                            FirebaseAuth.getInstance().signOut();
                            LoginManager.getInstance().logOut();

                            Toast.makeText(Login.this,
                                    "Has sido inhabilitado temporalmente por mal uso de la app",
                                    Toast.LENGTH_LONG).show();

                            startActivity(irALogin);
                            finish();
                        }
                    }
                },1500);
            }
        }).addOnCompleteListener(this,new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {


                if (firebaseAuth.getCurrentUser() != null){

                    loadingButton.loadingSuccessful();

                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            Intent irAMain = new Intent(Login.this,Home.class);
                            startActivity(irAMain);
                            irAMain.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            finish();
                        }
                    },1500);

                }

                if (!task.isSuccessful()){

                    loadingButton.loadingFailed();

                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            LoginManager.getInstance().logOut();
                            ocultarProgressBar();
                            loadingButton.reset();
                        }
                    },1500);

                }
            }
        });
    }

    private void LoginFirebaseGoogle(GoogleSignInAccount account) {

        AuthCredential credentialG = GoogleAuthProvider.getCredential(account.getIdToken(),null);
        firebaseAuth.signInWithCredential(credentialG).addOnFailureListener(this, new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                final Exception a = e;

                loadingButton.loadingFailed();

                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        if (a.getMessage().contains("USER_DISABLED")){

                            Intent irALogin = new Intent(Login.this,Login.class);
                            irALogin.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

                            FirebaseAuth.getInstance().signOut();
                            googleSignInClient.signOut();
                            ocultarProgressBar();
                            loadingButton.reset();

                            startActivity(irALogin);

                            Toast.makeText(Login.this,
                                    "Has sido inhabilitado temporalmente por mal uso de la app",
                                    Toast.LENGTH_LONG).show();

                            finish();
                        }

                    }
                },1500);
            }
        }).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {

                if (firebaseAuth.getCurrentUser() != null) {

                    loadingButton.loadingSuccessful();

                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            Intent irAMain = new Intent(Login.this, Home.class);
                            startActivity(irAMain);
                            irAMain.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            finish();
                        }
                    }, 1500);

                }
            }
        });

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);



        if (requestCode == SIGN_IN_CODE){

            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);


        }else {
            callback.onActivityResult(requestCode, resultCode, data);
        }



    }

    private void ocultarLogo() {
        titulo.setVisibility(View.INVISIBLE);
        icono.setVisibility(View.INVISIBLE);
        cardLoginFB.setVisibility(View.INVISIBLE);
        cardLoginG.setVisibility(View.INVISIBLE);
        loadingButton.setVisibility(View.VISIBLE);
    }

    private void ocultarProgressBar() {
        titulo.setVisibility(View.VISIBLE);
        icono.setVisibility(View.VISIBLE);
        cardLoginFB.setVisibility(View.VISIBLE);
        cardLoginG.setVisibility(View.VISIBLE);
        loadingButton.reset();
        loadingButton.startLoading();
        loadingButton.setVisibility(View.INVISIBLE);
    }
}
