package com.esteban.aplicacion_final.models;


public class Usuario {
    private String nombre;
    private String email;
    private String contraseña;
    private String usuarioId;

    public Usuario() {
        // Constructor vacío requerido para Firebase
    }

    public Usuario(String nombre, String email, String contraseña, String usuarioId) {
        this.nombre = nombre;
        this.email = email;
        this.contraseña = contraseña;
        this.usuarioId = usuarioId;
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

    public String getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(String usuarioId) {
        this.usuarioId = usuarioId;
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
