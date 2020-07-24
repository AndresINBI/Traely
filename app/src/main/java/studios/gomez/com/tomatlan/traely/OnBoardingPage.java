package studios.gomez.com.tomatlan.traely;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import com.codemybrainsout.onboarder.AhoyOnboarderActivity;
import com.codemybrainsout.onboarder.AhoyOnboarderCard;

import java.util.ArrayList;
import java.util.List;

public class OnBoardingPage extends AhoyOnboarderActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        AhoyOnboarderCard card1 = new AhoyOnboarderCard("Traely",
                "Bienvenido",R.mipmap.ic_launcher);

        AhoyOnboarderCard card2 = new AhoyOnboarderCard("Ofertas",
                "Encuentra las ofertas de tus negocios favoritos",R.mipmap.ic_plato);

        List<AhoyOnboarderCard> pages = new ArrayList<>();
        pages.add(card1);
        pages.add(card2);

        setGradientBackground();



        setOnboardPages(pages);


    }

    @Override
    public void setOnboardPages(List<AhoyOnboarderCard> pages) {
        super.setOnboardPages(pages);
    }

    @Override
    public void onFinishButtonPressed() {

        onBackPressed();

        /*

        Intent irAMain = new Intent(OnBoardingPage.this,Home.class);
        startActivity(irAMain);
        */

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}
