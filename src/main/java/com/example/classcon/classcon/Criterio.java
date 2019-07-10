package com.example.classcon.classcon;

/**
 * Created by speed on 25/03/2017.
 */

public class Criterio {
    private String nombre;
    private double porcentaje;

    Criterio(String nombre, double porcentaje){
        setNombre(nombre);
        setPorcentaje(porcentaje);
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public void setPorcentaje(double porcentaje) {
        this.porcentaje = porcentaje;
    }

    public String getNombre(){
        return this.nombre;
    }
    public double getPorcentaje(){
        return this.porcentaje;
    }
}
