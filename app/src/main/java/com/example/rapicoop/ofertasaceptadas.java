package com.example.rapicoop;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.rapicoop.db.InsertarUsuario;
import com.example.rapicoop.db.RapicoopDatabase;

import java.util.ArrayList;
import java.util.List;

public class ofertasaceptadas extends AppCompatActivity {

    public static final String EXTRA_MESSAGE="mesagge";
    List<Listadeelementos> elements;
    ArrayList<OfertaAceptada> listaofertas;
    RecyclerView recyclerView;
    AdaptadorAceptada adaptadorLista;
    String usuario;

    RapicoopDatabase rapidb = new RapicoopDatabase(ofertasaceptadas.this);
    InsertarUsuario iu = new InsertarUsuario(ofertasaceptadas.this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ofertasaceptadas);


        Intent intent=getIntent();
        usuario = intent.getStringExtra(EXTRA_MESSAGE);
        listaofertas = new ArrayList<OfertaAceptada>();

        listaofertas = iu.consultaaceptadas(usuario);
        init();

        adaptadorLista.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

    }

    public void  init() {
        elements = new ArrayList<>();

        for (OfertaAceptada x: listaofertas) {
            Oferta oferta = iu.consultanombre(x.getOferta());
            Bitmap bim = BitmapFactory.decodeByteArray(oferta.getImagen(),0,oferta.getImagen().length);
            elements.add(new Listadeelementos(bim, x.getOferta(), x.getUbicacion(), x.getEstado(), usuario));
        }

        adaptadorLista = new AdaptadorAceptada(elements, this,usuario);
        recyclerView = findViewById(R.id.lista);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adaptadorLista);
    }

}