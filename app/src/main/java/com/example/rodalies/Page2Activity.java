package com.example.rodalies;

import android.os.Bundle;
import android.app.Activity;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class Page2Activity extends Fragment {
	private View myFragmentView;
	private TextView textP;
	private TextView textS;
	private String lineasS = "";

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    	myFragmentView = inflater.inflate(R.layout.activity_page2, container, false);
//Log.e("Rodalies", getArguments().getString(Constants.LINEA_PRINCIPAL));
    	
       	textP = (TextView) myFragmentView.findViewById(R.id.textP);
       	textS = (TextView) myFragmentView.findViewById(R.id.textS);
       	
    	textP.setText("Linea principal: "+getArguments().getInt(Constants.LINEA_PRINCIPAL));
    	
    	if( getArguments().containsKey(Constants.LINEA_SECUNDARIA1) ){
    		lineasS += " "+getArguments().getInt(Constants.LINEA_SECUNDARIA1);
    	}
    	if( getArguments().containsKey(Constants.LINEA_SECUNDARIA2) ){
    		lineasS += " "+getArguments().getInt(Constants.LINEA_SECUNDARIA2);
    	}
    	if( getArguments().containsKey(Constants.LINEA_SECUNDARIA3) ){
    		lineasS += " "+getArguments().getInt(Constants.LINEA_SECUNDARIA3);
    	}
    	if( getArguments().containsKey(Constants.LINEA_SECUNDARIA4) ){
    		lineasS += " "+getArguments().getInt(Constants.LINEA_SECUNDARIA4);
    	}
    	if( getArguments().containsKey(Constants.LINEA_SECUNDARIA5) ){
    		lineasS += " "+getArguments().getInt(Constants.LINEA_SECUNDARIA5);
    	}
    	if( getArguments().containsKey(Constants.LINEA_SECUNDARIA6) ){
    		lineasS += " "+getArguments().getInt(Constants.LINEA_SECUNDARIA6);
    	}
    	if( getArguments().containsKey(Constants.LINEA_SECUNDARIA7) ){
    		lineasS += " "+getArguments().getInt(Constants.LINEA_SECUNDARIA7);
    	}
    	if( getArguments().containsKey(Constants.LINEA_SECUNDARIA8) ){
    		lineasS += " "+getArguments().getInt(Constants.LINEA_SECUNDARIA8);
    	}
    	if( getArguments().containsKey(Constants.LINEA_SECUNDARIA9) ){
    		lineasS += " "+getArguments().getInt(Constants.LINEA_SECUNDARIA9);
    	}
    	if( getArguments().containsKey(Constants.LINEA_SECUNDARIA10) ){
    		lineasS += " "+getArguments().getInt(Constants.LINEA_SECUNDARIA10);
    	}
    	if( getArguments().containsKey(Constants.LINEA_SECUNDARIA11) ){
    		lineasS += " "+getArguments().getInt(Constants.LINEA_SECUNDARIA11);
    	}
    	
    	if( getArguments().containsKey(Constants.LINEA_SECUNDARIA12) ){
    		lineasS += " "+getArguments().getInt(Constants.LINEA_SECUNDARIA12);
    	}


    	if( getArguments().containsKey(Constants.LINEA_SECUNDARIA13) ){
    		lineasS += " "+getArguments().getInt(Constants.LINEA_SECUNDARIA13);
    	}
        /*
    	if( getArguments().containsKey(Constants.LINEA_SECUNDARIA14) ){
    		lineasS += " "+getArguments().getInt(Constants.LINEA_SECUNDARIA14);
    	}
    	if( getArguments().containsKey(Constants.LINEA_SECUNDARIA15) ){
    		lineasS += " "+getArguments().getInt(Constants.LINEA_SECUNDARIA15);
    	}
    	if( getArguments().containsKey(Constants.LINEA_SECUNDARIA16) ){
    		lineasS += " "+getArguments().getInt(Constants.LINEA_SECUNDARIA16);
    	}
    	*/
    	
    	textS.setText("Lineas secundarias: "+lineasS );

		return myFragmentView;
	}	

}
