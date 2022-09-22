package com.example.rapicoop;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Consumidor extends AppCompatActivity {

    Button consulta;
    public static final String EXTRA_MESSAGE="mesagge";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consumidor);

        consulta = (Button) findViewById(R.id.consultarcomida);

        Intent intent=getIntent();
        String usuario = intent.getStringExtra(EXTRA_MESSAGE);

        consulta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Intent i = new Intent(Consumidor.this, Ofertasconsumidor.class);
    //            i.putExtra(Ofertarcomida.EXTRA_MESSAGE, usuario);
                startActivity(i);
            }
        });
    }
}