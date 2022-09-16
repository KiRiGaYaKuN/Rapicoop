package com.example.rapicoop.db;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import androidx.annotation.Nullable;


public class RapicoopDatabase extends SQLiteOpenHelper {

    public static final String DATABASE_NAME="Rapicoop.db";
    public static final int DATABASE_VERSION=12;
    public static final String TABLE_NAME="t_usuarios";
    public static final String TABLE_OFERTA="t_ofertas";

    public RapicoopDatabase(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " +TABLE_NAME+ "(id INTEGER PRIMARY KEY AUTOINCREMENT,usuario TEXT,"
                + "nombres TEXT,apellidos TEXT,correo TEXT,telefono TEXT,password TEXT,rol TEXT)");

        db.execSQL("create table " + TABLE_OFERTA + "(id INTEGER PRIMARY KEY AUTOINCREMENT,usuario TEXT,"
                + "nombre TEXT,categoria TEXT,precio INTEGER,ubicacion TEXT,descripcion TEXT,imagen BLOB)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE " + TABLE_NAME);
       // db.execSQL("DROP TABLE " + TABLE_OFERTA);
        onCreate(db);
    }
}
