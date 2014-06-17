package com.rds.rodalies;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class ListaNotificaciones extends ListActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lista_notificaciones);
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
