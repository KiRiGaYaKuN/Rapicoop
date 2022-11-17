package com.example.rapicoop;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.rapicoop.db.InsertarUsuario;
import com.example.rapicoop.db.RapicoopDatabase;

import java.util.Calendar;

public class Estado extends AppCompatActivity {

    public static final String EXTRA_MESSAGE="mesagge";
    Button entrega, fin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_estado);

        RapicoopDatabase rapidb = new RapicoopDatabase(Estado.this);
        InsertarUsuario iu = new InsertarUsuario(Estado.this);

        Intent it=getIntent();
        String usuario = it.getStringExtra(EXTRA_MESSAGE);

        OfertaAceptada ofertaA = iu.consultaproceso(usuario);

        entrega = findViewById(R.id.entrega);
        fin = findViewById(R.id.fin);

        entrega.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (ofertaA != null && ofertaA.getEstado().equals("proceso")) {

                    AlertDialog.Builder alerta = new AlertDialog.Builder(Estado.this);
                    alerta.setMessage("¿Esta seguro de que le entregaron todos los productos solicitados por el cliente?")
                            .setCancelable(false)
                            .setPositiveButton("Si", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    if (iu.modestado(ofertaA.getId(),"entregando")) {
                                        ofertaA.setEstado("entregando");
                                        Intent intent = new Intent(Estado.this,NotificarService.class);
                                        intent.putExtra(NotificarService.EXTRA_MESSAGE,"entrega");
                                        startService(intent);
                                        Toast.makeText(Estado.this, "reportado", Toast.LENGTH_SHORT).show();
                                    }else {
                                        Toast.makeText(Estado.this, "error", Toast.LENGTH_SHORT).show();
                                    }
                                    dialogInterface.cancel();
                                }
                            })
                            .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    dialogInterface.cancel();
                                }
                            });
                    AlertDialog titulo = alerta.create();
                    titulo.setTitle("Entregando");
                    titulo.show();
                } else{
                    Toast.makeText(Estado.this, "No tiene un pedido o ya se encuentra entregando", Toast.LENGTH_SHORT).show();
                }
            }
        });

        fin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (ofertaA != null && ofertaA.getEstado().equals("entregando")) {

                    AlertDialog.Builder alerta = new AlertDialog.Builder(Estado.this);
                    alerta.setMessage("¿Esta seguro de que ha entregado el pedido al cliente?")
                            .setCancelable(false)
                            .setPositiveButton("Si", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    if (iu.modestado(ofertaA.getId(),"finalizado")) {
                                        ofertaA.setEstado("finalizado");

                                        long ahora = System.currentTimeMillis();
                                        Calendar calendario = Calendar.getInstance();
                                        calendario.setTimeInMillis(ahora);
                                        int ho = calendario.get(Calendar.HOUR_OF_DAY);
                                        int mo = calendario.get(Calendar.MINUTE);
                                        int se = calendario.get(Calendar.SECOND);
                                        String time = ho+":"+mo+":"+se;

                                        String[] partes = ofertaA.getInicio().split(":");
                                        int hora = Integer.parseInt(partes[0]);
                                        int minuto = Integer.parseInt(partes[1]);
                                        int seg = Integer.parseInt(partes[2]);
                                        String duracion = Math.abs(ho-hora)+":"+Math.abs(mo-minuto)+":"+Math.abs(seg-se);

                                        if (iu.modfinal(ofertaA.getId(),time,duracion)){
                                            Toast.makeText(Estado.this, "funciona", Toast.LENGTH_SHORT).show();
                                        }
                                        Intent intent = new Intent(Estado.this,NotificarService.class);
                                        intent.putExtra(NotificarService.EXTRA_MESSAGE,"final");
                                        startService(intent);
                                        Toast.makeText(Estado.this, "reportado", Toast.LENGTH_SHORT).show();
                                    }else {
                                        Toast.makeText(Estado.this, "error", Toast.LENGTH_SHORT).show();
                                    }
                                    dialogInterface.cancel();
                                }
                            })
                            .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    dialogInterface.cancel();
                                }
                            });
                    AlertDialog titulo = alerta.create();
                    titulo.setTitle("Finalizado");
                    titulo.show();
                }else {
                    Toast.makeText(Estado.this, "No tiene un pedido o todavia no reporto entregando", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }
}