package com.example.classcon.classcon;

import java.util.ArrayList;

/**
 * Created by speed on 26/03/2017.
 */

public class Seccion {
    private ArrayList<Alumno> alumnos;
    private String id;
    private String horaInicio;
    private String horaFin;
    private String diasClase;
    private String idMat;
    private String cicloEsc;
    private ArrayList<Criterio> criterios;
    private ArrayList<Clase> clases;

    public Seccion(String id, String horaInicio, String horaFin, String diasClase, String ciclo, String idMat){
        this.id = id;
        this.horaInicio = horaInicio;
        this.horaFin = horaFin;
        this.diasClase = diasClase;
        this.idMat = idMat;
        this.cicloEsc = ciclo;
    }

    public ArrayList<Alumno> getAlumnos() {
        return alumnos;
    }

    public void setAlumnos(ArrayList<Alumno> alumnos) {
        this.alumnos = alumnos;
    }

    public String getIdMat() {
        return idMat;
    }

    public String getCicloEsc() {
        return cicloEsc;
    }

    public void setCicloEsc(String cicloEsc) {
        this.cicloEsc = cicloEsc;
    }

    public void setIdUser(String idMat) {
        this.idMat = idMat;
    }

    public ArrayList<Criterio> getCriterios() {
        return criterios;
    }

    public void setCriterios(ArrayList<Criterio> criterios) {
        this.criterios = criterios;
    }

    public ArrayList<Clase> getClases() {
        return clases;
    }

    public void setClases(ArrayList<Clase> clases) {
        this.clases = clases;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getHoraInicio() {
        return horaInicio;
    }

    public void setHoraInicio(String horaInicio) {
        this.horaInicio = horaInicio;
    }

    public String getHoraFin() {
        return horaFin;
    }

    public void setHoraFin(String horaFin) {
        this.horaFin = horaFin;
    }

    public String getDiasClase() {
        return diasClase;
    }

    public void setDiasClase(String diasClase) {
        this.diasClase = diasClase;
    }
}