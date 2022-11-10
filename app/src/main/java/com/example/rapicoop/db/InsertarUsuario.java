package com.example.rapicoop.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.example.rapicoop.Carrito;
import com.example.rapicoop.DescripcionOferta;
import com.example.rapicoop.Oferta;
import com.example.rapicoop.listadirecciones;

import java.util.ArrayList;

public class InsertarUsuario extends RapicoopDatabase{

    Context context;
    public InsertarUsuario(@Nullable Context context) {
        super(context);
        this.context = context;
    }

    //usuario
    public long nuevoUsuario (String usuario, String nombres, String apellidos, String correo, String telefono, String contrasena, String rol){

        long id = 0;

        try {

            RapicoopDatabase rapidb = new RapicoopDatabase(context);
            SQLiteDatabase db = rapidb.getWritableDatabase();

            ContentValues values = new ContentValues();
            values.put("usuario", usuario);
            values.put("nombres", nombres);
            values.put("apellidos", apellidos);
            values.put("correo", correo);
            values.put("telefono", telefono);
            values.put("password", contrasena);
            values.put("rol", rol);

            id = db.insert(TABLE_NAME, null, values);
        }catch (Exception ex){
            ex.toString();
        }

        return id;
    }

    public String devolver(String user){

        RapicoopDatabase rapidb = new RapicoopDatabase(context);
        SQLiteDatabase db = rapidb.getWritableDatabase();
        Cursor cursorUsuarios = null;
        String rol = "nada";

        cursorUsuarios = db.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE usuario LIKE '" + user + "'",null);

        cursorUsuarios.moveToFirst();
        rol = cursorUsuarios.getString(7);

        return rol;
    }

    public Boolean verificar(String psw, String user){

        RapicoopDatabase rapidb = new RapicoopDatabase(context);
        SQLiteDatabase db = rapidb.getWritableDatabase();
        Cursor cursorUsuarios = null;
        String rol = null;

        cursorUsuarios = db.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE usuario LIKE '" + user + "' AND password LIKE '" + psw + "'",null);


        return cursorUsuarios.moveToFirst();
    }

    //Ofertas

    public long nuevaOferta (String usuario, String nombre, String categoria, int precio, String ubicacion, String descripcion, byte[] imagen){

        long id = 0;

        try {

            RapicoopDatabase rapidb = new RapicoopDatabase(context);
            SQLiteDatabase db = rapidb.getWritableDatabase();

            ContentValues values = new ContentValues();
            values.put("usuario", usuario);
            values.put("nombre", nombre);
            values.put("categoria", categoria);
            values.put("precio", precio);
            values.put("ubicacion", ubicacion);
            values.put("descripcion", descripcion);
            values.put("imagen", imagen);

            id = db.insert(TABLE_OFERTA, null, values);
        }catch (Exception ex){
            ex.toString();
        }

        return id;
    }

    public boolean eliminarOferta (String vende, String name){

        RapicoopDatabase rapidb = new RapicoopDatabase(context);
        SQLiteDatabase db = rapidb.getWritableDatabase();
        boolean correct = false;
        Cursor cursorUsuarios = null;

        try {

            cursorUsuarios = db.rawQuery("SELECT * FROM " + TABLE_OFERTA + " WHERE usuario LIKE '" + vende + "' AND nombre LIKE '" + name + "'",null);

            int ident = 0;

            if (cursorUsuarios.moveToFirst()){
                do {
                    ident = cursorUsuarios.getInt(0);

                        db.execSQL("DELETE FROM " + TABLE_OFERTA + " WHERE id = '" + ident + "'");
                        correct = true;

                }   while (cursorUsuarios.moveToNext());
            }

        }catch (Exception ex){
            ex.toString();
        }finally {
            db.close();
        }

        return correct;
    }


    //Consultas

    public ArrayList<Oferta> consultaconsume(){

        RapicoopDatabase rapidb = new RapicoopDatabase(context);
        SQLiteDatabase db = rapidb.getWritableDatabase();

        ArrayList<Oferta> listaofertas = new ArrayList<Oferta>();

        Oferta oferta = null;
        Cursor cursoroferta = null;

        cursoroferta = db.rawQuery("SELECT * FROM " + TABLE_OFERTA,null);

        if (cursoroferta.moveToFirst()){
            do {
                oferta = new Oferta();
                oferta.setUsuario(cursoroferta.getString(1));
                oferta.setNombre(cursoroferta.getString(2));
                oferta.setCategoria(cursoroferta.getString(3));
                oferta.setPrecio(cursoroferta.getInt(4));
                oferta.setUbicacion(cursoroferta.getString(5));
                oferta.setImagen(cursoroferta.getBlob(7));
                listaofertas.add(oferta);
            }   while (cursoroferta.moveToNext());
        }

        cursoroferta.close();

        return listaofertas;
    }

    public ArrayList<Oferta> consultavende(String usu){

        RapicoopDatabase rapidb = new RapicoopDatabase(context);
        SQLiteDatabase db = rapidb.getWritableDatabase();

        ArrayList<Oferta> listaofertas = new ArrayList<Oferta>();

        Oferta oferta = null;
        Cursor cursoroferta = null;

        cursoroferta = db.rawQuery("SELECT * FROM " + TABLE_OFERTA + " WHERE usuario LIKE '" + usu + "'",null);

        if (cursoroferta.moveToFirst()){
            do {
                oferta = new Oferta();
                oferta.setUsuario(cursoroferta.getString(1));
                oferta.setNombre(cursoroferta.getString(2));
                oferta.setPrecio(cursoroferta.getInt(4));
                oferta.setUbicacion(cursoroferta.getString(5));
                oferta.setImagen(cursoroferta.getBlob(7));
                listaofertas.add(oferta);
            }   while (cursoroferta.moveToNext());
        }

        cursoroferta.close();

        return listaofertas;
    }

    public Oferta consultanombre(String usu){

        RapicoopDatabase rapidb = new RapicoopDatabase(context);
        SQLiteDatabase db = rapidb.getWritableDatabase();

        Oferta oferta = null;
        Cursor cursoroferta = null;

        cursoroferta = db.rawQuery("SELECT * FROM " + TABLE_OFERTA + " WHERE nombre LIKE '" + usu + "'",null);

        if (cursoroferta.moveToFirst()){
            do {
                oferta = new Oferta();
                oferta.setUsuario(cursoroferta.getString(1));
                oferta.setNombre(cursoroferta.getString(2));
                oferta.setDescripcion(cursoroferta.getString(6));
                oferta.setPrecio(cursoroferta.getInt(4));
                oferta.setUbicacion(cursoroferta.getString(5));
                oferta.setImagen(cursoroferta.getBlob(7));
            }   while (cursoroferta.moveToNext());
        }

        cursoroferta.close();

        return oferta;
    }

    public ArrayList<Carrito> consultacarro(String usu){

        RapicoopDatabase rapidb = new RapicoopDatabase(context);
        SQLiteDatabase db = rapidb.getWritableDatabase();

        ArrayList<Carrito> listacarrito = new ArrayList<Carrito>();

        Carrito carro = null;
        Cursor cursoroferta = null;

        cursoroferta = db.rawQuery("SELECT * FROM " + TABLE_CARRITO + " WHERE cliente LIKE '" + usu + "'",null);

        if (cursoroferta.moveToFirst()){
            do {

                    carro = new Carrito();
                    carro.setVendedor(cursoroferta.getString(1));
                    carro.setConsumidor(cursoroferta.getString(2));
                    carro.setProducto(cursoroferta.getString(3));
                    carro.setCantidad(cursoroferta.getInt(4));
                if (verificacarroferta(carro.getProducto())) {
                    listacarrito.add(carro);
                }else{
                    db.execSQL("DELETE FROM " + TABLE_CARRITO + " WHERE id = '" + cursoroferta.getInt(0) + "'");
                }
            }   while (cursoroferta.moveToNext());
        }

        cursoroferta.close();

        return listacarrito;
    }

    public Carrito consultacarro(int id){

        RapicoopDatabase rapidb = new RapicoopDatabase(context);
        SQLiteDatabase db = rapidb.getWritableDatabase();

        Carrito carro = new Carrito();
        Cursor cursoroferta = null;

        cursoroferta = db.rawQuery("SELECT * FROM " + TABLE_CARRITO + " WHERE id = '" + id + "'",null);

        if (cursoroferta.moveToFirst()){
            do {
                carro.setVendedor(cursoroferta.getString(1));
                carro.setConsumidor(cursoroferta.getString(2));
                carro.setProducto(cursoroferta.getString(3));
                carro.setCantidad(cursoroferta.getInt(4));
            }   while (cursoroferta.moveToNext());
        }

        cursoroferta.close();

        return carro;
    }

    public ArrayList<listadirecciones> consultaubi(String usu){

        RapicoopDatabase rapidb = new RapicoopDatabase(context);
        SQLiteDatabase db = rapidb.getWritableDatabase();

        listadirecciones listadir = null;
        Cursor cursoroferta = null;

        ArrayList<listadirecciones> lista = new ArrayList<listadirecciones>();

        cursoroferta = db.rawQuery("SELECT * FROM " + TABLE_UBICACIONES + " WHERE usuario LIKE '" + usu + "'",null);

        if (cursoroferta.moveToFirst()){
            do {
                listadir = new listadirecciones();
                listadir.setNombre(cursoroferta.getString(2));
                listadir.setDireccion(cursoroferta.getString(3));
                lista.add(listadir);
            }   while (cursoroferta.moveToNext());
        }

        cursoroferta.close();

        return lista;
    }

    //carrito

    public long agregaCarrito (String usuario, String vendedor, String nombre, int cantidad){

        long id = 0;

        try {

            RapicoopDatabase rapidb = new RapicoopDatabase(context);
            SQLiteDatabase db = rapidb.getWritableDatabase();

            ContentValues values = new ContentValues();
            values.put("vendedor", vendedor);
            values.put("cliente", usuario);
            values.put("oferta", nombre);
            values.put("cantidad", cantidad);

            id = db.insert(TABLE_CARRITO, null, values);
        }catch (Exception ex){
            ex.toString();
        }

        return id;
    }

    public boolean aumentacant (String usu, String vende, String name){

        RapicoopDatabase rapidb = new RapicoopDatabase(context);
        SQLiteDatabase db = rapidb.getWritableDatabase();
        boolean correct = false;
        Cursor cursorUsuarios = null;

        try {

            cursorUsuarios = db.rawQuery("SELECT * FROM " + TABLE_CARRITO + " WHERE vendedor LIKE '" + vende + "' AND cliente LIKE '" + usu + "' AND oferta LIKE '" + name + "'",null);


            int ident = 0;
            int cant = 0;

            if (cursorUsuarios.moveToFirst()){
                do {
                    ident = cursorUsuarios.getInt(0);
                    cant = cursorUsuarios.getInt(4) + 1;
                }   while (cursorUsuarios.moveToNext());
            }


            try {
                db.execSQL("UPDATE " + TABLE_CARRITO + " SET cantidad = '" + cant + "' WHERE id = '" + ident + "'");

                correct = true;
            }catch (Exception ex){
                ex.toString();
                correct = false;
            }finally {
                db.close();
            }

        }catch (Exception ex){
            ex.toString();
        }

        return correct;
    }

    public boolean disminuircarrito (String usu, String vende, String name){

        RapicoopDatabase rapidb = new RapicoopDatabase(context);
        SQLiteDatabase db = rapidb.getWritableDatabase();
        boolean correct = false;
        Cursor cursorUsuarios = null;

        try {

            cursorUsuarios = db.rawQuery("SELECT * FROM " + TABLE_CARRITO + " WHERE vendedor LIKE '" + vende + "' AND cliente LIKE '" + usu + "' AND oferta LIKE '" + name + "'",null);


            int ident = 0;
            int cant = 0;

            if (cursorUsuarios.moveToFirst()){
                do {
                    ident = cursorUsuarios.getInt(0);
                    cant = cursorUsuarios.getInt(4);
                    if (cant > 1){
                        cant--;
                    }else {
                        db.execSQL("DELETE FROM " + TABLE_CARRITO + " WHERE id = '" + ident + "'");
                        return true;
                    }
                }   while (cursorUsuarios.moveToNext());
            }


            try {
                db.execSQL("UPDATE " + TABLE_CARRITO + " SET cantidad = '" + cant + "' WHERE id = '" + ident + "'");

                correct = true;
            }catch (Exception ex){
                ex.toString();
                correct = false;
            }finally {
                db.close();
            }

        }catch (Exception ex){
            ex.toString();
        }

        return correct;
    }

    public Boolean verificacarro(String usu, String vende, String name){

        RapicoopDatabase rapidb = new RapicoopDatabase(context);
        SQLiteDatabase db = rapidb.getWritableDatabase();
        Cursor cursorUsuarios = null;


        cursorUsuarios = db.rawQuery("SELECT * FROM " + TABLE_CARRITO + " WHERE vendedor LIKE '" + vende + "' AND cliente LIKE '" + usu + "' AND oferta LIKE '" + name + "'",null);


        return cursorUsuarios.moveToFirst();
    }

    public Boolean verificacarroferta(String name){

        RapicoopDatabase rapidb = new RapicoopDatabase(context);
        SQLiteDatabase db = rapidb.getWritableDatabase();
        Cursor cursorUsuarios = null;


        cursorUsuarios = db.rawQuery("SELECT * FROM " + TABLE_OFERTA + " WHERE nombre LIKE '" + name + "'",null);


        return cursorUsuarios.moveToFirst();
    }

    public int idproducto(String usu, String vende, String name){

        RapicoopDatabase rapidb = new RapicoopDatabase(context);
        SQLiteDatabase db = rapidb.getWritableDatabase();
        Cursor cursorUsuarios = null;


        cursorUsuarios = db.rawQuery("SELECT * FROM " + TABLE_CARRITO + " WHERE vendedor LIKE '" + vende + "' AND cliente LIKE '" + usu + "' AND oferta LIKE '" + name + "'",null);

        cursorUsuarios.moveToFirst();

        return cursorUsuarios.getInt(0);
    }

    public String idbusca(int id){

        RapicoopDatabase rapidb = new RapicoopDatabase(context);
        SQLiteDatabase db = rapidb.getWritableDatabase();
        Cursor cursorUsuarios = null;


        cursorUsuarios = db.rawQuery("SELECT * FROM " + TABLE_CARRITO + " WHERE id = '" + id + "'",null);

        cursorUsuarios.moveToFirst();

        return cursorUsuarios.getString(2);
    }

    //ubicaciones

    public long agregaUbi (String usuario, String nombre, String ubi){

        long id = 0;

        try {

            RapicoopDatabase rapidb = new RapicoopDatabase(context);
            SQLiteDatabase db = rapidb.getWritableDatabase();

            ContentValues values = new ContentValues();
            values.put("usuario", usuario);
            values.put("nombre", nombre);
            values.put("direccion", ubi);

            id = db.insert(TABLE_UBICACIONES, null, values);
        }catch (Exception ex){
            ex.toString();
        }

        return id;
    }



}
