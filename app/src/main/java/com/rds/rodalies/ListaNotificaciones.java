package com.rds.rodalies;

import android.app.ListActivity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ListaNotificaciones extends ListActivity {

    private  NotificacionesSQL handlerSQL;
    private SQLiteDatabase db;
   // private ListView lista ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lista_notificaciones);


        //lista = (ListView) findViewById( R.id.list);


        ArrayList<Map<String, String>> list = buildData();
        String[] from = { "name", "purpose" };
        int[] to = { android.R.id.text1, android.R.id.text2 };

        SimpleAdapter adapter = new SimpleAdapter(this, list,
                android.R.layout.simple_list_item_2, from, to);

        setListAdapter(adapter);



    }

    private ArrayList<Map<String, String>> buildData() {
        ArrayList<Map<String, String>> list = new ArrayList<Map<String, String>>();
        handlerSQL = new NotificacionesSQL(this, "rodalies.db", null, 1);

        db = handlerSQL.getReadableDatabase();

        String dia;
        String hora;
        String minut;
        //String auxiliar;


        if(db != null){
            //consulta de login
            Cursor cursor = db.rawQuery("SELECT * FROM Notificaciones", null);

            if(cursor.getCount() > 0) //Si el cursor tiene resultados...
            {

                if (cursor.moveToFirst()) {

                    do {

                        hora =cursor.getString(1);
                        minut =cursor.getString(2);
                        dia =cursor.getString(3);
                        //auxiliar = "Hora: "+hora+" :"+minut+"  Dies:"+ dia;

                        list.add(putData(hora+":"+minut, obtenerDias(dia)));

                    } while(cursor.moveToNext());
                }

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
                       rt += getResources().getString(R.string.lunes) + " ";
                        break;
                   case 1:
                       rt += getResources().getString(R.string.martes) + " ";
                       break;
                   case 2:
                       rt += getResources().getString(R.string.miercoles) + " ";
                       break;
                   case 3:
                       rt += getResources().getString(R.string.jueves) + " ";
                       break;
                   case 4:
                       rt += getResources().getString(R.string.viernes) + " ";
                       break;
                   case 5:
                       rt += getResources().getString(R.string.sabado) + " ";
                       break;
                   case 6:
                       rt += getResources().getString( R.string.domingo) + " ";
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
