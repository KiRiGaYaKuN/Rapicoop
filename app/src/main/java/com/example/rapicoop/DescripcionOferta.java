package com.example.rapicoop;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rapicoop.db.InsertarUsuario;
import com.example.rapicoop.db.RapicoopDatabase;

public class DescripcionOferta extends AppCompatActivity {

    public static final String[] EXTRA_MESSAGE = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_descripcion_oferta);

        RapicoopDatabase rapidb = new RapicoopDatabase(DescripcionOferta.this);
        InsertarUsuario iu = new InsertarUsuario(DescripcionOferta.this);
        Intent intent=getIntent();
        String[] dato = intent.getStringArrayExtra(String.valueOf(EXTRA_MESSAGE));
        String nombre = dato[0];
        String cliente = dato[1];

        Oferta oferta = iu.consultanombre(nombre);

        TextView name = (TextView) findViewById(R.id.name);
        TextView precio = (TextView) findViewById(R.id.precio);
        TextView ubi = (TextView) findViewById(R.id.gps);
        TextView descripcion = (TextView) findViewById(R.id.description);
        ImageView img = (ImageView) findViewById(R.id.picture);

        Button agregar = (Button) findViewById(R.id.agregar);

        name.setText(oferta.getNombre());
        precio.setText("" + oferta.getPrecio());
        ubi.setText(oferta.getUbicacion());
        descripcion.setText(oferta.getDescripcion());



        Bitmap bim = BitmapFactory.decodeByteArray(oferta.getImagen(),0,oferta.getImagen().length);
        img.setImageBitmap(bim);

        agregar.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {


                if (iu.verificacarro(cliente,oferta.getUsuario(),oferta.getNombre())){

                    if (iu.aumentacant(cliente,oferta.getUsuario(),nombre)){
                        Toast.makeText(DescripcionOferta.this, "Aumenta cantidad", Toast.LENGTH_LONG).show();
                    }else {
                        Toast.makeText(DescripcionOferta.this, "No aumenta", Toast.LENGTH_LONG).show();
                    }

                }else{
                    long id = iu.agregaCarrito(cliente,oferta.getUsuario(),nombre,1);

                    if(id > 0){
                        Toast.makeText(DescripcionOferta.this, "Agregado", Toast.LENGTH_LONG).show();
                    }else {
                        Toast.makeText(DescripcionOferta.this, "No fue agregada", Toast.LENGTH_LONG).show();
                    }
                }

            }
        });
    }
}