package com.example.rapicoop;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Color;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

public class Ofertasconsumidor extends AppCompatActivity {

    List<Listadeelementos> elements;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ofertasconsumidor);

        init();
    }

    public void  init() {
        elements = new ArrayList<>();
        elements.add(new Listadeelementos("#FFB562","Papa Jhons", "Todos los dias 10am-10pm", "Abierto"));
        elements.add(new Listadeelementos("#FFB562","Presto", "Todos los dias 10am-10pm", "Cerrado"));
        elements.add(new Listadeelementos("#FFB562","Subway", "Todos los dias 10am-10pm", "Abierto"));
        elements.add(new Listadeelementos("#FFB562","Frisby", "Todos los dias 10am-10pm", "Abierto"));

        AdaptadorLista adaptadorLista = new AdaptadorLista(elements, this);
        RecyclerView recyclerView = findViewById(R.id.listaRecycler);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adaptadorLista);
    }
}