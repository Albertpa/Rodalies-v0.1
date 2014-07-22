package com.rds.rodalies.Preferencias;

public class LineaPreferencias {


    private String iconoLinea; //icono de la linea
    private String nombre; //Nombre de la linea
    private String paradas; //Estado de la linea


    public LineaPreferencias(String nombre, String paradas, String icono) {
        this.nombre = nombre;
        this.paradas = paradas;
        this.iconoLinea = icono;

    }

    public String getIcono() {
        return iconoLinea;
    }

    public String getNombre() {
        return nombre;
    }

    public String getParadas() {
        return paradas;
    }

}
