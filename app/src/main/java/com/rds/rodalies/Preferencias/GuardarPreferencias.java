package com.rds.rodalies.Preferencias;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;

import com.rds.rodalies.Constants;
import com.rds.rodalies.R;

import java.util.ArrayList;
import java.util.List;

public class GuardarPreferencias extends Activity {
	
	private ListView myList;
	private static Button guardar;
    List<Integer> itemChecked;

    public static boolean[] checksPrefencias;

    List<LineaPreferencias> lista_lineas = new ArrayList<LineaPreferencias>();

	private SharedPreferences sharedRodalies;
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.guardar_preferencias);
		
		sharedRodalies = getSharedPreferences(Constants.RODA_PREFERENCES, Context.MODE_PRIVATE);

        guardar = (Button)findViewById(R.id.guardar);
        guardar.setEnabled(false);

		//spinner principal
		myList = (ListView)findViewById(R.id.list);

        crearObjetosLinea();
        consultarPreferencias();

        LineasPreferenciasAdapter adapter = new LineasPreferenciasAdapter(this, android.R.layout.simple_list_item_multiple_choice, lista_lineas);
        myList.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        myList.setAdapter(adapter);
        clickFila();
	}
	
	public void guardar(View v){
        itemChecked = new ArrayList<Integer>();
        for(int i = 0; i < Constants.lineaSecundaria.length; i++)
        {
            if (checksPrefencias[i]) {
                itemChecked.add(i);
            }
        }

        Editor editor = sharedRodalies.edit();
        editor.clear();
        editor.commit();

        int contadorLineasSeleccionados = 0;

        for (int i = 0; i < itemChecked.size(); i++) {
            int j = itemChecked.get(i);
            editor.putInt(Constants.lineaSecundaria[i], j);
            contadorLineasSeleccionados++;
        }
        editor.putInt(Constants.LINEAS_TOTAL, contadorLineasSeleccionados);
        editor.commit();

        finish();
	}
	
	private Boolean esUnDells(int i){
		//tenim que mirar si existeix i a les sharedpreferences
		sharedRodalies= getSharedPreferences(Constants.RODA_PREFERENCES, Context.MODE_PRIVATE);
        Boolean trobat = false;

        for (int s = 0; s < Constants.lineaSecundaria.length; s++){
            if(sharedRodalies.contains(Constants.lineaSecundaria[s])){
                if(sharedRodalies.getInt(Constants.lineaSecundaria[s], -1) == i){
                    trobat = true;
                }
            }
        }
		return trobat;
	}

    //Esta funcion activa o desactiva el submit dependiendo de si hay algun elemento seleccionado

    public static void activaSubmit() {
        boolean seHanSeleccionadoLineas = false;

        for (boolean value : checksPrefencias) {
            if(value) {
                seHanSeleccionadoLineas = true;
                break;
            }
        }
        guardar.setEnabled(seHanSeleccionadoLineas);
    }

    /* Esta funcion crea un objecto "Linea", le asigna el nombre y las paradas.
     */
    private void crearObjetosLinea() {
        String[] arrayLineas = getResources().getStringArray(R.array.lineas);
        String[] arrayParadas= getResources().getStringArray(R.array.paradas);
        String[] arrayIconos= getResources().getStringArray(R.array.iconos);

        for(int i = 0; i < Constants.lineaSecundaria.length; i++){
            LineaPreferencias lp = new LineaPreferencias(arrayLineas[i],  arrayParadas[i], arrayIconos[i]);
            lista_lineas.add(lp);
        }
    }

    /* Esta funcion rellena el array de boleanos. */
    private void consultarPreferencias() {

        checksPrefencias = new boolean[lista_lineas.size()];

        for (int i = 0; i < Constants.lineaSecundaria.length; i++) {
            //per cada element de la llista hem de buscar si existeix a les shared preferences y marcarlo com ha marcat
            Boolean marcado = esUnDells(i);
            if (marcado) {
                guardar.setEnabled(true);
                checksPrefencias[i] = true;
            }
        }
    }

    private void clickFila(){
        myList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                CheckBox seleccionado = (CheckBox) view.findViewById(R.id.checkbox);
                if(seleccionado.isChecked())
                {
                    seleccionado.setChecked(false);
                    checksPrefencias[position] = false;
                }
                else
                {
                    seleccionado.setChecked(true);
                    checksPrefencias[position] = true;
                }
                activaSubmit();
            }
        });
    }
}
