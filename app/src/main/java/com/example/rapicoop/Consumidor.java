package com.example.rapicoop;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class Consumidor extends AppCompatActivity {

    Button consulta;
    Button carrito;
    Button cerrar;
    public static final String EXTRA_MESSAGE="mesagge";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consumidor);

        consulta = (Button) findViewById(R.id.consultarcomida);
        carrito = (Button) findViewById(R.id.consultacarrito);
        cerrar = (Button) findViewById(R.id.cerrar);

        Intent intent=getIntent();
        String usuario = intent.getStringExtra(EXTRA_MESSAGE);

        consulta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(Consumidor.this, Ofertasconsumidor.class);
                i.putExtra(Ofertasconsumidor.EXTRA_MESSAGE, usuario);
                startActivity(i);
            }
        });

        carrito.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(Consumidor.this, carritodecompras.class);
                i.putExtra(carritodecompras.EXTRA_MESSAGE, usuario);
                startActivity(i);
            }
        });

        cerrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent init = new Intent(Consumidor.this, LogIn.class);

                AlertDialog.Builder alerta = new AlertDialog.Builder(Consumidor.this);
                alerta.setMessage("¿Esta seguro de cerrar su sesión?")
                        .setCancelable(false)
                        .setPositiveButton("Si", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                                startActivity(init);
                                dialogInterface.cancel();
                                finish();
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.cancel();
                            }
                        });
                AlertDialog titulo = alerta.create();
                titulo.setTitle("Cerrar sesión");
                titulo.show();


            }
        });
    }
}