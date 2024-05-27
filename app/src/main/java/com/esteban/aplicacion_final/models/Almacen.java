package com.esteban.aplicacion_final.models;


public class Almacen {
    private String nombre;
    private String email;
    private String contraseña;
    private String almacenId;

    public Almacen() {
        // Constructor vacío requerido para Firebase
    }

    public Almacen(String almacenId, String nombre, String email, String contraseña) {
        this.almacenId = almacenId;
        this.nombre = nombre;
        this.email = email;
        this.contraseña = contraseña;
    }

    public String getAlmacenId() {
        return almacenId;
    }

    public void setAlmacenId(String almacenId) {
        this.almacenId = almacenId;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContraseña() {
        return contraseña;
    }

    public void setContraseña(String contraseña) {
        this.contraseña = contraseña;
    }

}
