package com.example.rodalies;

import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class Page1Activity extends Fragment {
	private View myFragmentView;
	private TextView textP;

    private int codeLinea;

    public Linea l;
    /* Array con las URL de las lineas */
    private String[] lineasURL = new String[]{
            "http://www.gencat.cat/rodalies/incidencies_rodalies_rss_r1_ca_ES.xml",
            "http://www.gencat.cat/rodalies/incidencies_rodalies_rss_r2_nord_ca_ES.xml",
            "http://www.gencat.cat/rodalies/incidencies_rodalies_rss_r2_sud_ca_ES.xml",
            "http://www.gencat.cat/rodalies/incidencies_rodalies_rss_r3_ca_ES.xml",
            "http://www.gencat.cat/rodalies/incidencies_rodalies_rss_r4_ca_ES.xml",
            "http://www.gencat.cat/rodalies/incidencies_rodalies_rss_r7_ca_ES.xml",
            "http://www.gencat.cat/rodalies/incidencies_rodalies_rss_r8_ca_ES.xml",
            "http://www.gencat.cat/rodalies/incidencies_rodalies_rss_r11_ca_ES.xml",
            "http://www.gencat.cat/rodalies/incidencies_rodalies_rss_r12_ca_ES.xml",
            "http://www.gencat.cat/rodalies/incidencies_rodalies_rss_r13_ca_ES.xml",
            "http://www.gencat.cat/rodalies/incidencies_rodalies_rss_r14_ca_ES.xml",
            "http://www.gencat.cat/rodalies/incidencies_rodalies_rss_r15_ca_ES.xml",
            "http://www.gencat.cat/rodalies/incidencies_rodalies_rss_r16_ca_ES.xml"
    };

    /* Array con los nombres de las lineas */
    private String[] nombreLineas = new String[]{
            "R1", "R2 Nord", "R2 Sud", "R3", "R4", "R7", "R8", "R11", "R12", "R13", "R14", "R15", "R16"
    };




	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    	myFragmentView = inflater.inflate(R.layout.activity_page1, container, false);

    	//Log.e("Rodalies", getArguments().getString(Constants.LINEA_PRINCIPAL));
    	
       	textP = (TextView) myFragmentView.findViewById(R.id.textP);

        codeLinea = getArguments().getInt(Constants.LINEA_PRINCIPAL);
        crearObjetosLinea(codeLinea);

        Log.e("Rodalies", "ESTADO->"+l.getEstado());
        //textP.setText("Codi de la linea: " + getArguments().getInt(Constants.LINEA_PRINCIPAL));



		return myFragmentView;
	}

    private void asignarEstado(Linea l) {
        if (l.getEstado() != null)
        {
                /* Por defecto el estado de una linea es un string vacio.
                * Si no existe incidencia para una linea, se añade el texto de "normalidad en la linea
                * y se colorea de color verde.
                * Si se existe una incidencia para una linea, se añade a la caja de texto y se
                * colorea de color rojo */
            if(l.getEstado() == ""){
                textP.setText(getString(R.string.normalitat));
                textP.setTextColor(Color.parseColor("#538900"));

            }
            else
            {

                textP.setText(l.getEstado());
                textP.setTextColor(Color.parseColor("#BA0000"));

            }
        }
    }

    /* Esta funcion crea un objecto "Linea", le asigna el nombre y la url.
    * El estado inicialmente es un string vacio "", a continuación se hace una llamada
    * al AsyncTask que descargará el estado y se le asignará al objeto */
    private void crearObjetosLinea(int codeLinea) {
        /*
        listaDeLineas = new ArrayList<Linea>();
        for(int i=0; i < nombreLineas.length; i++)
        {
            Linea L = new Linea(nombreLineas[i], "", lineasURL[i]);
            new LineasAsyncTask(L).execute();
        }
        adapter = new LineasAdapter(this, android.R.layout.simple_list_item_1, listaDeLineas);
        this.getListView().setAdapter(adapter);
        */

        l = new Linea(nombreLineas[codeLinea], "", lineasURL[codeLinea]);
        new LineasAsyncTask(l).execute();
    }

    private class LineasAsyncTask extends AsyncTask<String, Void, String> {
        private Linea linea;

        public LineasAsyncTask(Linea l) {
            this.linea = l;
        }

        /* Se llama a la clase RssHandler pasando como parametro el string URL.
        * Está clase devolverá el estado de la linea como un string.
        * Si la descarga se ha realizado con exito (!null), se asigna el estado
        * y se añade el objeto "Linea" a la lista de lineas */
        @Override
        protected String doInBackground(String... params) {
            RssHandler rh = new RssHandler();
            return rh.getLatestArticles(this.linea.getUrl());
        }
        @Override
        protected void onPostExecute(String response)
        {
            if(response != null)
            {
                this.linea.setEstado(response);
            }
            //listaDeLineas.add(this.linea);
            //adapter.notifyDataSetChanged();

            //l= this.linea;
            Log.e("Rodalies", "estat->>"+response);

            l = this.linea;
            asignarEstado(l);

        }
    }

	
}
