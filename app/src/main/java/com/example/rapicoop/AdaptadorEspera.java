package com.example.rapicoop;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class AdaptadorEspera extends RecyclerView.Adapter<AdaptadorEspera.ViewHolder> implements View.OnClickListener {

    private View.OnClickListener listener;
    private List<Listadeelementos> mData;
    private LayoutInflater mInflater;
    private Context context;
    private String user;


    public AdaptadorEspera(List<Listadeelementos> itemList, Context context, String user){
        this.mInflater = LayoutInflater.from(context);
        this.context = context;
        this.mData = itemList;
        this.user = user;
    }

    @Override
    public int getItemCount() {return mData.size(); }

    @Override
    public AdaptadorEspera.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.activity_itempedidosespera, null);
        view.setOnClickListener(this);
        return new AdaptadorEspera.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final AdaptadorEspera.ViewHolder holder, final int position){
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
            nombrerestaurante = itemView.findViewById(R.id.nombreubi);
            horario = itemView.findViewById(R.id.textohorario);
            estadorestaurante = itemView.findViewById(R.id.estadorestaurante);

        }
        void bindData(final Listadeelementos item) {
                Icono.setImageBitmap(item.getImg());
                nombrerestaurante.setText(item.getNombrerestaurante());
                horario.setText(item.getHorario());
        }
    }
}



