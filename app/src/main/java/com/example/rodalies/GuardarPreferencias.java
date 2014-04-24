package com.example.rodalies;


import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class GuardarPreferencias extends Activity {
	
	private ListView myList;
	//private Spinner principal;
	private String lineaPrincipal;
	private int lineaPrincipalInt;
	private static Button guardar;
    List<Integer> itemChecked;

    public static boolean[] checksPrefencias;

    private LineasPreferenciasAdapter adapter;
    List<LineaPreferencias> lista_lineas = new ArrayList<LineaPreferencias>();

	private SharedPreferences sharedRodalies;
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.guardar_preferencias);
		
		sharedRodalies= getSharedPreferences(Constants.RODA_PREFERENCES, Context.MODE_PRIVATE);

        guardar = (Button)findViewById(R.id.guardar);
        guardar.setEnabled(false);

		//spinner principal
		myList = (ListView)findViewById(R.id.list);

        //lista secundaria
        String[] arrayLineas = getResources().getStringArray(R.array.lineas);

        List<LineaPreferencias> lineaPref = new ArrayList<LineaPreferencias>();
        crearObjetosLinea();

        consultarPreferencias();

        adapter = new LineasPreferenciasAdapter(this, android.R.layout.simple_list_item_multiple_choice, lista_lineas);
        myList.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        myList.setAdapter(adapter);
        clickFila();
	}
	
	public void guardar(View v){
		//guarda preferencias
		 String secundarioSelected = "";

        itemChecked =
                new ArrayList<Integer>();
        for(int i = 0; i < 13; i++){

            if (checksPrefencias[i] == true) {
                itemChecked.add(i);
            }
        }

         Editor editor = sharedRodalies.edit();
         editor.clear();
         editor.commit();
         
        // Log.e("Rodalies", "seleccionat Principal->"+principalSelectedInt);
         //editor.putInt(Constants.LINEA_PRINCIPAL, principalSelectedInt);
         
         //SparseBooleanArray sparseBooleanArray = myList.getCheckedItemPositions();
         int contador = 0;
        for (int i = 0; i < itemChecked.size(); i++) {
                 int j = itemChecked.get(i);

            	 //secundarioSelected += i + " \n";
            	 switch(contador){
            	 	case 0:
            	 		editor.putInt(Constants.LINEA_SECUNDARIA1, j);
            	 		break;
            	 	
             		case 1:
             			editor.putInt(Constants.LINEA_SECUNDARIA2, j);
             			break;
			        case 2:
			        	editor.putInt(Constants.LINEA_SECUNDARIA3, j);
			        	break;
			        case 3:
			        	editor.putInt(Constants.LINEA_SECUNDARIA4, j);
			        	break;
            	 	
             		case 4:
             			editor.putInt(Constants.LINEA_SECUNDARIA5, j);
             			break;
        	 	
			        case 5:
			        	editor.putInt(Constants.LINEA_SECUNDARIA6, j);
			        	break;
			        case 6:
			        	editor.putInt(Constants.LINEA_SECUNDARIA7, j);
			        	break;
            	 	
             		case 7:
             			editor.putInt(Constants.LINEA_SECUNDARIA8, j);
             			break;
        	 	
			        case 8:
			        	editor.putInt(Constants.LINEA_SECUNDARIA9, j);
			        	break;
			        case 9:
			        	editor.putInt(Constants.LINEA_SECUNDARIA10, j);
            	 		break;
            	 	
             		case 10:
             			editor.putInt(Constants.LINEA_SECUNDARIA11, j);
             			break;
        	 	
			        case 11:
			    		 editor.putInt(Constants.LINEA_SECUNDARIA12, j);
			    		 break;

			        case 12:
            	 		editor.putInt(Constants.LINEA_SECUNDARIA13, j);
            	 		break;

            	 }
            	 
            	 contador++;
         }
       //Log.e("Rodalies", "seleccionat Secundari->"+secundarioSelected);
       editor.putInt(Constants.LINEAS_TOTAL, contador);
       editor.commit();

        finish();

	}
	
	private Boolean esUnDells(int i){
		//tenim que mirar si existeix i a les sharedpreferences
		sharedRodalies= getSharedPreferences(Constants.RODA_PREFERENCES, Context.MODE_PRIVATE);
        Boolean trobat = false;
        
        if (sharedRodalies.contains(Constants.LINEA_SECUNDARIA1)) {
			 if(sharedRodalies.getInt(Constants.LINEA_SECUNDARIA1, -1) == i)
				 trobat=true;
		}
        if (sharedRodalies.contains(Constants.LINEA_SECUNDARIA2)) {
			 if(sharedRodalies.getInt(Constants.LINEA_SECUNDARIA2, -1) == i)
				 trobat=true;
		}
        if (sharedRodalies.contains(Constants.LINEA_SECUNDARIA3)) {
			 if(sharedRodalies.getInt(Constants.LINEA_SECUNDARIA3, -1) == i)
				 trobat=true;
		}
        if (sharedRodalies.contains(Constants.LINEA_SECUNDARIA4)) {
			 if(sharedRodalies.getInt(Constants.LINEA_SECUNDARIA4, -1) == i)
				 trobat=true;
		}
        if (sharedRodalies.contains(Constants.LINEA_SECUNDARIA5)) {
			 if(sharedRodalies.getInt(Constants.LINEA_SECUNDARIA5, -1) == i)
				 trobat=true;
		}
        if (sharedRodalies.contains(Constants.LINEA_SECUNDARIA6)) {
			 if(sharedRodalies.getInt(Constants.LINEA_SECUNDARIA6, -1) == i)
				 trobat=true;
		}
        if (sharedRodalies.contains(Constants.LINEA_SECUNDARIA7)) {
			 if(sharedRodalies.getInt(Constants.LINEA_SECUNDARIA7, -1) == i)
				 trobat=true;
		}
        if (sharedRodalies.contains(Constants.LINEA_SECUNDARIA8)) {
			 if(sharedRodalies.getInt(Constants.LINEA_SECUNDARIA8, -1) == i)
				 trobat=true;
		}
        if (sharedRodalies.contains(Constants.LINEA_SECUNDARIA9)) {
			 if(sharedRodalies.getInt(Constants.LINEA_SECUNDARIA9, -1) == i)
				 trobat=true;
		}
        if (sharedRodalies.contains(Constants.LINEA_SECUNDARIA10)) {
			 if(sharedRodalies.getInt(Constants.LINEA_SECUNDARIA10, -1) == i)
				 trobat=true;
		}
        if (sharedRodalies.contains(Constants.LINEA_SECUNDARIA11)) {
			 if(sharedRodalies.getInt(Constants.LINEA_SECUNDARIA11, -1) == i)
				 trobat=true;
		}
        if (sharedRodalies.contains(Constants.LINEA_SECUNDARIA12)) {
			 if(sharedRodalies.getInt(Constants.LINEA_SECUNDARIA12, -1) == i)
				 trobat=true;
		}
        if (sharedRodalies.contains(Constants.LINEA_SECUNDARIA13)) {
			 if(sharedRodalies.getInt(Constants.LINEA_SECUNDARIA13, -1) == i)
				 trobat=true;
		}

		return trobat;
	}

        //Esta funcion activa o desactiva el submit dependiendo de si hay algun elemento seleccionado

    public static void activaSubmit() {

        Log.e("Rodalies", "entra al activa submit");

        boolean b = false;

        for (boolean value : checksPrefencias) {
            if (value == true) {
                b= true;
                break;
            }
        }

        if (b) {
            guardar.setEnabled(true);
        } else {
            guardar.setEnabled(false);
        }

    }

    /* Esta funcion crea un objecto "Linea", le asigna el nombre y las paradas.
     */
    private void crearObjetosLinea() {

        String[] arrayLineas = getResources().getStringArray(R.array.lineas);

        String[] arrayParadas= getResources().getStringArray(R.array.paradas);

        String[] arrayIconos= getResources().getStringArray(R.array.iconos);

        for(int i = 0; i < 13; i++){
            LineaPreferencias lp = new LineaPreferencias(arrayLineas[i],  arrayParadas[i], arrayIconos[i]);
            lista_lineas.add(lp);
        }
    }

    /* Esta funcion rellena el array de boleanos.
    */
    private void consultarPreferencias() {

        checksPrefencias = new boolean[lista_lineas.size()];

        for (int i=0;i<13;i++) {

            //per cada element de la llista hem de buscar si existeix a les shared preferences y marcarlo com yes
            Boolean esun = esUnDells(i);
            //Log.e("Rodalies", "es un dells:"+i);
            if (esun) {
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
                    GuardarPreferencias.checksPrefencias[position] = false;
                    GuardarPreferencias.activaSubmit();
                }
                else
                {
                    seleccionado.setChecked(true);
                    GuardarPreferencias.checksPrefencias[position] = true;
                    GuardarPreferencias.activaSubmit();
                }
            }
        });
    }
}
