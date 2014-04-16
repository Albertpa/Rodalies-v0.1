package com.example.rodalies;

import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.Twitter;
import twitter4j.TwitterException;

public class Page1Activity extends Fragment{
    private View myFragmentView;
    private TextView textoPrincipal;

    private int codigoLinea;

    public Linea linea;
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
    /* Array cuentas twitter */
    private String[] usuarioTwitterLineas = new String[]{
            "rodalia1","rodalia2", "rodalia2", "rodalia3","rodalia4","rodalia7","rodalia8","#rod11","#rod12","#rod13",
            "#rod14","#rod15","#rod16"
    };

    private TuitsAdapter adapter;
    private ListView listaTuits;
    List<Tuit> lista_Tuits = new ArrayList<Tuit>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        myFragmentView = inflater.inflate(R.layout.activity_page1, container, false);

        listaTuits = (ListView) myFragmentView.findViewById(R.id.lista_tuits);
        adapter = new TuitsAdapter(getActivity(), android.R.layout.simple_list_item_1, lista_Tuits);
        listaTuits.setAdapter(adapter);

        textoPrincipal = (TextView) myFragmentView.findViewById(R.id.textP);

        codigoLinea = getArguments().getInt(Constants.LINEA_PRINCIPAL);
        crearObjetosLinea(codigoLinea);
           Log.e("Rodalies", "Entra al "+ codigoLinea);
        return myFragmentView;
    }

    /* Esta funcion crea un objecto "Linea", le asigna el nombre y la url.
    * El estado inicialmente es un string vacio "", a continuación se hace una llamada
    * al AsyncTask que descargará el estado y se le asignará al objeto */
     public void crearObjetosLinea(int codeLinea) {
        linea = new Linea(nombreLineas[codeLinea], "", lineasURL[codeLinea], usuarioTwitterLineas[codeLinea]);
        new EstadoAsyncTask(linea).execute();

         /* se limipia el adapter de los posibles tuits que ya contenga con tal de que no
         aparezcan repetidos cuando se recarge el tab */
         adapter.clear();
         adapter.notifyDataSetChanged();

        new TuitsAsyncTask(linea).execute();
    }

    public void actualizar(Integer idLinea){
        Log.i("ACTUALIZAR", "ACTUALIZAR");

        listaTuits = (ListView) myFragmentView.findViewById(R.id.lista_tuits);
        adapter = new TuitsAdapter(getActivity(), android.R.layout.simple_list_item_1, lista_Tuits);
        listaTuits.setAdapter(adapter);

        textoPrincipal = (TextView) myFragmentView.findViewById(R.id.textP);
        crearObjetosLinea(idLinea);
    }

    private void asignarEstado(Linea l) {
        if (l.getEstado() != null)
        {
            /* Por defecto el estado de una linea es un string vacio.
            * Si no existe incidencia para una linea, se añade el texto de "normalidad en la linea
            * y se colorea de color verde.
            * Si se existe una incidencia para una linea, se añade a la caja de texto y se
            * colorea de color rojo */
            if(l.getEstado() == "")
            {
                textoPrincipal.setText(getString(R.string.normalitat));
                textoPrincipal.setTextColor(Color.parseColor("#538900"));
            }
            else
            {
                textoPrincipal.setText(l.getEstado());
                textoPrincipal.setTextColor(Color.parseColor("#BA0000"));
            }
        }
    }

    private String obtenerFechaTuit(Date fecha)
    {
        SimpleDateFormat dateFormatter = new SimpleDateFormat("dd/MM/yyyy");
        SimpleDateFormat timeFormatter = new SimpleDateFormat("HH:mm");

        String dateAsString = dateFormatter.format(fecha); //convierte la fecha del tuit en una fecha de formato dd/MM/yyyy
        String timeAsString = timeFormatter.format(fecha); //extrae la hora del tuit

        String fechaTuit = timeAsString;

        Date hoy = new Date(); //fecha de hoy
        Calendar calHoy = Calendar.getInstance();
        calHoy.setTime(hoy);

        Calendar cal = Calendar.getInstance();
        cal.setTime(fecha);

        Calendar ayer = Calendar.getInstance();
        ayer.add(Calendar.DATE, -1);


        if(cal.get(Calendar.DAY_OF_MONTH) == calHoy.get(Calendar.DAY_OF_MONTH) &&   //Hoy
                cal.get(Calendar.MONTH) == calHoy.get(Calendar.MONTH) &&
                cal.get(Calendar.YEAR) == calHoy.get(Calendar.YEAR))
        {
            fechaTuit += " - " + getString(R.string.avui);
        }
        else if(cal.get(Calendar.DAY_OF_MONTH) == ayer.get(Calendar.DAY_OF_MONTH) &&  //Ayer
                cal.get(Calendar.MONTH) == ayer.get(Calendar.MONTH) &&
                cal.get(Calendar.YEAR) == ayer.get(Calendar.YEAR))
        {
            fechaTuit += " - " + getString(R.string.ahir);
        }
        else //otro dia
        {
            fechaTuit += " - " + dateAsString;
        }

        return fechaTuit;
    }

    private class EstadoAsyncTask extends AsyncTask<String, Void, String> {
        private Linea linea;

        public EstadoAsyncTask(Linea l) {
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
            Log.i("Rodalies", "estat->>"+response);

            Page1Activity.this.linea = this.linea;  // ??????????
            asignarEstado(Page1Activity.this.linea);
        }
    }

    private class TuitsAsyncTask extends AsyncTask<String, Void, Void> {
        private Linea linea;

        public TuitsAsyncTask(Linea l) {
            this.linea = l;
        }

        @Override
        protected Void doInBackground(String... params) {

            ConfiguradorTwitter config = ConfiguradorTwitter.getInstance();
            final Twitter twitter = config.getTwitter();

            final Query query = new Query(linea.getUsuarioTwitter()); //Establecer nombre de usuario (sin @) o hashtag
            query.count(15); //Numero maximo de tuits maximo

            try {
                QueryResult result = twitter.search(query);
                for (twitter4j.Status status : result.getTweets()) {
                    Log.i("TWEETS", "@" + status.getUser().getScreenName() + ": " + status.getText() + " " + status.getCreatedAt());

                    String fecha = obtenerFechaTuit(status.getCreatedAt());

                    Tuit tuit = new Tuit("@" + status.getUser().getScreenName(), status.getText(), fecha);
                    lista_Tuits.add(tuit);
                }
            } catch (TwitterException e) {
                e.printStackTrace();
            }
            return null;
        }
        @Override
        protected void onPostExecute(Void listaTuits)
        {
            adapter.notifyDataSetChanged();
        }
    }


}
