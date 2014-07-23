package com.rds.rodalies.alarmas;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.rds.rodalies.Constants;
import com.rds.rodalies.Linea.Linea;
import com.rds.rodalies.MainActivity;
import com.rds.rodalies.R;
import com.rds.rodalies.RssHandler;

import java.util.ArrayList;

public class ServicioNotificaciones extends Service {

    public static final int NOTIFICATION_ID = 1;

    Integer contadorLineas = 0;
    Integer lineasConsultadas = 0;
    Integer numeroDeLineasConProblemas = 0;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {


        if(intent != null){
            SharedPreferences sharedSettings = getSharedPreferences(Constants.RODA_PREFERENCES, Context.MODE_PRIVATE);

            ArrayList<Integer> listaLineas;
            listaLineas = new ArrayList<Integer>(); //Codigo linea
            int codePrincipal = -1;

            for (int s = 0; s < Constants.lineaSecundaria.length; s++){
                if(sharedSettings.contains(Constants.lineaSecundaria[s])){
                    if(codePrincipal != sharedSettings.getInt(Constants.lineaSecundaria[s], -1)){
                        listaLineas.add(sharedSettings.getInt(Constants.lineaSecundaria[s], -1));
                    }
                }
            }

            contadorLineas = listaLineas.size();

            for(int i = 0; i < listaLineas.size(); i++){
                Linea linea = new Linea(Constants.nombreLineas[listaLineas.get(i)], "", Constants.lineasURL[listaLineas.get(i)], null);
                new EstadoAsyncTask(linea).execute();
            }

            return super.onStartCommand (intent, flags, startId);
        }

        return START_STICKY;

    }


    private class EstadoAsyncTask extends AsyncTask<String, Void, String> {
        private Linea linea;

        public EstadoAsyncTask(Linea l) {
            this.linea = l;
        }

        /* Se llama a la clase RssHandler pasando como parametro el string URL.
        * Está clase devolverá el estado de la linea como un string.
        * Si la descarga se ha realizado con exito (!null), se asigna el estado
        * y se añade el objeto "Linea" a la lista de lineas */
        @Override
        protected String doInBackground(String... params) {
/*            mBuilder = null;
            mainActitivyIntent = null;*/
            RssHandler rh = new RssHandler();
            return rh.getLatestArticles(this.linea.getUrl());
        }
        @Override
        protected void onPostExecute(String response)
        {
            lineasConsultadas++;
            if (response != null){
                this.linea.setEstado(response);
                numeroDeLineasConProblemas++;
            }
            Log.i("RODALIES", "Numero de lineas con problemas: " + numeroDeLineasConProblemas);
            //if((lineasConsultadas.equals(contadorLineas)) && numeroDeLineasConProblemas > 0){
            if(lineasConsultadas.equals(contadorLineas)){

                    NotificationCompat.Builder mBuilder =
                        new NotificationCompat.Builder(ServicioNotificaciones.this)
                                .setSmallIcon(android.R.drawable.ic_dialog_info)
                                .setLargeIcon((((BitmapDrawable) getResources()
                                        .getDrawable(R.drawable.logo)).getBitmap()))
                                .setContentTitle("Atención, problemas en las lineas!")
                                .setContentText("Hay problemas en " + numeroDeLineasConProblemas + " de tus lineas.")
                                .setTicker("Atención, problemas en las lineas!")
                                .setAutoCancel(true)
                                .setOnlyAlertOnce(true)
                                .setDefaults(Notification.DEFAULT_VIBRATE | Notification.DEFAULT_SOUND);

                Intent mainActitivyIntent = new Intent(ServicioNotificaciones.this, MainActivity.class);

                PendingIntent contIntent = PendingIntent.getActivity(ServicioNotificaciones.this, 0, mainActitivyIntent, PendingIntent.FLAG_UPDATE_CURRENT);
                mBuilder.setContentIntent(contIntent);

                NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                mNotificationManager.notify(NOTIFICATION_ID, mBuilder.build());

                lineasConsultadas = 0;
                numeroDeLineasConProblemas = 0;
            }
        }
    }
}
