package com.example.classcon.classcon;

import java.util.ArrayList;

/**
 * Created by speed on 25/03/2017.
 */

public class Materia {
    private String nombre;
    private String clave;
    private ArrayList<Tema> temas;

    Materia(String clave, String nombre){
        setNombre(nombre);
        setClave(clave);
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public void setClave(String clave) {
        this.clave = clave;
    }

    public String getNombre(){
        return this.nombre;
    }
    public String getClave(){
        return this.clave;
    }

    public ArrayList<Tema> getTemas() {
        return temas;
    }

    public void setTemas(ArrayList<Tema> temas) {
        this.temas = temas;
    }
}

