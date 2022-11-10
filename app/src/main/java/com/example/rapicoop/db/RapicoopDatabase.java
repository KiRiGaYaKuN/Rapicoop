package com.example.rapicoop.db;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import androidx.annotation.Nullable;


public class RapicoopDatabase extends SQLiteOpenHelper {

    public static final String DATABASE_NAME="Rapicoop.db";
    public static final int DATABASE_VERSION=20;
    public static final String TABLE_NAME="t_usuarios";
    public static final String TABLE_OFERTA="t_ofertas";
    public static final String TABLE_CARRITO="t_carrito";
    public static final String TABLE_UBICACIONES="t_ubicaciones";
    public static final String TABLE_ACEPTADO="t_aceptados";


    public RapicoopDatabase(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " +TABLE_NAME+ "(id INTEGER PRIMARY KEY AUTOINCREMENT,usuario TEXT,"
                + "nombres TEXT,apellidos TEXT,correo TEXT,telefono TEXT,password TEXT,rol TEXT)");

        db.execSQL("create table " + TABLE_OFERTA + "(id INTEGER PRIMARY KEY AUTOINCREMENT,usuario TEXT,"
                + "nombre TEXT,categoria TEXT,precio INTEGER,ubicacion TEXT,descripcion TEXT,imagen BLOB)");

        db.execSQL("create table " + TABLE_CARRITO + "(id INTEGER PRIMARY KEY AUTOINCREMENT,vendedor TEXT,"
                + "cliente TEXT,oferta TEXT,cantidad INTEGER)");

        db.execSQL("create table " + TABLE_UBICACIONES + "(id INTEGER PRIMARY KEY AUTOINCREMENT,usuario TEXT,"
                + "nombre TEXT,direccion TEXT)");

        db.execSQL("create table " + TABLE_ACEPTADO + "(id INTEGER PRIMARY KEY AUTOINCREMENT,oferta TEXT,"
                + "cliente TEXT,ubicacion TEXT,cantidad INTEGER, precio INTEGER,domiciliario TEXT, estado TEXT)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE " + TABLE_NAME);
        db.execSQL("DROP TABLE " + TABLE_OFERTA);
        db.execSQL("DROP TABLE " + TABLE_CARRITO);
        db.execSQL("DROP TABLE " + TABLE_ACEPTADO);
        db.execSQL("DROP TABLE " + TABLE_UBICACIONES);

        onCreate(db);
    }
}
