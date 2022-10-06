package com.example.rapicoop;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;

import com.example.rapicoop.db.InsertarUsuario;
import com.example.rapicoop.db.RapicoopDatabase;

import java.util.ArrayList;
import java.util.List;

public class Ofertasconsumidor extends AppCompatActivity {

    public static final String EXTRA_MESSAGE="mesagge";
    List<Listadeelementos> elements;
    ArrayList<Oferta> listaofertas;
    RecyclerView recyclerView;
    AdaptadorLista adaptadorLista;

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
            init();
        }else{
            listaofertas = iu.consultaconsume();
            init();
        }


        adaptadorLista.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Oferta oferta = listaofertas.get(recyclerView.getChildAdapterPosition(view));
                Intent i = new Intent(Ofertasconsumidor.this, DescripcionOferta.class);
                String[] cap = {oferta.getNombre(),usuario};
                i.putExtra(String.valueOf(DescripcionOferta.EXTRA_MESSAGE), cap);
                startActivity(i);
            }
        });

    }

    public void  init() {
        elements = new ArrayList<>();

        //    elements.add(new Listadeelementos("#FFB562", "Papa Jhons", "Todos los dias 10am-10pm", "Abierto"));

        for (Oferta x: listaofertas) {
            String palo = "" + x.getPrecio();
            Bitmap bim = BitmapFactory.decodeByteArray(x.getImagen(),0,x.getImagen().length);
            elements.add(new Listadeelementos(bim, x.getNombre(), x.getUbicacion(), palo));
        }

        adaptadorLista = new AdaptadorLista(elements, this);
        recyclerView = findViewById(R.id.listaRecycler);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adaptadorLista);



    }
}