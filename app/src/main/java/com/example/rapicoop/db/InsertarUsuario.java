package com.example.rapicoop.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.Nullable;

public class InsertarUsuario extends RapicoopDatabase{

    Context context;
    public InsertarUsuario(@Nullable Context context) {
        super(context);
        this.context = context;
    }

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
}
