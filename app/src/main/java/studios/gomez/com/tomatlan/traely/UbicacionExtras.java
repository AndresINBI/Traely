package studios.gomez.com.tomatlan.traely;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class UbicacionExtras extends AppCompatActivity {

    FloatingActionButton boton_irAConfirmar;
    EditText editUbicacion,editExtras;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ubicacion_extras);
        Toolbar toolbar  = findViewById(R.id.toolbar_ubicacion);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        setTitle("Agregar Datos");

        //todo Detectar ubicacion
        instanciarObjetos();

    }

    private View.OnClickListener ClickIrAConfirmar() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String extras = editExtras.getText().toString();
                String Ubicacion = editUbicacion.getText().toString();

                if (Ubicacion.equals("")){
                    Toast.makeText(UbicacionExtras.this, "Agrega tu Ubicación", Toast.LENGTH_SHORT).show();
                }else {

                    irAConfirmar(extras,Ubicacion);

                }

            }
        };
    }

    private void irAConfirmar(String extras, String ubicacion) {

        Intent irAConfirmar = new Intent(this,ConfirmarPedido.class);
        irAConfirmar.putExtra("ubicacion",ubicacion);
        irAConfirmar.putExtra("extras",extras);
        startActivity(irAConfirmar);

    }

    private void instanciarObjetos() {

        editExtras = findViewById(R.id.editExtras);
        editUbicacion = findViewById(R.id.editUbicacion);
        boton_irAConfirmar = findViewById(R.id.boton_irAConfirmar);


    }


    @Override
    public boolean onSupportNavigateUp() {

        onBackPressed();
        return true;
    }

    @Override
    protected void onStart() {
        super.onStart();

        boton_irAConfirmar.setOnClickListener(ClickIrAConfirmar());

    }
}
