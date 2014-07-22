package com.rds.rodalies.alarmas;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.rds.rodalies.R;

public class ListaNotificaciones extends ListActivity {

    private AlarmListAdapter mAdapter;

    private NotificacionesSQL dbHelper = new NotificacionesSQL(this);
    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;

        setContentView(R.layout.lista_notificaciones);

        mAdapter = new AlarmListAdapter(this, dbHelper.getAlarms());

        setListAdapter(mAdapter);
    }

    @Override
    protected void onResume()
    {
        //numeroElementosLista = dbHelper.getAlarms().size();

        super.onResume();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            mAdapter.setAlarms(dbHelper.getAlarms());
            mAdapter.notifyDataSetChanged();
        }
    }

    public void borrarAlerta(long idAlarma) {

        final long alarmId = idAlarma;
        AlertDialog.Builder builder = new AlertDialog.Builder(ListaNotificaciones.this);
        builder.setMessage(R.string.eliminar_alerta)
                .setCancelable(true)
                .setNegativeButton(R.string.no, null)
                .setPositiveButton(R.string.si, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //Cancel Alarms
                        AlarmManagerHelper.cancelAlarms(mContext);
                        //Delete alarm from DB by id
                        dbHelper.deleteAlarm(alarmId);
                        //Refresh the list of the alarms in the adaptor
                        mAdapter.setAlarms(dbHelper.getAlarms());
                        //Notify the adapter the data has changed
                        mAdapter.notifyDataSetChanged();
                        //Set the alarms
                        AlarmManagerHelper.setAlarms(mContext);
                    }
                }).show();
    }

    public void startAlarmDetailsActivity(long id) {
        Intent intent = new Intent(this, NuevaAlarmaNotificacion.class);
        intent.putExtra("id", id);
        startActivityForResult(intent, 0);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.lista_notificaciones, menu);

/*        if(numeroElementosLista < Constants.MAX_ALARMAS){
            startAlarmDetailsActivity(-1);
        }*/

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId())
        {
            case R.id.nuevaAlerta:
                //startActivityForResult(new Intent(ListaNotificaciones.this, NuevaAlarmaNotificacion.class), 100);
                startAlarmDetailsActivity(-1);

                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
