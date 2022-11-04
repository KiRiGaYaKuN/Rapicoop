package com.example.rapicoop;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.example.rapicoop.db.InsertarUsuario;
import com.example.rapicoop.db.RapicoopDatabase;

import java.util.List;

public class AdaptadorEliminar extends RecyclerView.Adapter<AdaptadorEliminar.ViewHolder> implements View.OnClickListener{

    private View.OnClickListener listener;
    private List<Listadeelementos> mData;
    private LayoutInflater mInflater;
    private Context context;
    private String user;

    public AdaptadorEliminar(List<Listadeelementos> itemList, Context context, String user){
        this.mInflater = LayoutInflater.from(context);
        this.context = context;
        this.mData = itemList;
        this.user = user;
    }

    @Override
    public int getItemCount() {return mData.size(); }

    @Override
    public AdaptadorEliminar.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.lista_eliminaroferta, null);
        view.setOnClickListener(this);
        return new AdaptadorEliminar.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final AdaptadorEliminar.ViewHolder holder, final int position){
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
        ImageButton estadorestaurante;

        ViewHolder(View itemView){
            super(itemView);
            Icono = itemView.findViewById(R.id.logo);
            nombrerestaurante = itemView.findViewById(R.id.nombreubi);
            horario = itemView.findViewById(R.id.precio);
            estadorestaurante = itemView.findViewById(R.id.botoneliminar);

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

                    AlertDialog.Builder alerta = new AlertDialog.Builder(context);
                    alerta.setMessage("¿Esta seguro de eliminar el producto?")
                            .setCancelable(false)
                            .setPositiveButton("Si", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    boolean id = iu.eliminarOferta(item.getVendedor(),item.getNombrerestaurante());
                                    if(id){
                                        Toast.makeText(context, "Se elimino al oferta", Toast.LENGTH_LONG).show();
                                        Intent intent = new Intent(context, eliminarofertav.class);
                                        intent.putExtra(carritodecompras.EXTRA_MESSAGE, item.getVendedor());
                                        context.startActivity(intent);
                                    }else {
                                        Toast.makeText(context, "No se elimino", Toast.LENGTH_LONG).show();
                                    }
                                    dialogInterface.cancel();
                                }
                            })
                            .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    dialogInterface.cancel();
                                }
                            });
                    AlertDialog titulo = alerta.create();
                    titulo.setTitle("Eliminar");
                    titulo.show();

                }


            });

        }

    }

}
