package com.example.rapicoop;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.example.rapicoop.db.InsertarUsuario;
import com.example.rapicoop.db.RapicoopDatabase;

import java.util.ArrayList;
import java.util.List;

public class AdaptadorProducto extends RecyclerView.Adapter<AdaptadorProducto.ViewHolder> {
    private List<Listaproducto> mData;
    private LayoutInflater mInflater;
    private Context context;


    public AdaptadorProducto(List<Listaproducto> itemList, Context context){
        this.mInflater = LayoutInflater.from(context);
        this.context = context;
        this.mData = itemList;

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
        TextView nproducto, precio, descripcion, cantidad;
        Button mas, menos;

        ViewHolder(View itemView){
            super(itemView);
          Icono = itemView.findViewById(R.id.icono);
            nproducto = itemView.findViewById(R.id.item_nombre);
            precio = itemView.findViewById(R.id.item_precio);
            descripcion = itemView.findViewById(R.id.item_descripcion);
            cantidad = itemView.findViewById(R.id.contadorproducto);
            mas = (Button) itemView.findViewById(R.id.Buttonplus);
            menos = (Button) itemView.findViewById(R.id.Buttonminus);

        }
        void bindData(final Listaproducto item) {
            Icono.setImageBitmap(item.getImg());
            nproducto.setText(item.getnproducto());
            precio.setText(item.getprecio());
            descripcion.setText(item.getdescripcion());
            cantidad.setText(item.getCantidad());

            mas.setOnClickListener(new View.OnClickListener(){

                @Override
                public void onClick(View view) {

                    RapicoopDatabase rapidb = new RapicoopDatabase(context);
                    InsertarUsuario iu = new InsertarUsuario(context);
                    if (iu.verificacarro(item.getConsumidor(),item.getVendedor(),item.getnproducto())){

                        if (iu.aumentacant(item.getConsumidor(),item.getVendedor(),item.getnproducto())){
                            int sum = Integer.parseInt(cantidad.getText().toString())+1;
                            cantidad.setText("" + sum);
                        }else {
                            Toast.makeText(context, "No aumenta", Toast.LENGTH_LONG).show();
                        }

                    }
                }
            });

            menos.setOnClickListener(new View.OnClickListener(){

                @Override
                public void onClick(View view) {

                    RapicoopDatabase rapidb = new RapicoopDatabase(context);
                    InsertarUsuario iu = new InsertarUsuario(context);
                    if (iu.verificacarro(item.getConsumidor(),item.getVendedor(),item.getnproducto())){

                        if (iu.disminuircarrito(item.getConsumidor(),item.getVendedor(),item.getnproducto())){
                            int men = Integer.parseInt(cantidad.getText().toString())-1;
                            if (men <= 0){
                                Intent i = new Intent(context, carritodecompras.class);
                                i.putExtra(carritodecompras.EXTRA_MESSAGE, item.getConsumidor());
                                context.startActivity(i);

                            }
                            cantidad.setText("" + men);
                        }else {
                            Toast.makeText(context, "No aumenta", Toast.LENGTH_LONG).show();
                        }

                    }
                }
            });
        }

    }
}

