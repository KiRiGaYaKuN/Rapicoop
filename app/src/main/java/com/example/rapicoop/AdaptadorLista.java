package com.example.rapicoop;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class AdaptadorLista extends RecyclerView.Adapter<AdaptadorLista.ViewHolder>{
    private List<Listadeelementos> mData;
    private LayoutInflater mInflater;
    private Context context;


    public AdaptadorLista(List<Listadeelementos> itemList, Context context){
        this.mInflater = LayoutInflater.from(context);
        this.context = context;
        this.mData = itemList;

    }

    @Override
    public int getItemCount() {return mData.size(); }

    @Override
    public AdaptadorLista.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.lista_elementos, null);
        return new AdaptadorLista.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final AdaptadorLista.ViewHolder holder, final int position){
      holder.bindData(mData.get(position));
    }

    public void setItems(List<Listadeelementos> items) {mData = items; }

    class ViewHolder extends RecyclerView.ViewHolder {
        ImageView Icono;
        TextView nombrerestaurante, horario, estadorestaurante;

        ViewHolder(View itemView){
            super(itemView);
            Icono = itemView.findViewById(R.id.Icono);
            nombrerestaurante = itemView.findViewById(R.id.nombrerestaurante);
            horario = itemView.findViewById(R.id.textohorario);
            estadorestaurante = itemView.findViewById(R.id.estadorestaurante);

        }
    void bindData(final Listadeelementos item) {
            Icono.setImageBitmap(item.getImg());
            nombrerestaurante.setText(item.getNombrerestaurante());
            horario.setText(item.getHorario());
            estadorestaurante.setText(item.getEstado());
    }

    }
    }



