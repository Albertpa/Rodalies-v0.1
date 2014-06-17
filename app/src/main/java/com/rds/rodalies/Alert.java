package com.rds.rodalies;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

/**
 * Created by jesus on 22/07/13.
 */
public class Alert {
    public void alert(String message, Context ctx){
        AlertDialog.Builder alertbox = new AlertDialog.Builder(ctx);
        alertbox.setMessage(message);
        // add a neutral button to the alert box and assign a click listener
        alertbox.setNeutralButton(R.string.aceptar,  new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {

            }
        });
        alertbox.show();
    }
}
