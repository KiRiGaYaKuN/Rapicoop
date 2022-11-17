package com.example.rapicoop;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.rapicoop.db.InsertarUsuario;
import com.example.rapicoop.db.RapicoopDatabase;

public class Ruta extends AppCompatActivity {

    public static final String EXTRA_MESSAGE="mesagge";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ruta);

        RapicoopDatabase rapidb = new RapicoopDatabase(Ruta.this);
        InsertarUsuario iu = new InsertarUsuario(Ruta.this);

        Intent it=getIntent();
        String usuario = it.getStringExtra(EXTRA_MESSAGE);

        TextView mensaje = (TextView) findViewById(R.id.mensajecarrito);
        Button boton = (Button) findViewById(R.id.iroferta);

        OfertaAceptada ofertaA = iu.consultaesperanombre(usuario);

        if (ofertaA != null && (ofertaA.getEstado().equals("proceso") || ofertaA.getEstado().equals("entregando"))) {
            Oferta oferta = iu.consultanombre(ofertaA.getOferta());
            Uri.Builder builder = new Uri.Builder();
            builder.scheme("https")
                    .authority("www.google.com")
                    .appendPath("maps")
                    .appendPath("dir")
                    .appendPath("")
                    .appendQueryParameter("api", "1")
                    .appendQueryParameter("origin", oferta.getUbicacion())
                    .appendQueryParameter("destination", ofertaA.getUbicacion());
            String url = builder.build().toString();
            Log.d("Direccion", url);
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse(url));
            startActivity(intent);
        }else {
            mensaje.setVisibility(View.VISIBLE);
            boton.setVisibility(View.VISIBLE);
        }

        boton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Ruta.this, Pedidosdomi.class);
                i.putExtra(Pedidosdomi.EXTRA_MESSAGE, usuario);
                startActivity(i);
            }
        });
    }

    @Override
    protected void onStop() {
        super.onStop();
        finish();
    }
}