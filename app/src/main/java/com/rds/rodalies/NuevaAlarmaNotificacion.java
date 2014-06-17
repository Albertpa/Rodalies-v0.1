package com.rds.rodalies;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.TimePicker;
import android.widget.ToggleButton;

import java.util.Arrays;
import java.util.Calendar;


public class NuevaAlarmaNotificacion extends FragmentActivity {

    private TimePicker timePicker1;

    private Integer hora;
    private Integer minuto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nueva_alarma_notificacion);

        timePicker1 = (TimePicker) findViewById(R.id.timePicker1);
    }

    // display current time
    public void setCurrentTimeOnView() {


        final Calendar c = Calendar.getInstance();
        hora = c.get(Calendar.HOUR_OF_DAY);
        minuto = c.get(Calendar.MINUTE);

        //si no hay datos guardados asignamos la hora actual a los pickers
        //set current time into timepicker
        timePicker1.setCurrentHour(hora);
        timePicker1.setCurrentMinute(minuto);
    }

    public void guardar(View v){
        Log.e("Rodalies", "guarda elemento");

        String[] diasSeleccionados = new String[7];

        ToggleButton lunes = (ToggleButton)findViewById(R.id.toggleButtonLUN);
        ToggleButton martes = (ToggleButton)findViewById(R.id.toggleButtonMAR);
        ToggleButton miercoles = (ToggleButton)findViewById(R.id.toggleButtonMIE);
        ToggleButton jueves = (ToggleButton)findViewById(R.id.toggleButtonJUE);
        ToggleButton viernes = (ToggleButton)findViewById(R.id.toggleButtonVIE);
        ToggleButton sabado = (ToggleButton)findViewById(R.id.toggleButtonSAB);
        ToggleButton domingo = (ToggleButton)findViewById(R.id.toggleButtonDOM);

        diasSeleccionados[0] = (lunes.isChecked() == true) ? "1" : "0"; // 1 = dia seleccionado, 0 = dia no seleccionado
        diasSeleccionados[1] = (martes.isChecked() == true) ? "1" : "0";
        diasSeleccionados[2] = (miercoles.isChecked() == true) ? "1" : "0";
        diasSeleccionados[3] = (jueves.isChecked() == true) ? "1" : "0";
        diasSeleccionados[4] = (viernes.isChecked() == true) ? "1" : "0";
        diasSeleccionados[5] = (sabado.isChecked() == true) ? "1" : "0";
        diasSeleccionados[6] = (domingo.isChecked() == true) ? "1" : "0";

        if(Arrays.asList(diasSeleccionados).contains("1") == false){ //Si no se ha seleccionado ningun dia
            Alert alert = new Alert();
            alert.alert(getString(R.string.seleccionar_un_dia), this);
        }
        else{
            hora = timePicker1.getCurrentHour();
            minuto = timePicker1.getCurrentMinute();
            String dias = convertArrayToString(diasSeleccionados);



            NotificacionesSQL handlerSQL = new NotificacionesSQL(this, "rodalies.db", null, 1);

            SQLiteDatabase db = handlerSQL.getReadableDatabase();


            if(db != null) {

                //consulta de login
                 Cursor cursor = db.rawQuery("SELECT * FROM Notificaciones", null);

                 Log.e("Rodalies", ""+cursor.getCount());
                if(cursor.getCount() < Constants.MAX_PREF) //Si el cursor tiene resultados...
                {
                    db.execSQL("INSERT or replace INTO Notificaciones (hora, minuto, dias) " + "VALUES('" + hora + "', '" + minuto + "', '" + dias + "')");
                    db.close();
                    finish();

                }else{
                    Alert alert = new Alert();
                    alert.alert(getString(R.string.max_pref), this);
                }

            }

            db.close();




        }
    }

    public static String convertArrayToString(String[] dias){
        String separador = ",";
        String str = "";
        for (int i = 0; i < dias.length; i++) {
            str = str + dias[i];
            // No añadir coma al ultimo elemento
            if(i < dias.length-1){
                str = str + separador;
            }
        }
        return str;
    }
    public static String[] convertStringToArray(String str){
        String separador = ",";
        String[] arr = str.split(separador);
        return arr;
    }
}
