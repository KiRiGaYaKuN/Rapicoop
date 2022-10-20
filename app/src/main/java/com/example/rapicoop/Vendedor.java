package com.example.rapicoop;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Vendedor extends AppCompatActivity {

    Button ofertar;
    Button eliminar;
    public static final String EXTRA_MESSAGE="mesagge";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vendedor);

        ofertar = (Button) findViewById(R.id.OfertarComida);
        eliminar = (Button) findViewById(R.id.eliminar);

        Intent intent=getIntent();
        String usuario = intent.getStringExtra(EXTRA_MESSAGE);

        ofertar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Intent i = new Intent(Vendedor.this, Ofertarcomida.class);
                i.putExtra(Ofertarcomida.EXTRA_MESSAGE, usuario);
                startActivity(i);
            }
        });



        eliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(Vendedor.this, eliminarofertav.class);
                i.putExtra(Ofertarcomida.EXTRA_MESSAGE, usuario);
                startActivity(i);
            }
        });
    }
}