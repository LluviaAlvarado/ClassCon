package com.example.classcon.classcon;

import java.util.ArrayList;

/**
 * Created by speed on 26/03/2017.
 */

public class Clase {
    private String fecha;
    private ArrayList<Tema> temas;
    private ArrayList<Material> materiales;
    private String nombre;
    private String descrp;

    public Clase(String fecha, String nombre, String desc, ArrayList<Tema> t, ArrayList<Material> m) {
        setFecha(fecha);
        setNombre(nombre);
        setDescrp(desc);
        setTemas(t);
        setMateriales(m);
    }
    public String getFecha() {
        return fecha;
    }
    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public ArrayList<Tema> getTemas() {
        return temas;
    }

    public void setTemas(ArrayList<Tema> temas) {
        this.temas = temas;
    }

    public ArrayList<Material> getMateriales() {
        return materiales;
    }

    public void setMateriales(ArrayList<Material> materiales) {
        this.materiales = materiales;
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
}
