package com.rds.rodalies.alarmas;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.TimePicker;
import android.widget.ToggleButton;

import com.rds.rodalies.Alert;
import com.rds.rodalies.R;


public class NuevaAlarmaNotificacion extends FragmentActivity {

    private NotificacionesSQL dbHelper = new NotificacionesSQL(this);

    private AlarmModel alarmDetails;

    private TimePicker timePicker1;
    private ToggleButton lunes;
    private ToggleButton martes;
    private ToggleButton miercoles;
    private ToggleButton jueves;
    private ToggleButton viernes;
    private ToggleButton sabado;
    private ToggleButton domingo;

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


        long id = getIntent().getExtras().getLong("id");
        if (id == -1) {
            alarmDetails = new AlarmModel();
        } else {
            alarmDetails = dbHelper.getAlarm(id);
            completarDatos(alarmDetails);
        }
    }

    /*
    * Asigna los valores de hora, minutos y dias seleccionados a la pantalla
    * */
    private void completarDatos(AlarmModel alarma) {

        timePicker1.setCurrentHour(alarma.timeHour);
        timePicker1.setCurrentMinute(alarma.timeMinute);

        domingo.setChecked(alarma.getRepeatingDay(AlarmModel.SUNDAY));
        lunes.setChecked(alarma.getRepeatingDay(AlarmModel.MONDAY));
        martes.setChecked(alarma.getRepeatingDay(AlarmModel.TUESDAY));
        miercoles.setChecked(alarma.getRepeatingDay(AlarmModel.WEDNESDAY));
        jueves.setChecked(alarma.getRepeatingDay(AlarmModel.THURSDAY));
        viernes.setChecked(alarma.getRepeatingDay(AlarmModel.FRIDAY));
        sabado.setChecked(alarma.getRepeatingDay(AlarmModel.SATURDAY));
    }

    public void guardar(View v) {

        if(domingo.isChecked() || lunes.isChecked() || martes.isChecked() || miercoles.isChecked() || jueves.isChecked() ||
                viernes.isChecked() || sabado.isChecked()){

            alarmDetails.timeMinute = timePicker1.getCurrentMinute().intValue();
            alarmDetails.timeHour = timePicker1.getCurrentHour().intValue();
            alarmDetails.setRepeatingDay(AlarmModel.SUNDAY, domingo.isChecked());
            alarmDetails.setRepeatingDay(AlarmModel.MONDAY, lunes.isChecked());
            alarmDetails.setRepeatingDay(AlarmModel.TUESDAY, martes.isChecked());
            alarmDetails.setRepeatingDay(AlarmModel.WEDNESDAY, miercoles.isChecked());
            alarmDetails.setRepeatingDay(AlarmModel.THURSDAY, jueves.isChecked());
            alarmDetails.setRepeatingDay(AlarmModel.FRIDAY, viernes.isChecked());
            alarmDetails.setRepeatingDay(AlarmModel.SATURDAY, sabado.isChecked());
            alarmDetails.repeatWeekly = true;

            AlarmManagerHelper.cancelAlarms(this);

            if (alarmDetails.id < 0) {
                dbHelper.createAlarm(alarmDetails);
            } else {
                dbHelper.updateAlarm(alarmDetails);
            }

            AlarmManagerHelper.setAlarms(this);

            setResult(RESULT_OK);

            finish();
        }
        else
        {
            Alert alert = new Alert();
            alert.alert(getString(R.string.seleccionar_un_dia), this);
        }
    }
}
