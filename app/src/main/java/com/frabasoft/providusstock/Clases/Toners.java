package com.frabasoft.providusstock.Clases;

public class Toners {
    private int idToner;
    private String modelo;
    private String color;
    private int cantidad;
    private String usuarioModifico;
    private String fechaModificacion;

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

    public String getUsuarioModifico() {
        return usuarioModifico;
    }

    public void setUsuarioModifico(String usuarioModifico) {
        this.usuarioModifico = usuarioModifico;
    }

    public String getFechaModificacion() {
        return fechaModificacion;
    }

    public void setFechaModificacion(String fechaModificacion) {
        this.fechaModificacion = fechaModificacion;
    }

    public Toners() { }

    public Toners(String modelo, String color, int cantidad) {
        this.modelo = modelo;
        this.color = color;
        this.cantidad = cantidad;
    }

    public Toners(int idCartucho, String modelo, String color, String fechaModificacion, int cantidad) {
        this.idToner = idCartucho;
        this.modelo = modelo;
        this.color = color;
        this.fechaModificacion = fechaModificacion;
        this.cantidad = cantidad;
    }
}
