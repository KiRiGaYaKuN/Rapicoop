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

import java.util.Calendar;

public class descripcionpedido extends AppCompatActivity {

    public static final String[] EXTRA_MESSAGE = null;

    TextView nombre, dirres, dircasa, cantida, precio;
    ImageView img;
    Button aceptar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_descripcionpedido);

        RapicoopDatabase rapidb = new RapicoopDatabase(descripcionpedido.this);
        InsertarUsuario iu = new InsertarUsuario(descripcionpedido.this);
        Intent intent=getIntent();
        String[] dato = intent.getStringArrayExtra(String.valueOf(EXTRA_MESSAGE));
        int id = Integer.parseInt(dato[0]);
        String usuario = dato[1];

        nombre = (TextView) findViewById(R.id.ubi);
        dirres = (TextView) findViewById(R.id.dirres);
        dircasa = (TextView) findViewById(R.id.dircasa);
        cantida = (TextView) findViewById(R.id.cant);
        precio = (TextView) findViewById(R.id.precio);

        img = (ImageView) findViewById(R.id.picture);

        aceptar = (Button) findViewById(R.id.eliminarbtn);

        OfertaAceptada ofertaA = iu.consultaesperaid(id);

        Oferta oferta = iu.consultanombre(ofertaA.getOferta());
        Bitmap bim = BitmapFactory.decodeByteArray(oferta.getImagen(),0,oferta.getImagen().length);

        nombre.setText(oferta.getNombre());
        dirres.setText(oferta.getUbicacion());
        dircasa.setText(ofertaA.getUbicacion());
        cantida.setText("Cantidad: "+ofertaA.getCantidad());
        precio.setText("$"+ofertaA.getPrecio());

        img.setImageBitmap(bim);

        long ahora = System.currentTimeMillis();
        Calendar calendario = Calendar.getInstance();
        calendario.setTimeInMillis(ahora);
        int hora = calendario.get(Calendar.HOUR_OF_DAY);
        int minuto = calendario.get(Calendar.MINUTE);
        int seg = calendario.get(Calendar.SECOND);
        String time = hora+":"+minuto+":"+seg;

        aceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (iu.aceptarpedido(usuario,id,"proceso",time)){
                    Toast.makeText(descripcionpedido.this, "Pedido aceptado", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(descripcionpedido.this,NotificarService.class);
                    intent.putExtra(NotificarService.EXTRA_MESSAGE,"toma");
                    startService(intent);
                    Intent i = new Intent(descripcionpedido.this, Ruta.class);
                    i.putExtra(Ruta.EXTRA_MESSAGE, usuario);
                    startActivity(i);
                }else{
                    Toast.makeText(descripcionpedido.this, "Pedido no aceptado", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    @Override
    protected void onStop() {
        super.onStop();
        finish();
    }
}