package com.rds.rodalies;

/**
 * Created by jesus on 08/02/14.
 */
public class Tuit {
    private String usuario;
    private String texto;
    private String fecha;

    public Tuit(String usuario, String texto, String fecha)
    {
        this.usuario = usuario;
        this.texto = texto;
        this.fecha = fecha;
    }

    public String getUsuario() {
        return usuario;
    }

    public String getTexto() {
        return texto;
    }

    public String getFecha() {
        return fecha;
    }
}
