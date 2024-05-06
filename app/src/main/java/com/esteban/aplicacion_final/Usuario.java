package com.esteban.aplicacion_final;


public class Usuario {
    private String email;
    private String contraseña;

    public Usuario() {
        // Constructor vacío requerido para Firebase
    }

    public Usuario(String email, String contraseña) {
        this.email = email;
        this.contraseña = contraseña;
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
