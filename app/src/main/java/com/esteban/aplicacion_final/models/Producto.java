package com.esteban.aplicacion_final.models;

import android.net.Uri;

public class Producto {
    private String nombre;
    private String descripcion;
    private Integer precio;
    private Integer cantidad;
    private String nombreAlmacen;

    public Producto() {

    }

    public Producto(String nombre, String descripcion, Integer precio, Integer cantidad, String nombreAlmacen) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.precio = precio;
        this.cantidad = cantidad;
        this.nombreAlmacen = nombreAlmacen;
    }
    public String getNombre() {
        return nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public Integer getPrecio() {
        return precio;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public String getNombreAlmacen() {
        return nombreAlmacen;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public void setPrecio(Integer precio) {
        this.precio = precio;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public void setNombreAlmacen(String nombreAlmacen) {
        this.nombreAlmacen = nombreAlmacen;
    }
}
