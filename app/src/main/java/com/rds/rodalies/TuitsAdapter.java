package com.rds.rodalies;

import android.app.Activity;
import android.content.Context;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

/**
 * Created by jesus on 08/02/14.
 */
public class TuitsAdapter extends ArrayAdapter<Tuit>{
    private List<Tuit> tuits;
    private Context ctx;

    ViewHolder holder = null;

    public TuitsAdapter(Context context, int resource, List<Tuit> objects) {
        super(context, resource, objects);
        this.ctx = context;
        this.tuits = objects;
    }

    static class ViewHolder {
        TextView nombre_usuario;
        TextView texto_tuit;
    }

    public View getView(int position, View convertView, ViewGroup parent)
    {
        View view = convertView;

        if (view == null)
        {
            LayoutInflater inflater = ((Activity) this.ctx).getLayoutInflater();
            view = inflater.inflate(R.layout.tuit_adapter, null);

            holder = new ViewHolder();
            holder.nombre_usuario = (TextView) view.findViewById(R.id.nombre_usuario);
            holder.texto_tuit = (TextView) view.findViewById(R.id.texto_tuit);

            view.setTag(holder);
        }
        else
        {
            holder = (ViewHolder) view.getTag();
        }

        Tuit tuit = getItem(position);
        if (tuit != null)
        {
            if (holder.nombre_usuario != null && holder.texto_tuit != null)
            {
                holder.nombre_usuario.setText(tuit.getUsuario() + " " + tuit.getFecha()); //Asigna el nombre de la linea REVISAR (FECHA)
                holder.texto_tuit.setText(detectarUrls(tuit.getTexto()));
                holder.texto_tuit.setMovementMethod(LinkMovementMethod.getInstance());
            }
        }
        return view;
    }

    private String detectarUrls(String texto) {
        // separete input by spaces ( URLs don't have spaces )
        String [] parts = texto.split("\\s");
        String textoConURLS = texto;

        // Attempt to convert each item into an URL.
        for(String item : parts)
        {
            try
            {
                URL url = new URL(item);
                // If possible then replace with anchor...
                textoConURLS = "<a href=\"" + url + "\">"+ url + "</a> ";
                Log.i("URLS", textoConURLS);
            }
            catch (MalformedURLException e) {
                // If there was an URL that was not it!...
                return texto;
            }
        }
        return textoConURLS;
    }
}
