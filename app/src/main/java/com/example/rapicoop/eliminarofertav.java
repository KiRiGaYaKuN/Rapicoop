package com.example.rapicoop;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.rapicoop.db.InsertarUsuario;
import com.example.rapicoop.db.RapicoopDatabase;

import java.util.ArrayList;
import java.util.List;

public class eliminarofertav extends AppCompatActivity {





    public static final String EXTRA_MESSAGE="mesagge";
    List<Listadeelementos> elements;
    ArrayList<Oferta> listaofertas;
    RecyclerView recyclerView;
    AdaptadorEliminar adaptadorLista;

    TextView usu;
    TextView tipo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eliminarofertav);

        RapicoopDatabase rapidb = new RapicoopDatabase(eliminarofertav.this);
        InsertarUsuario iu = new InsertarUsuario(eliminarofertav.this);
        Intent intent=getIntent();
        String usuario = intent.getStringExtra(EXTRA_MESSAGE);
        String rol = iu.devolver(usuario);
        listaofertas = new ArrayList<Oferta>();

        usu = (TextView) findViewById(R.id.usuario);
        tipo = (TextView) findViewById(R.id.tipo);
        tipo.setText(rol);
        usu.setText(usuario);

        listaofertas = iu.consultavende(usuario);
        init();

    }

    public void  init() {
        elements = new ArrayList<>();


        for (Oferta x: listaofertas) {
            String palo = "" + x.getPrecio();
            Bitmap bim = BitmapFactory.decodeByteArray(x.getImagen(),0,x.getImagen().length);

            elements.add(new Listadeelementos(bim, x.getNombre(), x.getUbicacion(), palo, x.getUsuario()));
        }

        adaptadorLista = new AdaptadorEliminar(elements, this,usu.getText().toString());
        recyclerView = findViewById(R.id.listaRecycler);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adaptadorLista);

    }

    @Override
    protected void onStop() {
        super.onStop();
        finish();
    }
}