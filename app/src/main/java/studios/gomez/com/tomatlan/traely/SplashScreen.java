package studios.gomez.com.tomatlan.traely;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Timer;
import java.util.TimerTask;

public class SplashScreen extends AppCompatActivity {

    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_splash_screen);

        TimerTask task = new TimerTask() {
            @Override
            public void run() {

                if (user != null) {
                    Intent irAMain = new Intent(SplashScreen.this,Home.class);
                    startActivity(irAMain);
                    finish();

                } else{
                    Intent irALogin = new Intent(SplashScreen.this, Login.class);
                    startActivity(irALogin);
                    finish();
                }

            }
        };
        Timer timer = new Timer();
        timer.schedule(task,2000);


    }

}
