package com.example.rapicoop;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rapicoop.db.InsertarUsuario;

import java.util.Locale;

public class Estadisticas extends AppCompatActivity {

    public static final String EXTRA_MESSAGE="mesagge";

    private ServicioEstadisticas estadistica;
    private boolean enlazado = false;

    private ServiceConnection conexion = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            ServicioEstadisticas.EsBinder load = (ServicioEstadisticas.EsBinder) iBinder;
            estadistica = load.getestadistica();
            enlazado = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            enlazado = false;
        }
    };

    @Override
    protected void onStart() {
        super.onStart();

        Intent intent = new Intent(this,ServicioEstadisticas.class);
        Log.v("Usuario","hola");
        bindService(intent, conexion, Context.BIND_AUTO_CREATE);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_estadisticas);

        Intent intent=getIntent();
        String usuario = intent.getStringExtra(EXTRA_MESSAGE);

        InsertarUsuario iu = new InsertarUsuario(Estadisticas.this);

            if(iu.devolver(usuario).equals("Vendedor")) {

                mostrarEmprendedor(usuario);
            }else{
                if(iu.devolver(usuario).equals("Consumidor")) {
                    mostrarConsumi(usuario);
                }else {
                    if(iu.devolver(usuario).equals("Domiciliario")) {
                        mostrarDomi(usuario);
                    }else {
                        Toast.makeText(Estadisticas.this, "No existe el usuario", Toast.LENGTH_SHORT).show();
                    }
                }
            }


    }

    @Override
    protected void onStop() {
        super.onStop();
        if (enlazado){
            unbindService(conexion);
            enlazado = false;
        }
    }

    public void mostrarEmprendedor(String usu){
        final TextView pregunta = (TextView) findViewById(R.id.pregunta);
        final TextView res = (TextView) findViewById(R.id.respuesta);
        final Handler handler = new Handler();
        InsertarUsuario iu = new InsertarUsuario(Estadisticas.this);
        handler.post(new Runnable() {
            @Override
            public void run() {
                String ej = "-,-";

                if (enlazado && estadistica!=null){
                    ej = estadistica.getNumero(usu);
                }

                String[] partes = ej.split(",");
                String valor = String.valueOf(partes[0]);
                String cantidad = String.valueOf(partes[1]);
                pregunta.setText("VALOR ACUMULADO DE LAS VENTAS");
                res.setText("De " + cantidad + " compras se ha recolectado " + valor + "$");
                handler.postDelayed(this,1000);
            }
        });
    }

    public void mostrarConsumi(String usu){
        final TextView pregunta = (TextView) findViewById(R.id.pregunta);
        final TextView res = (TextView) findViewById(R.id.respuesta);
        final Handler handler = new Handler();
        InsertarUsuario iu = new InsertarUsuario(Estadisticas.this);
        handler.post(new Runnable() {
            @Override
            public void run() {
                String ej = "-,-";

                if (enlazado && estadistica!=null){
                    ej = estadistica.getConsumi(usu);
                }

                String[] partes = ej.split(",");
                String valor = String.valueOf(partes[0]);
                String cantidad = String.valueOf(partes[1]);
                pregunta.setText("VALOR ACUMULADO DE LAS COMPRAS");
                res.setText("De " + cantidad + " compras se ha recolectado " + valor + "$");
                handler.postDelayed(this,1000);
            }
        });
    }

    public void mostrarDomi(String usu){
        final TextView pregunta = (TextView) findViewById(R.id.pregunta);
        final TextView res = (TextView) findViewById(R.id.respuesta);
        final Handler handler = new Handler();
        InsertarUsuario iu = new InsertarUsuario(Estadisticas.this);
        handler.post(new Runnable() {
            @Override
            public void run() {
                double ej = 0.0;

                if (enlazado && estadistica!=null){
                    ej = estadistica.getDomi(usu);
                }

                pregunta.setText("DISTANCIA PROMEDIO POR ENTREGA");
                res.setText("El promedio por entrega es de: " + ej);
                handler.postDelayed(this,1000);
            }
        });
    }

}