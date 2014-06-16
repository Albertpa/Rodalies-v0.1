package com.rds.rodalies;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class NotificacionesSQL extends SQLiteOpenHelper {

    //Sentencia SQL para crear la tabla de notificaciones
    String sqlCreate = "CREATE TABLE Notificaciones (id INTEGER, hora TEXT, minuto TEXT, dias TEXT)";

    public NotificacionesSQL(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //Se ejecuta la sentencia SQL de creación de la tabla
        db.execSQL(sqlCreate);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //Se elimina la versión anterior de la tabla
        db.execSQL("DROP TABLE IF EXISTS Notificaciones");
        //Se crea la nueva versión de la tabla
        db.execSQL(sqlCreate);
    }
}
