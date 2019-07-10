package com.example.classcon.classcon;

/**
 * Created by speed on 25/03/2017.
 */

public class Usuario {
    private String nombre;
    private String password;
    private String correo;
    private int idDP;
    Usuario(String nombre,String password, String correo, int idDp){
        if(!setNombre(nombre)){
            this.nombre = "user";
        }
        if(!setCorreo(correo)){
            this.correo = "xxx@gmail.com";
        }
        if(!setPassword(password)){
            this.password = "password";
        }
        if (!setIdDP(idDp)){
            this.idDP = 0;
        }

    }

    public int getIdDP() {
        return idDP;
    }

    public boolean setIdDP(int idDP) {
        if(idDP >= 0) {
            this.idDP = idDP;
            return true;
        }else{
            return false;
        }
    }

    public boolean setNombre(String nombre) {
        if(nombre.length()>0){
            this.nombre = nombre;
            return true;
        }
        else{
            return false;
        }
    }
    public boolean setPassword(String password) {
        if(password.length()>0){
            this.password = password;
            return true;
        }
        else{
            return false;
        }
    }
    public boolean setCorreo(String correo) {
        if(correo.length()>0){
            this.correo = correo;
            return true;
        }
        else{
            return false;
        }
    }
    public String getNombre(){
        return this.nombre;
    }
    public String getCorreo(){
        return this.correo;
    }

    public String getPassword(){
        return this.password;
    }
}