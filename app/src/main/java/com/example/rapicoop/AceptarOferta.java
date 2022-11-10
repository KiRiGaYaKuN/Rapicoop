package com.example.rapicoop;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
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

public class AceptarOferta extends AppCompatActivity {

    public static final String[] EXTRA_MESSAGE = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aceptar_oferta);

        RapicoopDatabase rapidb = new RapicoopDatabase(AceptarOferta.this);
        InsertarUsuario iu = new InsertarUsuario(AceptarOferta.this);
        Intent intent=getIntent();
        String[] dato = intent.getStringArrayExtra(String.valueOf(EXTRA_MESSAGE));
        String ubicacion = dato[0];
        String idcarro = dato[1];

        Carrito carro= iu.consultacarro(Integer.parseInt(idcarro));
        Oferta oferta = iu.consultanombre(carro.getProducto());

        TextView name = (TextView) findViewById(R.id.ubi);
        TextView cantidad = (TextView) findViewById(R.id.cant);
        TextView ubi = (TextView) findViewById(R.id.description);
        TextView fijo = (TextView) findViewById(R.id.preciofijo);
        TextView total = (TextView) findViewById(R.id.preciototal);
        ImageView img = (ImageView) findViewById(R.id.picture);

        Button aceptar = (Button) findViewById(R.id.eliminarbtn);

        name.setText(carro.getProducto());
        ubi.setText("Dirección de entrega: " + ubicacion);
        cantidad.setText("Cantidad: " + carro.getCantidad());
        fijo.setText("Precio Uni: " + oferta.getPrecio());
        total.setText("Precio Total: " + (oferta.getPrecio()*carro.getCantidad()));

        Bitmap bim = BitmapFactory.decodeByteArray(oferta.getImagen(),0,oferta.getImagen().length);
        img.setImageBitmap(bim);

        aceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent init = new Intent(AceptarOferta.this, Consumidor.class);

                AlertDialog.Builder alerta = new AlertDialog.Builder(AceptarOferta.this);
                alerta.setMessage("¿Esta seguro de esta compra?")
                        .setCancelable(false)
                        .setPositiveButton("Si", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                                long id = iu.aceptarOferta(carro.getProducto(),carro.getConsumidor(),ubicacion,carro.getCantidad(),(oferta.getPrecio()*carro.getCantidad()),"espera");

                                if(id > 0){
                                    Toast.makeText(AceptarOferta.this, "Oferta aceptada", Toast.LENGTH_SHORT).show();
                                    if (iu.eliminarcarrito (carro.getConsumidor(), carro.getProducto())){Toast.makeText(AceptarOferta.this, "Carrito eliminado", Toast.LENGTH_SHORT).show();}
                                    init.putExtra(Consumidor.EXTRA_MESSAGE, carro.getConsumidor());
                                    startActivity(init);
                                    finish();
                                }else {
                                    Toast.makeText(AceptarOferta.this, "Oferta rechazada", Toast.LENGTH_LONG).show();
                                }

                                dialogInterface.cancel();
                                finish();
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.cancel();
                            }
                        });
                AlertDialog titulo = alerta.create();
                titulo.setTitle("Aceptar compra");
                titulo.show();
            }
        });

    }
}