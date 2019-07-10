package com.example.classcon.classcon;

/**
 * Created by Lluvia on 15/4/2017.
 */

public class DatosPersonales {
    private String nomCompleto;
    private String codigo;
    private String departamento;
    private int id;

    public DatosPersonales(String nomCompleto, String codigo, String departamento, int id) {
        this.nomCompleto = nomCompleto;
        this.codigo = codigo;
        this.departamento = departamento;
        this.id = id;
    }

    public String getNomCompleto() {
        return nomCompleto;
    }

    public void setNomCompleto(String nomCompleto) {
        this.nomCompleto = nomCompleto;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getDepartamento() {
        return departamento;
    }

    public void setDepartamento(String departamento) {
        this.departamento = departamento;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
