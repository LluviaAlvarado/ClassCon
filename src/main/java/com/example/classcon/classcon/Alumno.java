package com.example.classcon.classcon;

import java.util.ArrayList;

/**
 * Created by Lluvia on 10/3/2017.
 */

public class Alumno {
    private String nombre;
    private int equipo;
    private char status;
    private double pAsist;
    private double pEval;
    private String codigo;
    private ArrayList<Actividad> actividades;

    Alumno(String nombre, int equipo,String codigo){
        setNombre(nombre);
        setEquipo(equipo);
        setStatus('D');
        setpAsist(100);
        setpEval(0);
        setCodigo(codigo);
    }


    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public void setpEval(double pEval) {
        this.pEval = pEval;
    }
    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }
    public void setEquipo(int equipo) {
        this.equipo = equipo;
    }
    public void setStatus(char status) {
        this.status = status;
    }
    public void setpAsist(double pAsist) {
        this.pAsist = pAsist;
    }

    public int getEquipo() {
        return equipo;
    }
    public String getCodigo() {
        return codigo;
    }
    public char getStatus() {
        return status;
    }
    public double getpAsist() {
        return pAsist;
    }
    public double getpEval() {
        return pEval;
    }
    public String getNombre(){
        return this.nombre;
    }

    public ArrayList<Actividad> getActividades() {
        return actividades;
    }

    public void setActividades(ArrayList<Actividad> actividades) {
        this.actividades = actividades;
    }
}