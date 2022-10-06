package com.example.rapicoop;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class eliminarofertav extends AppCompatActivity {

   /* botoneliminar.setOnClickListener {
        new AlertDialog.Builder(this).apply {
            setTitle("Entregar tarea")
            setMessage("¿Estás seguro de borrar la oferta publicada, los cambios son irreversibles")
            setPositiveButton("Si") { _: DialogInterface, _: Int ->
                    eliminar = true
                eliminaroferta()
            }
            setNegativeButton("No", null)
        }.show()
    }
}
    */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eliminarofertav);
    }
}