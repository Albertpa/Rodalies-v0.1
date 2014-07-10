package com.rds.rodalies.alarmas;

import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.ListActivity;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.SimpleAdapter;

import com.rds.rodalies.Constants;
import com.rds.rodalies.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ListaNotificaciones extends ListActivity {

    private NotificacionesSQL handlerSQL;
    private SQLiteDatabase db;
    private List<Integer> listaAlertas = new ArrayList<Integer>(); //Lista de ids de alertas
    private Integer numeroElementosLista = 0;
    private ArrayList<Map<String, String>> list;
    private SimpleAdapter adapter;
    private final String[] from = { "name", "purpose" };
    private final int[] to = { android.R.id.text1, android.R.id.text2 };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lista_notificaciones);
    }

    @Override
    protected void onResume()
    {
        list = buildData();

        adapter = new SimpleAdapter(this, list, android.R.layout.simple_list_item_2, from, to);
        setListAdapter(adapter);

        this.getListView().setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> a, View v, int position, long id) {
                Log.i("lista", "Ver alerta");
                modificarAlerta(listaAlertas.get(position));
            }
        });

        this.getListView().setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long arg3) {
                borrarAlerta(position);
                return false;
            }
        });

        invalidateOptionsMenu();
        super.onResume();
    }

    private void modificarAlerta(final Integer id) {

        Intent intent = null;

        handlerSQL = new NotificacionesSQL(ListaNotificaciones.this, "Notificaciones", null, 1);
        db = handlerSQL.getReadableDatabase();
        if(db != null) {
            Cursor cursor = db.rawQuery("SELECT * FROM Notificaciones WHERE _id='" + id + "'", null);

            if (cursor.getCount() > 0) //Si el cursor tiene resultados...
            {
                numeroElementosLista = cursor.getCount();
                if (cursor.moveToFirst()) {
                    Alerta alerta = new Alerta(id, cursor.getString(1), cursor.getString(2), cursor.getString(3));

                    intent = new Intent(getBaseContext(), NuevaAlarmaNotificacion.class);
                    intent.putExtra("ID", alerta.getId());
                    intent.putExtra("HORA",alerta.getHora());
                    intent.putExtra("MINUTOS", alerta.getMinutos());
                    intent.putExtra("DIAS", alerta.getDiasSeleccionados());
                }
            }
        }

        db.close();

        startActivity(intent);
        //startActivityForResult(new Intent(ListaNotificaciones.this, NuevaAlarmaNotificacion.class), 100);

    }

    private void borrarAlerta(final int p) {
        AlertDialog.Builder alertbox = new AlertDialog.Builder(this);
        alertbox.setMessage(getString(R.string.eliminar_alerta));

        alertbox.setNeutralButton(getString(R.string.si), new DialogInterface.OnClickListener()
        {
            public void onClick(DialogInterface dialog, int whichButton)
            {
                /*
                * La lista listaAlertas contiene los ids de las alerta en el mismo orden que la list.
                * Se selecciona el id de la alerta por la posicion que se ha pulsado
                * se elimina de la base de datos y de la lista listaAlertas. La alerta anterior queda enlazada
                * con la siguiente alerta. Asi pues en la lista siempre estan correctamente ordenadas e identificadas
                * */
                Integer idAlerta = listaAlertas.get(p);
                handlerSQL = new NotificacionesSQL(ListaNotificaciones.this, "Notificaciones", null, 1);

                db = handlerSQL.getReadableDatabase();
                db.execSQL("DELETE FROM Notificaciones WHERE _id='" + idAlerta + "'");

                listaAlertas.remove(p);
                list = buildData();
                adapter = new SimpleAdapter(ListaNotificaciones.this, list, android.R.layout.simple_list_item_2, from, to);
                setListAdapter(adapter);

                db.close();
                invalidateOptionsMenu();

                Intent intent = new Intent(ListaNotificaciones.this, AlarmReciever.class);
                PendingIntent.getBroadcast(ListaNotificaciones.this, idAlerta, intent, PendingIntent.FLAG_UPDATE_CURRENT).cancel();

            }
        });
        alertbox.setNegativeButton(getString(R.string.no), new DialogInterface.OnClickListener()
        {
            public void onClick(DialogInterface dialog, int whichButton)
            {

            }
        });

        alertbox.show();
    }

    private ArrayList<Map<String, String>> buildData() {
        ArrayList<Map<String, String>> list = new ArrayList<Map<String, String>>();
        listaAlertas = new ArrayList<Integer>();
        handlerSQL = new NotificacionesSQL(this, "Notificaciones", null, 1);

        db = handlerSQL.getReadableDatabase();

        if(db != null){
            Cursor cursor = db.rawQuery("SELECT * FROM Notificaciones", null);

            if(cursor.getCount() > 0) //Si el cursor tiene resultados...
            {
                numeroElementosLista = cursor.getCount();
                if (cursor.moveToFirst()) {

                    do {
                        Integer id = cursor.getInt(cursor.getColumnIndex("_id"));
                        String hora = cursor.getString(1);
                        String minuto = cursor.getString(2);
                        String dias = cursor.getString(3);

                        Integer auxMinuto = Integer.parseInt(minuto);

                        if(auxMinuto < 10) {
                            minuto = String.format("%02d", Integer.parseInt(minuto.toString()));
                        }

                        list.add(putData(hora+":" + minuto, obtenerDias(dias)));
                        listaAlertas.add(id);

                    } while(cursor.moveToNext());
                }
            }
            /* Si el numero de alarmas almacenadas es inferior al maximo de alarmas
            permitidas, se habilita el boton de agregar nueva alerta */
            if(cursor.getCount() < Constants.MAX_ALARMAS){
                invalidateOptionsMenu(); //rehace el menu
            }
        }
        db.close();
        return list;
    }

    private HashMap<String, String> putData(String name, String purpose) {
        HashMap<String, String> item = new HashMap<String, String>();
        item.put("name", name);
        item.put("purpose", purpose);
        return item;
    }

    private String obtenerDias(String dias){
        String[] split = dias.split(",");
        String rt = "";
        for (int i = 0; i < split.length; i++) {
            if(split[i].equals("1")){
                switch(i){
                    case 0:
                        rt += getResources().getString( R.string.domingo) + " ";
                        break;
                    case 1:
                        rt += getResources().getString(R.string.lunes) + " ";
                        break;
                    case 2:
                        rt += getResources().getString(R.string.martes) + " ";
                        break;
                    case 3:
                        rt += getResources().getString(R.string.miercoles) + " ";
                        break;
                    case 4:
                        rt += getResources().getString(R.string.jueves) + " ";
                        break;
                    case 5:
                        rt += getResources().getString(R.string.viernes) + " ";
                        break;
                    case 6:
                        rt += getResources().getString(R.string.sabado) + " ";
                        break;
                }
            }
        }
        return rt;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.lista_notificaciones, menu);

        if(numeroElementosLista < Constants.MAX_ALARMAS){
            MenuItem item = menu.findItem(R.id.nuevaAlerta);
            item.setEnabled(true);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId())
        {
            case R.id.nuevaAlerta:
                startActivityForResult(new Intent(ListaNotificaciones.this, NuevaAlarmaNotificacion.class), 100);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
