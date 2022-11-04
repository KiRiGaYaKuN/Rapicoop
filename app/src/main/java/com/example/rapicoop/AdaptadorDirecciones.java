package com.example.rapicoop;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.rapicoop.db.InsertarUsuario;
import com.example.rapicoop.db.RapicoopDatabase;

import java.util.List;

public class AdaptadorDirecciones extends RecyclerView.Adapter<AdaptadorDirecciones.ViewHolder> implements View.OnClickListener{

    private View.OnClickListener listener;
    private List<listadirecciones> mData;
    private LayoutInflater mInflater;
    private Context context;
    private String user;


    public AdaptadorDirecciones(List<listadirecciones> itemList, Context context, String user){
        this.mInflater = LayoutInflater.from(context);
        this.context = context;
        this.mData = itemList;
        this.user = user;
    }

    @Override
    public int getItemCount() {return mData.size(); }

    @Override
    public AdaptadorDirecciones.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.activity_direccion, null);
        view.setOnClickListener(this);
        return new AdaptadorDirecciones.ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(final AdaptadorDirecciones.ViewHolder holder, final int position){
        holder.bindData(mData.get(position));
    }

    public void setItems(List<listadirecciones> items) {mData = items; }

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
        TextView nombre, direccion;

        ViewHolder(View itemView){
            super(itemView);

            nombre = itemView.findViewById(R.id.nombreubi);
            direccion = itemView.findViewById(R.id.descripciondireccion);

        }
        void bindData(final listadirecciones item) {

            nombre.setText(item.getNombre());
            direccion.setText(item.getDireccion());

        }

    }
}
