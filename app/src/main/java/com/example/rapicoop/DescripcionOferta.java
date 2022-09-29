package com.example.rapicoop;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.rapicoop.db.InsertarUsuario;
import com.example.rapicoop.db.RapicoopDatabase;

public class DescripcionOferta extends AppCompatActivity {

    public static final String EXTRA_MESSAGE = "message";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_descripcion_oferta);

        RapicoopDatabase rapidb = new RapicoopDatabase(DescripcionOferta.this);
        InsertarUsuario iu = new InsertarUsuario(DescripcionOferta.this);
        Intent intent=getIntent();
        String nombre = intent.getStringExtra(EXTRA_MESSAGE);

        Oferta oferta = iu.consultanombre(nombre);

        TextView name = (TextView) findViewById(R.id.name);
        TextView precio = (TextView) findViewById(R.id.precio);
        TextView ubi = (TextView) findViewById(R.id.gps);
        TextView descripcion = (TextView) findViewById(R.id.description);
        ImageView img = (ImageView) findViewById(R.id.picture);

        name.setText(oferta.getNombre());
        precio.setText("" + oferta.getPrecio());
        ubi.setText(oferta.getUbicacion());
        descripcion.setText(oferta.getDescripcion());

        Bitmap bim = BitmapFactory.decodeByteArray(oferta.getImagen(),0,oferta.getImagen().length);
        img.setImageBitmap(bim);

    }
}