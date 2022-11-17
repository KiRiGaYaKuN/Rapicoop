package com.example.rapicoop;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Build;
import android.util.Log;

import androidx.core.app.NotificationCompat;


public class NotificarService extends IntentService {

    public static final String EXTRA_MESSAGE = "message";
    public static final String chanel_id = "canal";
    public static final int notify_id = 57682;

    public NotificarService() {
        super("NotificarService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {

        String res = intent.getStringExtra(EXTRA_MESSAGE);
        String titulo = "";
        String texto = "";
        if (res.equals("entrega")){
            titulo = "EN CAMINO";
            texto = "El domiciliario esta en camino a su ubicación";
            mostrarTexto(titulo,texto);
        }else {
            if (res.equals("final")){
                titulo = "ENTREGADO";
                texto = "El domiciliario ha entregado el producto";
                mostrarTexto(titulo,texto);
            }else {
                if (res.equals("toma")){
                    titulo = "PEDIDO TOMADO";
                    texto = "El domiciliario se dirige al puesto de comida";
                    mostrarTexto(titulo,texto);
                }
            }
        }

    }

    public void mostrarTexto(String titulo,String texto){

        Log.v(titulo,texto);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(chanel_id, "NEW", NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager notification = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            notification.createNotificationChannel(channel);
        }

        NotificationCompat.Builder builder = new NotificationCompat.Builder(NotificarService.this,chanel_id)
                .setSmallIcon(android.R.drawable.sym_def_app_icon)
                .setContentTitle(titulo)
                .setContentText(texto)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setAutoCancel(true);

        Intent action=new Intent(NotificarService.this,LogIn.class);
        PendingIntent actionPendingIntent= PendingIntent.getActivity(NotificarService.this, 0, action, PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(actionPendingIntent);

        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        notificationManager.notify(notify_id,builder.build());
    }

}