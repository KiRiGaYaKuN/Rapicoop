package com.example.rapicoop;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rapicoop.db.InsertarUsuario;
import com.example.rapicoop.db.RapicoopDatabase;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.io.IOException;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class Ubicacion extends AppCompatActivity {

    public static final String EXTRA_MESSAGE="mesagge";

    FusedLocationProviderClient fusedLocationProviderClient;
    TextView inicio, duracion, latitud, longitud, dir;

    private boolean running = true;
    private int s = 0;
    private int m = 0;
    private int h = 0;
    private  int seconds = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ubicacion);

        RapicoopDatabase rapidb = new RapicoopDatabase(Ubicacion.this);
        InsertarUsuario iu = new InsertarUsuario(Ubicacion.this);

        Intent it=getIntent();
        String usuario = it.getStringExtra(EXTRA_MESSAGE);

        OfertaAceptada ofertaA = iu.consultaproceso(usuario);

        if (ofertaA != null){

            long ahora = System.currentTimeMillis();
            Calendar calendario = Calendar.getInstance();
            calendario.setTimeInMillis(ahora);
            int ho = calendario.get(Calendar.HOUR_OF_DAY);
            int mo = calendario.get(Calendar.MINUTE);
            int se = calendario.get(Calendar.SECOND);

            latitud = (TextView) findViewById(R.id.latitud);
            inicio = (TextView) findViewById(R.id.horainicio);
            duracion = (TextView) findViewById(R.id.duracion);
            longitud = (TextView) findViewById(R.id.logi);
            dir = (TextView) findViewById(R.id.dir);

            String[] partes = ofertaA.getInicio().split(":");
            int hora = Integer.parseInt(partes[0]);
            int minuto = Integer.parseInt(partes[1]);
            int seg = Integer.parseInt(partes[2]);

            h = ho - hora;
            m = mo - minuto;
            s = se - seg;

            inicio.setText("H.inicio: " +hora+":"+minuto);
            fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
            localizacion();
            runTimer();

        }
    }

    private void localizacion() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.

            fusedLocationProviderClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
                @Override
                public void onComplete(@NonNull Task<Location> task) {
                    Location ubi = task.getResult();
                    if (ubi != null){
                        Geocoder geocoder = new Geocoder(Ubicacion.this, Locale.getDefault());
                        try {
                            List<Address> listadirecciones = geocoder.getFromLocation(ubi.getLatitude(), ubi.getLongitude(),1);
                            latitud.setText("Latitud: " + listadirecciones.get(0).getLatitude());
                            longitud.setText("Longitud: " + listadirecciones.get(0).getLongitude());
                            dir.setText("Dirección: " + listadirecciones.get(0).getAddressLine(0));
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            });

        }else {
            ActivityCompat.requestPermissions(Ubicacion.this, new String[] {Manifest.permission.ACCESS_FINE_LOCATION},44);
            ActivityCompat.requestPermissions(Ubicacion.this, new String[] {Manifest.permission.ACCESS_COARSE_LOCATION},44);
        }
    }

    private void runTimer(){

        duracion = (TextView) findViewById(R.id.duracion);
        seconds = (3600*h) + (60*m) + s;
        Handler handler = new Handler();
        handler.post(new Runnable() {
            @Override
            public void run() {
                int hours = seconds/3600;
                int minutes = (seconds%3600)/60;
                int secs = seconds%60;
                String time = String.format(Locale.getDefault(),"%d:%02d:%02d",hours,minutes,secs);
                duracion.setText("Duración: "+time);

                if(seconds%60==0){
                    localizacion();
                    Toast.makeText(Ubicacion.this, "se actualizo la ubicación", Toast.LENGTH_SHORT).show();
                }
                if(running)
                    seconds ++;

                handler.postDelayed(this,1000);
            }
        });
    }

}