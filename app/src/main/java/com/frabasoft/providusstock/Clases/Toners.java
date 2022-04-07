package com.frabasoft.providusstock.Clases;

public class Toners {
    private int idToner;
    private String modelo;
    private String color;
    private int cantidad;
    private String fechaModificacion;
    private String proveedor;

    public int getIdToner() {
        return idToner;
    }

    public void setIdToner(int idToner) {
        this.idToner = idToner;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public String getFechaModificacion() {
        return fechaModificacion;
    }

    public void setFechaModificacion(String fechaModificacion) {
        this.fechaModificacion = fechaModificacion;
    }

    public String getProveedor() {
        return proveedor;
    }

    public void setProveedor(String proveedor) {
        this.proveedor = proveedor;
    }

    public Toners() { }

    public Toners(int idCartucho, String modelo, String color, int cantidad, String fechaModificacion) {
        this.idToner = idCartucho;
        this.modelo = modelo;
        this.color = color;
        this.cantidad = cantidad;
        this.fechaModificacion = fechaModificacion;
    }
}
