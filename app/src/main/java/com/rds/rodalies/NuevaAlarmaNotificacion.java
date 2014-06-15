package com.rds.rodalies;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.TimePicker;

import java.util.Calendar;


public class NuevaAlarmaNotificacion extends FragmentActivity {


    private TimePicker timePicker1;

    private int hour;
    private int minute;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nueva_alarma_notificacion);

    }


    public void showTimePickerDialog(View v) {
        DialogFragment newFragment = new ConfiguradorNotificaciones();
        newFragment.show(getSupportFragmentManager(), "timePicker");
    }

    // display current time
    public void setCurrentTimeOnView() {

        timePicker1 = (TimePicker) findViewById(R.id.timePicker1);

        final Calendar c = Calendar.getInstance();
        hour = c.get(Calendar.HOUR_OF_DAY);
        minute = c.get(Calendar.MINUTE);


        //si no hay datos guardados asignamos la hora actual a los pickers
        // set current time into timepicker
        timePicker1.setCurrentHour(hour);
        timePicker1.setCurrentMinute(minute);

    }

    public void guardar(View v){
        Log.e("Rodalies", "guarda elemento");
        finish();
    }

}
