package com.rds.rodalies.alarmas;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.content.WakefulBroadcastReceiver;

import java.util.ArrayList;
import java.util.Calendar;

public class AlarmReciever extends WakefulBroadcastReceiver
{
    // The app's AlarmManager, which provides access to the system alarm services.
    private AlarmManager alarmMgr;
    // The pending intent that is triggered when the alarm fires.
    private PendingIntent alarmIntent;
    @Override
    public void onReceive(Context context, Intent intent)
    {
/*        ComponentName comp = new ComponentName("lineasServicio", ServicioNotificaciones.class.getName());
        startWakefulService(context, (intent.setComponent(comp)));*/
        ArrayList<Integer> listaLineas = intent.getIntegerArrayListExtra("lineasServicio");

        Intent intent2 = new Intent(context, ServicioNotificaciones.class); //Intent para el servicio
        /*intent2.putIntegerArrayListExtra("lineasServicio", listaLineas);    // PARAMETROS PARA EL SERVICIO*/

        startWakefulService(context, intent2); //Inicio del servicio
    }

    /**
     * Sets a repeating alarm that runs once a day at approximately 8:30 a.m. When the
     * alarm fires, the app broadcasts an Intent to this WakefulBroadcastReceiver.
     * @param context
     */
    public void setAlarm(Context context) {
        alarmMgr = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);

        NotificacionesSQL handlerSQL = new NotificacionesSQL(context, "Notificaciones", null, 1);

        SQLiteDatabase db = handlerSQL.getReadableDatabase();
        if(db != null) {
            Cursor cursor = db.rawQuery("SELECT * FROM Notificaciones", null);

            if (cursor.getCount() > 0) //Si el cursor tiene resultados...
            {
                if (cursor.moveToFirst()) {

                    do {
                        String hora = cursor.getString(1);
                        String minuto = cursor.getString(2);
                        String dias = cursor.getString(3);

                        String diasSeleccionados[] = NuevaAlarmaNotificacion.convertStringToArray(dias);

                        for(int i = 0; i < diasSeleccionados.length; i++){
                            Boolean estaSeleccionado = (diasSeleccionados[i].compareTo("0") == 0) ? false : true;

                            if(estaSeleccionado)
                            {
                                Calendar calendar = Calendar.getInstance();
                                /*
                                * Calendar.Sunday = 1
                                * Calendar.Monday = 2
                                * ...
                                * Calendar.Saturday = 7
                                * */

                                calendar.set(Calendar.DAY_OF_WEEK, i+1);
                                calendar.set(Calendar.HOUR_OF_DAY, Integer.parseInt(hora));
                                calendar.set(Calendar.MINUTE, Integer.parseInt(minuto));
                                calendar.set(Calendar.SECOND, 0);

                                Intent intent = new Intent(context, AlarmReciever.class);
                                alarmIntent = PendingIntent.getBroadcast(context, cursor.getInt(0), intent, 0);

                                alarmMgr.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY * 7, alarmIntent);
                            }
                        }
                    } while (cursor.moveToNext());
                }
            }
        }

/*
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        // Set the alarm's trigger time to 8:30 a.m.
        calendar.set(Calendar.HOUR_OF_DAY, 14);
        calendar.set(Calendar.MINUTE, 01);

        alarmMgr.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), 24 * 60 * 60 * 1000, PendingIntent.getBroadcast(context, 0, intent, 0));
*/



        // Enable {@code SampleBootReceiver} to automatically restart the alarm when the
        // device is rebooted.
        ComponentName receiver = new ComponentName(context, SampleBootReceiver.class);
        PackageManager pm = context.getPackageManager();

        pm.setComponentEnabledSetting(receiver, PackageManager.COMPONENT_ENABLED_STATE_ENABLED, PackageManager.DONT_KILL_APP);
    }
    // END_INCLUDE(set_alarm)

    /**
     * Cancels the alarm.
     * @param context
     */
    // BEGIN_INCLUDE(cancel_alarm)
    public void cancelAlarm(Context context) {
        // If the alarm has been set, cancel it.
        if (alarmMgr!= null) {
            alarmMgr.cancel(alarmIntent);
        }

        // Disable {@code SampleBootReceiver} so that it doesn't automatically restart the
        // alarm when the device is rebooted.
        ComponentName receiver = new ComponentName(context, SampleBootReceiver.class);
        PackageManager pm = context.getPackageManager();

        pm.setComponentEnabledSetting(receiver,
                PackageManager.COMPONENT_ENABLED_STATE_DISABLED,
                PackageManager.DONT_KILL_APP);
    }
}
