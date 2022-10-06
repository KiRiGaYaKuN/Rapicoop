package com.example.rapicoop;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import com.example.rapicoop.db.InsertarUsuario;
import com.example.rapicoop.db.RapicoopDatabase;

import java.util.ArrayList;
import java.util.List;

public class carritodecompras extends AppCompatActivity {

    public static final String EXTRA_MESSAGE="mesagge";
    List<Listaproducto> elements;
    ArrayList<Carrito> listacarrito;
    RecyclerView recyclerView;
    AdaptadorProducto adaptadorProducto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carritodecompras);

        RapicoopDatabase rapidb = new RapicoopDatabase(carritodecompras.this);
        InsertarUsuario iu = new InsertarUsuario(carritodecompras.this);
        Intent intent=getIntent();
        String usuario = intent.getStringExtra(EXTRA_MESSAGE);

        listacarrito = new ArrayList<Carrito>();
        listacarrito = iu.consultacarro(usuario);


        init();

    }
    public void init(){

        elements = new ArrayList<>();
        InsertarUsuario iu = new InsertarUsuario(carritodecompras.this);
        for (Carrito x: listacarrito) {
            String palo = "" + x.getCantidad();
            Oferta oferta = iu.consultanombre(x.getProducto());
            Bitmap bim = BitmapFactory.decodeByteArray(oferta.getImagen(),0,oferta.getImagen().length);
            elements.add(new Listaproducto(bim, oferta.getNombre(), "$" + oferta.getPrecio(), oferta.getDescripcion(),"" + x.getCantidad()));
        }

        AdaptadorProducto adaptadorProducto = new AdaptadorProducto(elements, this);
        RecyclerView recyclerView = findViewById(R.id.listadeproductos);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adaptadorProducto);


    }

}