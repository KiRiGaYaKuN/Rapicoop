package com.example.rapicoop;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

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

        TextView mensaje = (TextView) findViewById(R.id.mensajecarrito);
        Button boton = (Button) findViewById(R.id.iroferta);

        if (elements.isEmpty()){

            mensaje.setVisibility(View.VISIBLE);
            boton.setVisibility(View.VISIBLE);

        }

        boton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(carritodecompras.this, Ofertasconsumidor.class);
                i.putExtra(Ofertasconsumidor.EXTRA_MESSAGE, usuario);
                startActivity(i);
            }
        });

    }
    public void init(){

        elements = new ArrayList<>();
        InsertarUsuario iu = new InsertarUsuario(carritodecompras.this);
        for (Carrito x: listacarrito) {
            String palo = "" + x.getCantidad();
            Oferta oferta = iu.consultanombre(x.getProducto());
            Bitmap bim = BitmapFactory.decodeByteArray(oferta.getImagen(),0,oferta.getImagen().length);
            elements.add(new Listaproducto(bim, oferta.getNombre(), "$" + oferta.getPrecio(), oferta.getDescripcion(),"" + x.getCantidad(),x.getConsumidor(),x.getVendedor()));
        }

        AdaptadorProducto adaptadorProducto = new AdaptadorProducto(elements, this);

        RecyclerView recyclerView = findViewById(R.id.listadeproductos);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adaptadorProducto);


    }

    @Override
    protected void onStop() {
        super.onStop();

        finish();
    }

}