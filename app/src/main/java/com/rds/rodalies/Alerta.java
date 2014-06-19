package com.rds.rodalies;

public class Alerta {
    private Integer Id;
    private String Hora;
    private String Minutos;
    private String DiasSeleccionados;

    public Alerta(Integer id, String h, String m, String d){
        this.Id = id;
        this.Hora = h;
        this.Minutos = m;
        this.DiasSeleccionados = d;
    }

    public Integer getId() {
        return Id;
    }
}
