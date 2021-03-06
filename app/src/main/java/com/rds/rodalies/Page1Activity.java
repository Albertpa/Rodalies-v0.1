package com.rds.rodalies;

import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ListView;
import android.widget.TextView;

import com.rds.rodalies.Linea.Linea;
import com.rds.rodalies.Twitter.ConfiguradorTwitter;
import com.rds.rodalies.Twitter.Tuit;
import com.rds.rodalies.Twitter.TuitsAdapter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.Twitter;
import twitter4j.TwitterException;

public class Page1Activity extends Fragment implements SwipeRefreshLayout.OnRefreshListener {
    private View myFragmentView;
    private TextView textoPrincipal;

    private int codigoLinea;

    private Boolean estaActualizandoEstado = false;
    private Boolean estaActualizandoTuis = false;

    public Linea linea;
    public String avuiString;
    public String ahirString;

    private TuitsAdapter adapter;
    private ListView listaTuits;
    List<Tuit> lista_Tuits = new ArrayList<Tuit>();

    private SwipeRefreshLayout refreshLayout; //Control de la view para poder detectar pull to refresh

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        myFragmentView = inflater.inflate(R.layout.activity_page1, container, false);

        listaTuits = (ListView) myFragmentView.findViewById(R.id.lista_tuits);
        adapter = new TuitsAdapter(getActivity(), android.R.layout.simple_list_item_1, lista_Tuits);
        listaTuits.setAdapter(adapter);

        textoPrincipal = (TextView) myFragmentView.findViewById(R.id.textP);

        codigoLinea = getArguments().getInt(Constants.LINEA_PARAMETRO);
        crearObjetosLinea(codigoLinea);
        Log.e("Rodalies", "Entra al "+ codigoLinea);

        avuiString = getString(R.string.avui);
        ahirString = getString(R.string.ahir);

        /* Configuración swipe para pull to refresh*/
        refreshLayout = (SwipeRefreshLayout) myFragmentView.findViewById(R.id.swipe_container);
        refreshLayout.setOnRefreshListener(this);
        refreshLayout. setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);


        listaTuits.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }
            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                int topRowVerticalPosition =
                        (listaTuits == null || listaTuits.getChildCount() == 0) ?
                                0 : listaTuits.getChildAt(0).getTop();
                refreshLayout.setEnabled(topRowVerticalPosition >= 0);
            }
        });

        return myFragmentView;
    }

    @Override public void onRefresh() {
        refreshLayout.setRefreshing(true); //Empieza animación de refresco.
        actualizar(codigoLinea);
    }

    /* Esta funcion crea un objecto "Linea", le asigna el nombre y la url.
    * El estado inicialmente es un string vacio "", a continuación se hace una llamada
    * al AsyncTask que descargará el estado y se le asignará al objeto */
     public void crearObjetosLinea(int codeLinea) {
        linea = new Linea(Constants.nombreLineas[codeLinea], "", Constants.lineasURL[codeLinea], Constants.usuarioTwitterLineas[codeLinea]);
         if(!estaActualizandoEstado) {
             estaActualizandoEstado = true;
             new EstadoAsyncTask(linea).execute();
         }
         /* se limipia el adapter de los posibles tuits que ya contenga con tal de que no
         aparezcan repetidos cuando se recarge el tab */
         adapter.clear();
         adapter.notifyDataSetChanged();

         if(!estaActualizandoTuis){
             estaActualizandoTuis = true;
             new TuitsAsyncTask(linea).execute();
         }

    }
    public int getCodigoLinea(){
        return codigoLinea;
    }

    public void actualizar(Integer idLinea){
        adapter.notifyDataSetChanged();

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
            if(l.getEstado().equals(""))
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

        String fechaTuit = timeFormatter.format(fecha); //extrae la hora del tuit

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
            fechaTuit += " - " + avuiString;
        }
        else if(cal.get(Calendar.DAY_OF_MONTH) == ayer.get(Calendar.DAY_OF_MONTH) &&  //Ayer
                cal.get(Calendar.MONTH) == ayer.get(Calendar.MONTH) &&
                cal.get(Calendar.YEAR) == ayer.get(Calendar.YEAR))
        {
            fechaTuit += " - " + ahirString;
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
            if(isAdded()) {
                if (response != null) {
                    this.linea.setEstado(response);
                    Log.i("Rodalies", "Linea " + this.linea.getNombre() + ": " + response);
                }

                Page1Activity.this.linea = this.linea;  // ??????????
                estaActualizandoEstado = false;
                if(!estaActualizandoTuis){
                    refreshLayout.setRefreshing(false); //Detener animación de refresco.
                }
                asignarEstado(Page1Activity.this.linea);
            }
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

            final Query query = new Query(linea.getUsuarioTwitter() + " +exclude:retweets"); //Establecer nombre de usuario (sin @) o hashtag
            query.count(Constants.NUMERODETUITS); //Numero maximo de tuits maximo

            try {
                QueryResult result = twitter.search(query);
                for (twitter4j.Status status : result.getTweets()) {
                    String fecha = obtenerFechaTuit(status.getCreatedAt());

                    Tuit tuit = new Tuit("@" + status.getUser().getScreenName(), status.getText(), fecha);
                    lista_Tuits.add(tuit);
                }
                Log.i("Rodalies", "Tweets descargadis");

            } catch (TwitterException e) {
                e.printStackTrace();
            }
            return null;
        }
        @Override
        protected void onPostExecute(Void listaTuits)
        {
            if(isAdded()) {
                estaActualizandoTuis = false;
                if(!estaActualizandoEstado){
                    refreshLayout.setRefreshing(false); //Detener animación de refresco.
                }
                adapter.notifyDataSetChanged();
            }
        }
    }
}
