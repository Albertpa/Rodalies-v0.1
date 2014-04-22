package com.example.rodalies;


import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.Arrays;
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


        //principal = (Spinner)findViewById(R.id.Spinner_Lineas1);
        //si te sharedpreferences carreguem el resultat
        
        /*
        if (sharedRodalies.contains(Constants.LINEA_PRINCIPAL)) {
			lineaPrincipalInt = sharedRodalies.getInt(Constants.LINEA_PRINCIPAL, -1);

			principal.setSelection(lineaPrincipalInt);
		}
		*/


        //lista secundaria
        String[] arrayLineas = getResources().getStringArray(R.array.lineas);


        List<LineaPreferencias> lineaPref = new ArrayList<LineaPreferencias>();
        crearObjetosLinea();
        //itemCheckeds = new boolean[lista_lineas.size()];

        consultarPreferencias();


        adapter = new LineasPreferenciasAdapter(this, android.R.layout.simple_list_item_multiple_choice, lista_lineas);
        myList.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        myList.setAdapter(adapter);
        clickFila();





/*

        //modificar lisContent por la lista de rodalies
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_multiple_choice,
        		arrayLineas);
        
        myList.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        myList.setAdapter(adapter);
*/



/*
        for (int i=0;i<13;i++) {

        	 //per cada element de la llista hem de buscar si existeix a les shared preferences y marcarlo com yes
        	 Boolean esun = esUnDells(i);
        	     	//Log.e("Rodalies", "es un dells:"+i);
            if (esun) {
                guardar.setEnabled(true);
               // Log.e("Rodalies", "YES");
            	myList.setItemChecked(i, true);
            }
            
        }
*/

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



        // int cntChoice = myList.getCount();

         //String principalSelected = principal.getSelectedItem().toString();
         
         //int principalSelectedInt = principal.getSelectedItemPosition();
         
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
            	 		//editor.putString(Constants.LINEA_SECUNDARIA1, myList.getItemAtPosition(i).toString());
            	 		editor.putInt(Constants.LINEA_SECUNDARIA1, j);
            	 		break;
            	 	
             		case 1:
             			//editor.putString(Constants.LINEA_SECUNDARIA2, myList.getItemAtPosition(i).toString());
             			editor.putInt(Constants.LINEA_SECUNDARIA2, j);
             			break;
			        case 2:
			    		//editor.putString(Constants.LINEA_SECUNDARIA3, myList.getItemAtPosition(i).toString());
			        	editor.putInt(Constants.LINEA_SECUNDARIA3, j);
			        	break;
			        case 3:
            	 		//editor.putString(Constants.LINEA_SECUNDARIA4, myList.getItemAtPosition(i).toString());
			        	editor.putInt(Constants.LINEA_SECUNDARIA4, j);
			        	break;
            	 	
             		case 4:
             			//editor.putString(Constants.LINEA_SECUNDARIA5, myList.getItemAtPosition(i).toString());
             			editor.putInt(Constants.LINEA_SECUNDARIA5, j);
             			break;
        	 	
			        case 5:
			    		//editor.putString(Constants.LINEA_SECUNDARIA6, myList.getItemAtPosition(i).toString());
			        	editor.putInt(Constants.LINEA_SECUNDARIA6, j);
			        	break;
			        case 6:
            	 		//editor.putString(Constants.LINEA_SECUNDARIA7, myList.getItemAtPosition(i).toString());
			        	editor.putInt(Constants.LINEA_SECUNDARIA7, j);
			        	break;
            	 	
             		case 7:
             			//editor.putString(Constants.LINEA_SECUNDARIA8, myList.getItemAtPosition(i).toString());
             			editor.putInt(Constants.LINEA_SECUNDARIA8, j);
             			break;
        	 	
			        case 8:
			    		//editor.putString(Constants.LINEA_SECUNDARIA9, myList.getItemAtPosition(i).toString());
			        	editor.putInt(Constants.LINEA_SECUNDARIA9, j);
			        	break;
			        case 9:
            	 		//editor.putString(Constants.LINEA_SECUNDARIA10, myList.getItemAtPosition(i).toString());
			        	editor.putInt(Constants.LINEA_SECUNDARIA10, j);
            	 		break;
            	 	
             		case 10:
             			//editor.putString(Constants.LINEA_SECUNDARIA11, myList.getItemAtPosition(i).toString());
             			editor.putInt(Constants.LINEA_SECUNDARIA11, j);
             			break;
        	 	
			        case 11:
			    		 //editor.putString(Constants.LINEA_SECUNDARIA12, myList.getItemAtPosition(i).toString());
			    		 editor.putInt(Constants.LINEA_SECUNDARIA12, j);
			    		 break;

			        case 12:
            	 		//editor.putString(Constants.LINEA_SECUNDARIA13, myList.getItemAtPosition(i).toString());
            	 		editor.putInt(Constants.LINEA_SECUNDARIA13, j);
            	 		break;

            	 }
            	 
            	 contador++;

             
         }
       //Log.e("Rodalies", "seleccionat Secundari->"+secundarioSelected);
       editor.putInt(Constants.LINEAS_TOTAL, contador); 
       editor.commit();
       
        
        
        //tornar a la activity principal
       //startActivity(new Intent(GuardarPreferencias.this, MainActivity.class));
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
        /*
        if (sharedRodalies.contains(Constants.LINEA_SECUNDARIA14)) {
			 if(sharedRodalies.getInt(Constants.LINEA_SECUNDARIA14, -1) == i)
				 trobat=true;
		}
        if (sharedRodalies.contains(Constants.LINEA_SECUNDARIA15)) {
			 if(sharedRodalies.getInt(Constants.LINEA_SECUNDARIA15, -1) == i)
				 trobat=true;
		}
        if (sharedRodalies.contains(Constants.LINEA_SECUNDARIA16)) {
			 if(sharedRodalies.getInt(Constants.LINEA_SECUNDARIA16, -1) == i)
				 trobat=true;
		}
        if (sharedRodalies.contains(Constants.LINEA_SECUNDARIA17)) {
			 if(sharedRodalies.getInt(Constants.LINEA_SECUNDARIA17, -1) == i)
				 trobat=true;
		}
        if (sharedRodalies.contains(Constants.LINEA_SECUNDARIA18)) {
			 if(sharedRodalies.getInt(Constants.LINEA_SECUNDARIA18, -1) == i)
				 trobat=true;
		}
        */
        
        
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
                // Log.e("Rodalies", "YES");
                //myList.setItemChecked(i, true);
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
