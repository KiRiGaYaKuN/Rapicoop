package com.example.rapicoop;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.rapicoop.db.InsertarUsuario;
import com.example.rapicoop.db.RapicoopDatabase;

public class LogIn extends AppCompatActivity {

    Button sigin;
    Button ingresar;

    EditText usuario;
    EditText pasword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RapicoopDatabase rapidb = new RapicoopDatabase(LogIn.this);
        SQLiteDatabase db = rapidb.getWritableDatabase();

        sigin =(Button)findViewById(R.id.sigin);
        ingresar = (Button) findViewById(R.id.ingresar);

        pasword = (EditText) findViewById(R.id.password);
        usuario = (EditText) findViewById(R.id.usuario);

        sigin.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {

                Intent i = new Intent(LogIn.this, LogIn.class);
                startActivity(i);
            }
        });

        ingresar.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {

                InsertarUsuario iu = new InsertarUsuario(LogIn.this);

                if (iu.verificar(pasword.getText().toString(), usuario.getText().toString())) {

                    String rol = "vendedor";
                    if(iu.devolver(pasword.getText().toString(), usuario.getText().toString()).equals("Vendedor")) {
                        Intent i = new Intent(LogIn.this, Vendedor.class);
                        startActivity(i);
                    }
                    if(iu.devolver(pasword.getText().toString(), usuario.getText().toString()).equals("Consumidor")) {
                        Intent i = new Intent(LogIn.this, Consumidor.class);
                        startActivity(i);
                    }

                }else{
                    Toast.makeText(LogIn.this, "Credenciales invalidas", Toast.LENGTH_LONG).show();
                }

            }
        });

    }
}