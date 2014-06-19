package com.rds.rodalies;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by jesus on 08/02/14.
 */
public class LineasPreferenciasAdapter extends ArrayAdapter<LineaPreferencias>{
    private List<LineaPreferencias> lineas;
    private Context ctx;
    private final boolean[] mCheckedState;

    ViewHolder holder = null;

    public LineasPreferenciasAdapter(Context context, int resource, List<LineaPreferencias> objects) {
        super(context, resource, objects);
        this.ctx = context;
        this.lineas = objects;
        mCheckedState = new boolean[objects.size()];

    }

    static class ViewHolder {
        ImageView iconoLinea;
        TextView nombre_linea;
        TextView paradas;
        CheckBox seleccionado;
    }

    public View getView(final int position, View convertView, ViewGroup parent)
    {
        View view = convertView;

        if (view == null)
        {
            LayoutInflater inflater = ((Activity) this.ctx).getLayoutInflater();
            view = inflater.inflate(R.layout.lineas_preferencias_adapter, null);

            holder = new ViewHolder();


            holder.iconoLinea = (ImageView) view.findViewById(R.id.iconoLinea);
            holder.nombre_linea = (TextView) view.findViewById(R.id.nombreLineaPreferencias);
            holder.paradas = (TextView) view.findViewById(R.id.paradas);
            holder.seleccionado = (CheckBox) view.findViewById(R.id.checkbox);

            view.setTag(holder);
        }
        else
        {
            holder = (ViewHolder) view.getTag();
        }

        LineaPreferencias linea = getItem(position);
        if (linea != null)
        {
            if (holder.nombre_linea != null && holder.paradas != null && holder.seleccionado != null && holder.iconoLinea != null)
            {
                String uri = "drawable/" + linea.getIcono();
                int imageResource = ctx.getResources().getIdentifier(uri, null, ctx.getPackageName());
                Drawable image = ctx.getResources().getDrawable(imageResource);

                holder.iconoLinea.setImageDrawable(image);
                holder.nombre_linea.setText(linea.getNombre());
                holder.paradas.setText(linea.getParadas());
                holder.seleccionado.setChecked( GuardarPreferencias.checksPrefencias[position]);

            }
        }

        if (GuardarPreferencias.checksPrefencias[position])
            holder.seleccionado.setChecked(true);
        else
            holder.seleccionado.setChecked(false);

        holder.seleccionado.setOnClickListener(new View.OnClickListener() {

            @Override

            public void onClick(View v) {

                if (((CheckBox) v).isChecked()) {
                    GuardarPreferencias.checksPrefencias[position] = true;
                    GuardarPreferencias.activaSubmit();

                }else{
                    GuardarPreferencias.checksPrefencias[position] = false;
                    GuardarPreferencias.activaSubmit();
                }
            }
        });
        return view;
    }
}
