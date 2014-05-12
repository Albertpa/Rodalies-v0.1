package com.rds.rodalies;

/**
 * Created by jesus on 29/01/14.
 * Clase Linea
 */
public class Linea {
    private String nombre; //Nombre de la linea
    private String estado; //Estado de la linea
    private String url; //URL de la linea
    private String usuarioTwitter;

    public Linea(String nombre, String estado, String url, String usuarioTwitter) {
        this.nombre = nombre;
        this.estado = estado;
        this.url = url;
        this.usuarioTwitter = usuarioTwitter;
    }

    public String getNombre() {
        return nombre;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getUrl() {
        return url;
    }

    public String getUsuarioTwitter() {
        return usuarioTwitter;
    }
}