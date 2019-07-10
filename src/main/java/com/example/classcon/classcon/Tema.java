package com.example.classcon.classcon;

/**
 * Created by speed on 25/03/2017.
 */

public class Tema {
    private String idMateria;
    private String nombre;
    private String descripcion;

    Tema(String id, String nombre, String descripcion){
        setIdMateria(id);
        setNombre(nombre);
        setDescripcion(descripcion);
    }

    public void setIdMateria(String idMateria) {
        this.idMateria = idMateria;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getIdMateria(){
        return this.idMateria;
    }
    public String getNombre(){
        return this.nombre;
    }
    public String getDescripcion(){
        return this.descripcion;
    }
}
