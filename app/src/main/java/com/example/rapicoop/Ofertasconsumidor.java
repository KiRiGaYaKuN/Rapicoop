package com.example.rapicoop;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.TextView;

import com.example.rapicoop.db.InsertarUsuario;
import com.example.rapicoop.db.RapicoopDatabase;

import java.util.ArrayList;
import java.util.List;

public class Ofertasconsumidor extends AppCompatActivity {

    public static final String EXTRA_MESSAGE="mesagge";
    List<Listadeelementos> elements;
    ArrayList<Oferta> listaofertas;

    TextView usu;
    TextView tipo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ofertasconsumidor);

        RapicoopDatabase rapidb = new RapicoopDatabase(Ofertasconsumidor.this);
        InsertarUsuario iu = new InsertarUsuario(Ofertasconsumidor.this);
        Intent intent=getIntent();
        String usuario = intent.getStringExtra(EXTRA_MESSAGE);
        String rol = iu.devolver(usuario);
        listaofertas = new ArrayList<Oferta>();

        usu = (TextView) findViewById(R.id.usuario);
        tipo = (TextView) findViewById(R.id.tipo);
        tipo.setText(rol);
        usu.setText(usuario);
        if (rol.equals("Vendedor")){
            listaofertas = iu.consultavende(usuario);
        }else{
            listaofertas = iu.consultaconsume();
        }
            init();


    }

    public void  init() {
        elements = new ArrayList<>();

        //    elements.add(new Listadeelementos("#FFB562", "Papa Jhons", "Todos los dias 10am-10pm", "Abierto"));

        for (Oferta x: listaofertas) {
            String palo = "" + x.getPrecio();
            elements.add(new Listadeelementos("#FFB562", x.getNombre(), x.getUbicacion(), palo));
        }

        AdaptadorLista adaptadorLista = new AdaptadorLista(elements, this);
        RecyclerView recyclerView = findViewById(R.id.listaRecycler);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adaptadorLista);


    }
}