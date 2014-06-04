package com.rds.rodalies;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import java.util.ArrayList;

public class AlarmReciever extends BroadcastReceiver
{
    @Override
    public void onReceive(Context context, Intent intent)
    {
        ArrayList<Integer> listaLineas = intent.getIntegerArrayListExtra("lineasServicio");

        Intent intent2 = new Intent(context, ServicioNotificaciones.class); //Itent para el servicio
        intent2.putIntegerArrayListExtra("lineasServicio", listaLineas);// PARAMETROS PARA EL SERVICIO

        context.startService(intent2); //Inicio del servicio

    }

}
