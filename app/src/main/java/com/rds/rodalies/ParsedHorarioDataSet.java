package com.rds.rodalies;

/**
 * Created by albert on 03/07/14.
 * Basada en la app de * Jon Segador
 **/

public class ParsedHorarioDataSet {

    private String hsalida = null;
    private String hllegada = null;
    private String htiempo = null;
    private String hlineasalida = null;
    private String htransbordo = null;
    private String htransbordolinea = null;

    public String getHoraSalida() {
        return hsalida;
    }
    public void setHoraSalida(String hsalida) {
        this.hsalida = hsalida;
    }

    public String getHoraLlegada() {
        return hllegada;
    }
    public void setHoraLlegada(String hllegada) {
        this.hllegada = hllegada;
    }

    public String getTiempo() {
        return htiempo;
    }
    public void setTiempo(String htiempo) {
        this.htiempo = htiempo;
    }

    public String getLineaSalida() {
        return hlineasalida;
    }
    public void setLineaSalida(String hlineasalida) {
        this.hlineasalida = hlineasalida;
    }

    public String getTransbordo() {
        return htransbordo;
    }
    public void setTransbordo(String htransbordo) {
        this.htransbordo = htransbordo;
    }

    public String getTransbordoLinea() {
        return htransbordolinea;
    }
    public void setTransbordoLinea(String htransbordolinea) {
        this.htransbordolinea = htransbordolinea;
    }

    public String toString(){
        return "hsalida = " + this.hsalida + "\n" + "hllegada = " + this.hllegada;

    }
}
