package com.example.classcon.classcon;

/**
 * Created by speed on 25/03/2017.
 */

public class Material {
    private int id;
    private String nombre;

    Material(int id, String nombre){
        setId(id);
        setNombre(nombre);
    }
    public void setId(int id) {
        this.id = id;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }
}
