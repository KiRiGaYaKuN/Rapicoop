package com.example.rapicoop;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

public class carritodecompras extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carritodecompras);

        init();

    }
    public void init(){

        List<Listaproducto> elements = new ArrayList<>();
        elements.add(new Listaproducto("Icon", "Pizza Familiar Peppe", "25.000", "Tampaño increible"));
        elements.add(new Listaproducto("Icon", "Pizza Familiar Peppe", "25.000", "Tampaño increible"));
        AdaptadorProducto adaptadorProducto = new AdaptadorProducto(elements, this);
        RecyclerView recyclerView = findViewById(R.id.listadeproductos);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adaptadorProducto);


    }

}