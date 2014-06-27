package com.rds.rodalies;

import android.content.ContentValues;
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
    private ToggleButton lunes;
    private ToggleButton martes;
    private ToggleButton miercoles;
    private ToggleButton jueves;
    private ToggleButton viernes;
    private ToggleButton sabado;
    private ToggleButton domingo;

    private Integer hora;
    private Integer minuto;

    private Boolean esActualizacion = false;
    private Integer idActualizacion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nueva_alarma_notificacion);

        timePicker1 = (TimePicker) findViewById(R.id.timePicker1);

        lunes = (ToggleButton)findViewById(R.id.toggleButtonLUN);
        martes = (ToggleButton)findViewById(R.id.toggleButtonMAR);
        miercoles = (ToggleButton)findViewById(R.id.toggleButtonMIE);
        jueves = (ToggleButton)findViewById(R.id.toggleButtonJUE);
        viernes = (ToggleButton)findViewById(R.id.toggleButtonVIE);
        sabado = (ToggleButton)findViewById(R.id.toggleButtonSAB);
        domingo = (ToggleButton)findViewById(R.id.toggleButtonDOM);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            Alerta alarma = new Alerta(extras.getInt("ID"), extras.getString("HORA"), extras.getString("MINUTOS"), extras.getString("DIAS"));
            idActualizacion = alarma.getId();
            esActualizacion = true;
            completarDatos(alarma);
        }
    }

    /*
    * Asigna los valores de hora, minutos y dias seleccionados a la pantalla
    * */
    private void completarDatos(Alerta alarma) {

        timePicker1.setCurrentHour(Integer.parseInt(alarma.getHora()));
        timePicker1.setCurrentMinute(Integer.parseInt(alarma.getMinutos()));

        String[] diasSeleccionados = convertStringToArray(alarma.getDiasSeleccionados());

        for(int i = 0; i < diasSeleccionados.length; i++){
            Boolean estaSeleccionado = (diasSeleccionados[i].compareTo("0") == 0) ? false : true;;

            switch (i){
                case 0:
                    domingo.setChecked(estaSeleccionado);
                    break;
                case 1:
                    lunes.setChecked(estaSeleccionado);
                    break;
                case 2:
                    martes.setChecked(estaSeleccionado);
                    break;
                case 3:
                    miercoles.setChecked(estaSeleccionado);
                    break;
                case 4:
                    jueves.setChecked(estaSeleccionado);
                    break;
                case 5:
                    viernes.setChecked(estaSeleccionado);
                    break;
                case 6:
                    sabado.setChecked(estaSeleccionado);
                    break;
            }
        }
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

        diasSeleccionados[0] = (domingo.isChecked() == true) ? "1" : "0"; // 1 = dia seleccionado, 0 = dia no seleccionado
        diasSeleccionados[1] = (lunes.isChecked() == true) ? "1" : "0"; // 1 = dia seleccionado, 0 = dia no seleccionado
        diasSeleccionados[2] = (martes.isChecked() == true) ? "1" : "0";
        diasSeleccionados[3] = (miercoles.isChecked() == true) ? "1" : "0";
        diasSeleccionados[4] = (jueves.isChecked() == true) ? "1" : "0";
        diasSeleccionados[5] = (viernes.isChecked() == true) ? "1" : "0";
        diasSeleccionados[6] = (sabado.isChecked() == true) ? "1" : "0";

        if(Arrays.asList(diasSeleccionados).contains("1") == false){ //Si no se ha seleccionado ningun dia
            Alert alert = new Alert();
            alert.alert(getString(R.string.seleccionar_un_dia), this);
        }
        else{
            hora = timePicker1.getCurrentHour();
            minuto = timePicker1.getCurrentMinute();

            String dias = convertArrayToString(diasSeleccionados);

            NotificacionesSQL handlerSQL = new NotificacionesSQL(this, "Notificaciones", null, 1);
            SQLiteDatabase db = handlerSQL.getReadableDatabase();

            if(db != null) {
                /* Si es una modificación hay q usar el mismo id */
                if(esActualizacion){
                    ContentValues valores = new ContentValues();
                    valores.put("hora",hora);
                    valores.put("minuto",minuto);
                    valores.put("dias",dias);
                    db.update("Notificaciones", valores, "_id='" + idActualizacion + "'", null);
                    esActualizacion = false;
                }
                else{
                    db.execSQL("INSERT or replace INTO Notificaciones (hora, minuto, dias) " + "VALUES('" + hora + "', '" + minuto + "', '" + dias + "')");
                }

                db.close();

                cerrarPantalla();
            }
        }
    }

    private void cerrarPantalla() {

        AlarmReciever alarmReciever = new AlarmReciever();
        alarmReciever.setAlarm(this);
        finish();
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
