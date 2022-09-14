package com.example.rapicoop;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.rapicoop.db.RapicoopDatabase;

public class LogIn extends AppCompatActivity {

    Button sigin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RapicoopDatabase rapidb = new RapicoopDatabase(LogIn.this);
        SQLiteDatabase db = rapidb.getWritableDatabase();

        sigin =(Button)findViewById(R.id.sigin);

        sigin.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {

                Intent i = new Intent(LogIn.this, Ofertarcomida.class);
                startActivity(i);
            }
        });

    }
}