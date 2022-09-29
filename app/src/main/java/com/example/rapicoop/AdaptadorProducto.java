package com.example.rapicoop;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class AdaptadorProducto extends RecyclerView.Adapter<AdaptadorProducto.ViewHolder> {
    private List<Listaproducto> mData;
    private LayoutInflater mInflater;
    private Context context;


    public AdaptadorProducto(ArrayList<Object> itemList, Context context){
        this.mInflater = LayoutInflater.from(context);
        this.context = context;
        //this.mData = itemList;

    }

    @Override
    public int getItemCount() {return mData.size(); }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_producto, null);
        return new AdaptadorProducto.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position){
        holder.bindData(mData.get(position));
    }

    public void setItems(List<Listaproducto> items) {mData = items; }

    class ViewHolder extends RecyclerView.ViewHolder {
        ImageView Icono;
        TextView nproducto, precio, descripcion;

        ViewHolder(View itemView){
            super(itemView);
            Icono = itemView.findViewById(R.id.Icono);
            nproducto = itemView.findViewById(R.id.item_nombre);
            precio = itemView.findViewById(R.id.item_precio);
            descripcion = itemView.findViewById(R.id.item_descripcion);

        }
        void bindData(final Listaproducto item) {
            Icono.setImageBitmap(item.getImg());
            nproducto.setText(item.getnproducto());
            precio.setText(item.getprecio());
            descripcion.setText(item.getdescripcion());
        }

    }
}

