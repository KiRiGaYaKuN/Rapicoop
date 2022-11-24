package com.example.rapicoop;

import android.Manifest;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;

import com.example.rapicoop.db.InsertarUsuario;

import java.util.Random;

public class ServicioEstadisticas extends Service {
    public ServicioEstadisticas() {
    }

    public static final String PERMISSION_STRING = Manifest.permission.ACCESS_FINE_LOCATION;
    private final IBinder binder = new EsBinder();

    private LocationListener listener;
    private LocationManager locationManager;
    private Location lastLocation =  null;

    public static double distancia;

    public class EsBinder extends Binder {
        ServicioEstadisticas getestadistica() {
            return ServicioEstadisticas.this;
        }
    }

    @Override
    public void onCreate() {
        super.onCreate();
        listener = new LocationListener() {
            @Override
            public void onLocationChanged(@NonNull Location location) {
                if (lastLocation==null){
                    lastLocation = location;
                }
                distancia+=location.distanceTo(lastLocation);
                lastLocation = location;
            }

            @Override
            public void onProviderDisabled(String arg0) {

            }

            @Override
            public void onProviderEnabled(String arg0) {

            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }
        };
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        String proovedor = locationManager.getBestProvider(new Criteria(), true);
        if (proovedor != null) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {

                locationManager.requestLocationUpdates(proovedor, 3000, 1, listener);
                Log.v("Update Location", locationManager.getLastKnownLocation(proovedor).toString());
            }

        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (locationManager!=null && listener!=null){
            locationManager.removeUpdates(listener);
        }
        locationManager=null;
        listener=null;
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        return binder;
    }

    public String getNumero(String usu){
        InsertarUsuario iu = new InsertarUsuario(ServicioEstadisticas.this);
        int valor[] = iu.estaemprende(usu);
        String res = valor[0] + "," + valor[1];
        return res;
    }

    public String getConsumi(String usu){
        InsertarUsuario iu = new InsertarUsuario(ServicioEstadisticas.this);
        int valor[] = iu.estaconsumi(usu);
        String res = valor[0] + "," + valor[1];
        return res;
    }

    public double getDomi(String usu){
        return distancia;
    }
}