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
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;

public class GuardarPreferencias extends Activity {
	
	private ListView myList;
	private Spinner principal;
	private String lineaPrincipal;
	private int lineaPrincipalInt;
	
	private SharedPreferences sharedRodalies;
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.guardar_preferencias);
		
		sharedRodalies= getSharedPreferences(Constants.RODA_PREFERENCES, Context.MODE_PRIVATE);
		
		//spinner principal
		myList = (ListView)findViewById(R.id.list);
        principal = (Spinner)findViewById(R.id.Spinner_Lineas1);
        //si te sharedpreferences carreguem el resultat
        
        
        if (sharedRodalies.contains(Constants.LINEA_PRINCIPAL)) {
			lineaPrincipalInt = sharedRodalies.getInt(Constants.LINEA_PRINCIPAL, -1);
			
			principal.setSelection(lineaPrincipalInt);
		}
        
        //lista secundaria
        String[] arrayLineas = getResources().getStringArray(R.array.lineas);
          
        //modificar lisContent por la lista de rodalies
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_multiple_choice,
        		arrayLineas);
        
        myList.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        myList.setAdapter(adapter);
        
        for (int i=0;i<13;i++) {

        	 //per cada element de la llista hem de buscar si existeix a les shared preferences y marcarlo com yes
        	 Boolean esun = esUnDells(i);
        	     	//Log.e("Rodalies", "es un dells:"+i);
            if (esun) {
               // Log.e("Rodalies", "YES");
            	myList.setItemChecked(i, true);
            }
        }
	}
	
	public void guardar(View v){
		//guarda preferencias
		 String secundarioSelected = "";

         int cntChoice = myList.getCount();
         String principalSelected = principal.getSelectedItem().toString();
         
         int principalSelectedInt = principal.getSelectedItemPosition(); 
         
         Editor editor = sharedRodalies.edit();
         editor.clear();
         editor.commit();
         
        // Log.e("Rodalies", "seleccionat Principal->"+principalSelectedInt);
         editor.putInt(Constants.LINEA_PRINCIPAL, principalSelectedInt);
         
         SparseBooleanArray sparseBooleanArray = myList.getCheckedItemPositions();
         int contador = 1;
         for(int i = 0; i < cntChoice; i++){
             if(sparseBooleanArray.get(i)) {
            	 secundarioSelected += myList.getItemAtPosition(i).toString() + "\n";
            	 //secundarioSelected += i + " \n";
            	 switch(contador){
            	 	case 1:
            	 		//editor.putString(Constants.LINEA_SECUNDARIA1, myList.getItemAtPosition(i).toString());
            	 		editor.putInt(Constants.LINEA_SECUNDARIA1, i);
            	 		break;
            	 	
             		case 2:
             			//editor.putString(Constants.LINEA_SECUNDARIA2, myList.getItemAtPosition(i).toString());
             			editor.putInt(Constants.LINEA_SECUNDARIA2, i);
             			break;
			        case 3:
			    		//editor.putString(Constants.LINEA_SECUNDARIA3, myList.getItemAtPosition(i).toString());
			        	editor.putInt(Constants.LINEA_SECUNDARIA3, i); 
			        	break;
			        case 4:
            	 		//editor.putString(Constants.LINEA_SECUNDARIA4, myList.getItemAtPosition(i).toString());
			        	editor.putInt(Constants.LINEA_SECUNDARIA4, i);
			        	break;
            	 	
             		case 5:
             			//editor.putString(Constants.LINEA_SECUNDARIA5, myList.getItemAtPosition(i).toString());
             			editor.putInt(Constants.LINEA_SECUNDARIA5, i);
             			break;
        	 	
			        case 6:
			    		//editor.putString(Constants.LINEA_SECUNDARIA6, myList.getItemAtPosition(i).toString());
			        	editor.putInt(Constants.LINEA_SECUNDARIA6, i); 
			        	break;
			        case 7:
            	 		//editor.putString(Constants.LINEA_SECUNDARIA7, myList.getItemAtPosition(i).toString());
			        	editor.putInt(Constants.LINEA_SECUNDARIA7, i);
			        	break;
            	 	
             		case 8:
             			//editor.putString(Constants.LINEA_SECUNDARIA8, myList.getItemAtPosition(i).toString());
             			editor.putInt(Constants.LINEA_SECUNDARIA8, i);
             			break;
        	 	
			        case 9:
			    		//editor.putString(Constants.LINEA_SECUNDARIA9, myList.getItemAtPosition(i).toString());
			        	editor.putInt(Constants.LINEA_SECUNDARIA9, i); 
			        	break;
			        case 10:
            	 		//editor.putString(Constants.LINEA_SECUNDARIA10, myList.getItemAtPosition(i).toString());
			        	editor.putInt(Constants.LINEA_SECUNDARIA1, i);
            	 		break;
            	 	
             		case 11:
             			//editor.putString(Constants.LINEA_SECUNDARIA11, myList.getItemAtPosition(i).toString());
             			editor.putInt(Constants.LINEA_SECUNDARIA11, i);
             			break;
        	 	
			        case 12:
			    		 //editor.putString(Constants.LINEA_SECUNDARIA12, myList.getItemAtPosition(i).toString());
			    		 editor.putInt(Constants.LINEA_SECUNDARIA12, i);
			    		 break;

			        case 13:
            	 		//editor.putString(Constants.LINEA_SECUNDARIA13, myList.getItemAtPosition(i).toString());
            	 		editor.putInt(Constants.LINEA_SECUNDARIA13, i);
            	 		break;
            	 }
            	 
            	 contador++;
             }
             
         }
       editor.putInt(Constants.LINEAS_TOTAL, contador);
       editor.commit();

        //tornar a la activity principal
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

}
