package com.example.classcon.classcon;

import java.util.ArrayList;

/**
 * Created by Lluvia on 29/3/2017.
 */

public class Actividad {
    private String Tipo;
    private String nombre;
    private String descrp;
    private double pt;
    private ArrayList<Tema> temas;
    private String fecha;

    public Actividad(String fecha, String tipo, String nombre, String descrp, double pt, ArrayList<Tema> temas) {
        Tipo = tipo;
        this.nombre = nombre;
        this.descrp = descrp;
        this.pt = pt;
        this.temas = temas;
        this.fecha = fecha;
    }

    public String getTipo() {
        return Tipo;
    }

    public void setTipo(String tipo) {
        Tipo = tipo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescrp() {
        return descrp;
    }

    public void setDescrp(String descrp) {
        this.descrp = descrp;
    }

    public double getPt() {
        return pt;
    }

    public void setPt(double pt) {
        this.pt = pt;
    }

    public ArrayList<Tema> getTemas() {
        return temas;
    }

    public void setTemas(ArrayList<Tema> temas) {
        this.temas = temas;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }
}
