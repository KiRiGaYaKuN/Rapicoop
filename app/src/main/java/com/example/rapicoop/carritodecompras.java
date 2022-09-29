package com.example.rapicoop;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;

public class carritodecompras extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carritodecompras);


    }
    public void init(){

        ArrayList<Object> elements = new ArrayList<>();
        elements.add(new Listaproducto("Icon", "Pizza Familiar Peppe", "25.000", "Tampaño increible"));
        AdaptadorProducto adaptadorProducto = new AdaptadorProducto(elements, this);
        RecyclerView recyclerView = findViewById(R.id.listaRecycler);
        recyclerView.setHasFixedSize(true);




    }

}