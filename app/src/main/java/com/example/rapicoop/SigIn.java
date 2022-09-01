package com.example.rapicoop;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.example.rapicoop.db.InsertarUsuario;

public class SigIn extends AppCompatActivity {

    Button crear;
    EditText usr, fname, sname, correo, telefono, psw;
    RadioButton vende, compra;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sig_in);

        crear = (Button) findViewById(R.id.sigin);

        usr = (EditText) findViewById(R.id.user);
        fname = (EditText) findViewById(R.id.name);
        sname = (EditText) findViewById(R.id.apllido);
        correo = (EditText) findViewById(R.id.email);
        telefono = (EditText) findViewById(R.id.cel);
        psw = (EditText) findViewById(R.id.psw);

        vende = (RadioButton) findViewById(R.id.vende);
        compra = (RadioButton) findViewById(R.id.compra);


        crear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String usrtxt = usr.getText().toString();
                String fnamanetxt = fname.getText().toString();
                String snametxt = sname.getText().toString();
                String correotxt = correo.getText().toString();
                String telefonotxt = telefono.getText().toString();
                String pswtxt = psw.getText().toString();
                String rol = "ninguno";

                if(vende.isChecked() == true){
                    rol = "Vendedor";
                }else{
                    if(compra.isChecked() == true){
                        rol = "Consumidor";
                    }
                }

                String finalRol = rol;
                InsertarUsuario crearusuario = new InsertarUsuario(SigIn.this);
                long id = crearusuario.nuevoUsuario(usrtxt,fnamanetxt,snametxt,correotxt,telefonotxt,pswtxt, finalRol);

                if(id > 0){
                    Toast.makeText(SigIn.this, "Registro guardado", Toast.LENGTH_LONG).show();
                    Intent i = new Intent(SigIn.this, LogIn.class);
                    startActivity(i);
                }else {
                    Toast.makeText(SigIn.this, "Registro no guardado", Toast.LENGTH_LONG).show();
                }
            }
        });


    }
}