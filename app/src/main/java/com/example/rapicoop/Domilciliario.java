package com.example.rapicoop;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Domilciliario extends AppCompatActivity {

    public static final String EXTRA_MESSAGE="mesagge";

    Button consulta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_domilciliario);

        consulta = (Button) findViewById(R.id.pedido);

        Intent intent=getIntent();
        String usuario = intent.getStringExtra(EXTRA_MESSAGE);

        consulta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(Domilciliario.this, Pedidosdomi.class);
                i.putExtra(Pedidosdomi.EXTRA_MESSAGE, usuario);
                startActivity(i);
            }
        });

    }
}