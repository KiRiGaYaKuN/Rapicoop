package com.example.rapicoop;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Domilciliario extends AppCompatActivity {

    public static final String EXTRA_MESSAGE="mesagge";

    Button consulta, pedido, estado, reportar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_domilciliario);

        consulta = (Button) findViewById(R.id.entrega);
        pedido = (Button) findViewById(R.id.fin);
        estado = (Button) findViewById(R.id.estado);
        reportar = (Button) findViewById(R.id.ubi);

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

        pedido.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(Domilciliario.this, Ruta.class);
                i.putExtra(Ruta.EXTRA_MESSAGE, usuario);
                startActivity(i);
            }
        });

        estado.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(Domilciliario.this, Estado.class);
                i.putExtra(Estado.EXTRA_MESSAGE, usuario);
                startActivity(i);
            }
        });

        reportar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(Domilciliario.this, Ubicacion.class);
                i.putExtra(Estado.EXTRA_MESSAGE, usuario);
                startActivity(i);
            }
        });
    }
}