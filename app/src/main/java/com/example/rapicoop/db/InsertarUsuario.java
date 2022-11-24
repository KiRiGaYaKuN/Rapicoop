package com.example.rapicoop.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.example.rapicoop.Carrito;
import com.example.rapicoop.DescripcionOferta;
import com.example.rapicoop.Oferta;
import com.example.rapicoop.OfertaAceptada;
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

    public long aceptarOferta (String oferta, String cliente, String ubicacion, int cantidad, int precio, String estado){

        long id = 0;

        try {

            RapicoopDatabase rapidb = new RapicoopDatabase(context);
            SQLiteDatabase db = rapidb.getWritableDatabase();

            ContentValues values = new ContentValues();
            values.put("oferta", oferta);
            values.put("cliente", cliente);
            values.put("ubicacion", ubicacion);
            values.put("cantidad", cantidad);
            values.put("precio", precio);
            values.put("estado", estado);

            id = db.insert(TABLE_ACEPTADO, null, values);
        }catch (Exception ex){
            ex.toString();
        }

        return id;
    }

    public boolean aceptarpedido (String usu, int idnt, String est,String time){

        RapicoopDatabase rapidb = new RapicoopDatabase(context);
        SQLiteDatabase db = rapidb.getWritableDatabase();
        boolean correct = false;
        Cursor cursorUsuarios = null;

        try {
            db.execSQL("UPDATE " + TABLE_ACEPTADO + " SET domiciliario = '" + usu + "',estado = '" +est+ "',inicio = '" +time+ "' WHERE id = '" + idnt + "'");
            correct = true;

        }catch (Exception ex){
            ex.toString();
            correct = false;
        }finally {
            db.close();
        }

        return correct;
    }

    public boolean modestado (int idnt, String est){

        RapicoopDatabase rapidb = new RapicoopDatabase(context);
        SQLiteDatabase db = rapidb.getWritableDatabase();
        boolean correct = false;
        Cursor cursorUsuarios = null;

        try {
            db.execSQL("UPDATE " + TABLE_ACEPTADO + " SET estado = '" + est + "' WHERE id = '" + idnt + "'");
            correct = true;

        }catch (Exception ex){
            ex.toString();
            correct = false;
        }finally {
            db.close();
        }

        return correct;
    }

    public boolean modfinal (int idnt, String finde, String dura){

        RapicoopDatabase rapidb = new RapicoopDatabase(context);
        SQLiteDatabase db = rapidb.getWritableDatabase();
        boolean correct = false;
        Cursor cursorUsuarios = null;

        try {
            db.execSQL("UPDATE " + TABLE_ACEPTADO + " SET fin = '" + finde + "',duracion = '" + dura + "' WHERE id = '" + idnt + "'");
            correct = true;

        }catch (Exception ex){
            ex.toString();
            correct = false;
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

    public ArrayList<OfertaAceptada> consultaaceptadas( String usu ){

        RapicoopDatabase rapidb = new RapicoopDatabase(context);
        SQLiteDatabase db = rapidb.getWritableDatabase();

        ArrayList<OfertaAceptada> listaofertas = new ArrayList<OfertaAceptada>();

        OfertaAceptada oferta = null;
        Cursor cursoroferta = null;

        cursoroferta = db.rawQuery("SELECT * FROM " + TABLE_ACEPTADO + " WHERE cliente LIKE '" + usu + "'",null);

        if (cursoroferta.moveToFirst()){
            do {
                oferta = new OfertaAceptada();
                oferta.setId(cursoroferta.getInt(0));
                oferta.setOferta(cursoroferta.getString(1));
                oferta.setCliente(cursoroferta.getString(2));
                oferta.setUbicacion(cursoroferta.getString(3));
                oferta.setCantidad(cursoroferta.getInt(4));
                oferta.setPrecio(cursoroferta.getInt(5));
                oferta.setDomiciliario(cursoroferta.getString(6));
                oferta.setEstado(cursoroferta.getString(7));
                listaofertas.add(oferta);
            }   while (cursoroferta.moveToNext());
        }

        cursoroferta.close();

        return listaofertas;
    }

    public ArrayList<OfertaAceptada> consultaespera(){

        RapicoopDatabase rapidb = new RapicoopDatabase(context);
        SQLiteDatabase db = rapidb.getWritableDatabase();

        ArrayList<OfertaAceptada> listaofertas = new ArrayList<OfertaAceptada>();

        OfertaAceptada oferta = null;
        Cursor cursoroferta = null;

        cursoroferta = db.rawQuery("SELECT * FROM " + TABLE_ACEPTADO + " WHERE estado LIKE '" + "espera" + "'",null);

        if (cursoroferta.moveToFirst()){
            do {
                oferta = new OfertaAceptada();
                oferta.setId(cursoroferta.getInt(0));
                oferta.setOferta(cursoroferta.getString(1));
                oferta.setCliente(cursoroferta.getString(2));
                oferta.setUbicacion(cursoroferta.getString(3));
                oferta.setCantidad(cursoroferta.getInt(4));
                oferta.setPrecio(cursoroferta.getInt(5));
                oferta.setDomiciliario(cursoroferta.getString(6));
                oferta.setEstado(cursoroferta.getString(7));
                listaofertas.add(oferta);
            }   while (cursoroferta.moveToNext());
        }

        cursoroferta.close();

        return listaofertas;
    }

    public OfertaAceptada consultaesperaid(int idnt){

        RapicoopDatabase rapidb = new RapicoopDatabase(context);
        SQLiteDatabase db = rapidb.getWritableDatabase();

        ArrayList<OfertaAceptada> listaofertas = new ArrayList<OfertaAceptada>();

        OfertaAceptada oferta = null;
        Cursor cursoroferta = null;

        cursoroferta = db.rawQuery("SELECT * FROM " + TABLE_ACEPTADO + " WHERE id LIKE '" + idnt + "'",null);

        if (cursoroferta.moveToFirst()){
            do {
                oferta = new OfertaAceptada();
                oferta.setId(cursoroferta.getInt(0));
                oferta.setOferta(cursoroferta.getString(1));
                oferta.setCliente(cursoroferta.getString(2));
                oferta.setUbicacion(cursoroferta.getString(3));
                oferta.setCantidad(cursoroferta.getInt(4));
                oferta.setPrecio(cursoroferta.getInt(5));
                oferta.setDomiciliario(cursoroferta.getString(6));
                oferta.setEstado(cursoroferta.getString(7));
                listaofertas.add(oferta);
            }   while (cursoroferta.moveToNext());
        }

        cursoroferta.close();

        return oferta;
    }

    public OfertaAceptada consultaesperanombre(String domi){

        RapicoopDatabase rapidb = new RapicoopDatabase(context);
        SQLiteDatabase db = rapidb.getWritableDatabase();

        ArrayList<OfertaAceptada> listaofertas = new ArrayList<OfertaAceptada>();

        OfertaAceptada oferta = null;
        Cursor cursoroferta = null;

        cursoroferta = db.rawQuery("SELECT * FROM " + TABLE_ACEPTADO + " WHERE domiciliario LIKE '" + domi + "'",null);

        if (cursoroferta.moveToFirst()){
            do {
                oferta = new OfertaAceptada();
                oferta.setId(cursoroferta.getInt(0));
                oferta.setOferta(cursoroferta.getString(1));
                oferta.setCliente(cursoroferta.getString(2));
                oferta.setUbicacion(cursoroferta.getString(3));
                oferta.setCantidad(cursoroferta.getInt(4));
                oferta.setPrecio(cursoroferta.getInt(5));
                oferta.setDomiciliario(cursoroferta.getString(6));
                oferta.setEstado(cursoroferta.getString(7));
                listaofertas.add(oferta);
            }   while (cursoroferta.moveToNext());
        }

        cursoroferta.close();

        return oferta;
    }

    public OfertaAceptada consultaproceso(String domi){

        RapicoopDatabase rapidb = new RapicoopDatabase(context);
        SQLiteDatabase db = rapidb.getWritableDatabase();

        ArrayList<OfertaAceptada> listaofertas = new ArrayList<OfertaAceptada>();

        OfertaAceptada oferta = null;
        Cursor cursoroferta = null;

        cursoroferta = db.rawQuery("SELECT * FROM " + TABLE_ACEPTADO + " WHERE domiciliario LIKE '" + domi + "' AND (estado LIKE 'proceso' OR estado LIKE 'entregando')",null);

        if (cursoroferta.moveToFirst()){
            do {
                oferta = new OfertaAceptada();
                oferta.setId(cursoroferta.getInt(0));
                oferta.setOferta(cursoroferta.getString(1));
                oferta.setCliente(cursoroferta.getString(2));
                oferta.setUbicacion(cursoroferta.getString(3));
                oferta.setCantidad(cursoroferta.getInt(4));
                oferta.setPrecio(cursoroferta.getInt(5));
                oferta.setDomiciliario(cursoroferta.getString(6));
                oferta.setEstado(cursoroferta.getString(7));
                oferta.setInicio(cursoroferta.getString(8));
                oferta.setFin(cursoroferta.getString(9));
                oferta.setDuracion(cursoroferta.getString(10));
                listaofertas.add(oferta);
            }   while (cursoroferta.moveToNext());
        }

        cursoroferta.close();

        return oferta;
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

    public boolean eliminarcarrito (String usu, String name){

        RapicoopDatabase rapidb = new RapicoopDatabase(context);
        SQLiteDatabase db = rapidb.getWritableDatabase();
        boolean correct = false;
        Cursor cursorUsuarios = null;

        try {
            cursorUsuarios = db.rawQuery("SELECT * FROM " + TABLE_CARRITO + " WHERE cliente LIKE '" + usu + "' AND oferta LIKE '" + name + "'",null);



            if (cursorUsuarios.moveToFirst()){
                do {
                        int ident = cursorUsuarios.getInt(0);
                        db.execSQL("DELETE FROM " + TABLE_CARRITO + " WHERE id = '" + ident + "'");
                        return true;
                }   while (cursorUsuarios.moveToNext());
            }

        }catch (Exception ex){
            ex.toString();
        }finally {
            db.close();
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

    //estadisticas

    public int[] estaemprende(String usu){

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

        int valor = 0;
        int cantidad = 0;
        for (Oferta x:listaofertas
             ) {

            ArrayList<OfertaAceptada> listaofertasA = new ArrayList<OfertaAceptada>();

            OfertaAceptada ofertaA = null;
            Cursor cursor = null;

            cursor = db.rawQuery("SELECT * FROM " + TABLE_ACEPTADO + " WHERE oferta LIKE '" + x.getNombre() + "' AND (estado <> 'espera')",null);

            if (cursor.moveToFirst()){
                do {
                    ofertaA = new OfertaAceptada();
                    ofertaA.setPrecio(cursor.getInt(5));
                    cantidad++;
                    valor = valor + ofertaA.getPrecio();
                }   while (cursor.moveToNext());
            }

            cursor.close();

        }

        int [] resultado = new int[2];
        resultado[0] = valor;
        resultado[1] = cantidad;

        return resultado;
    }

    public int[] estaconsumi(String usu){

        RapicoopDatabase rapidb = new RapicoopDatabase(context);
        SQLiteDatabase db = rapidb.getWritableDatabase();

        int valor = 0;
        int cantidad = 0;


            OfertaAceptada ofertaA = null;
            Cursor cursor = null;

            cursor = db.rawQuery("SELECT * FROM " + TABLE_ACEPTADO + " WHERE cliente LIKE '" + usu + "' AND (estado <> 'espera')",null);

            if (cursor.moveToFirst()){
                do {
                    ofertaA = new OfertaAceptada();
                    ofertaA.setPrecio(cursor.getInt(5));
                    cantidad++;
                    valor = valor + ofertaA.getPrecio();
                }   while (cursor.moveToNext());
            }

            cursor.close();



        int [] resultado = new int[2];
        resultado[0] = valor;
        resultado[1] = cantidad;

        return resultado;
    }

}
