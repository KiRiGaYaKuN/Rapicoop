package com.example.rapicoop;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.rapicoop.db.InsertarUsuario;
import com.example.rapicoop.db.RapicoopDatabase;

import java.util.List;

public class AdaptadorLista extends RecyclerView.Adapter<AdaptadorLista.ViewHolder> implements View.OnClickListener {

    private View.OnClickListener listener;
    private List<Listadeelementos> mData;
    private LayoutInflater mInflater;
    private Context context;
    private String user;


    public AdaptadorLista(List<Listadeelementos> itemList, Context context, String user){
        this.mInflater = LayoutInflater.from(context);
        this.context = context;
        this.mData = itemList;
        this.user = user;
    }

    @Override
    public int getItemCount() {return mData.size(); }

    @Override
    public AdaptadorLista.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.lista_elementos, null);
        view.setOnClickListener(this);
        return new AdaptadorLista.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final AdaptadorLista.ViewHolder holder, final int position){
      holder.bindData(mData.get(position));
    }

    public void setItems(List<Listadeelementos> items) {mData = items; }

    public void setOnClickListener(View.OnClickListener listener){
        this.listener = listener;
    }

    @Override
    public void onClick(View view) {

        if(listener != null){
            listener.onClick(view);
        }

    }



    class ViewHolder extends RecyclerView.ViewHolder {
        ImageView Icono;
        TextView nombrerestaurante, horario;
        Button estadorestaurante;

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

        estadorestaurante.setOnClickListener(new View.OnClickListener(){



            @Override
            public void onClick(View view) {

                RapicoopDatabase rapidb = new RapicoopDatabase(context);
                InsertarUsuario iu = new InsertarUsuario(context);
                if (iu.verificacarro(user,item.getVendedor(),item.getNombrerestaurante())){

                    if (iu.aumentacant(user,item.getVendedor(),item.getNombrerestaurante())){
                        Toast.makeText(context, "Aumenta cantidad", Toast.LENGTH_LONG).show();
                    }else {
                        Toast.makeText(context, "No aumenta", Toast.LENGTH_LONG).show();
                    }

                }else{
                    long id = iu.agregaCarrito(user,item.getVendedor(),item.getNombrerestaurante(),1);

                    if(id > 0){
                        Toast.makeText(context, "Agregado", Toast.LENGTH_LONG).show();
                    }else {
                        Toast.makeText(context, "No fue agregada", Toast.LENGTH_LONG).show();
                    }
                }

            }
        });

    }

    }
    }



