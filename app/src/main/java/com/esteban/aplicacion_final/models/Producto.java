package com.esteban.aplicacion_final.models;

public class Producto {
    private String id; // Nuevo campo para almacenar la clave única del producto en la base de datos
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Integer getPrecio() {
        return precio;
    }

    public void setPrecio(Integer precio) {
        this.precio = precio;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public String getNombreAlmacen() {
        return nombreAlmacen;
    }

    public void setNombreAlmacen(String nombreAlmacen) {
        this.nombreAlmacen = nombreAlmacen;
    }


}
