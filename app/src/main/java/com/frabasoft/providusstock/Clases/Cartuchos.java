package com.frabasoft.providusstock.Clases;

import java.util.ArrayList;
import java.util.List;

public class Cartuchos {
    private int idCartucho;
    private String modelo;
    private String color;
    private int cantidad;
    private String usuarioModifico;
    private String fechaModificacion;
    private List<Cartuchos> students = new ArrayList<>();

    public int getIdCartucho() {
        return idCartucho;
    }

    public void setIdCartucho(int idCartucho) {
        this.idCartucho = idCartucho;
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

    public Cartuchos() { }

    public Cartuchos(String modelo, String color, int cantidad) {
        this.modelo = modelo;
        this.color = color;
        this.cantidad = cantidad;
    }

    public Cartuchos(int idCartucho, String modelo, String color, String fechaModificacion, int cantidad) {
        this.idCartucho = idCartucho;
        this.modelo = modelo;
        this.color = color;
        this.fechaModificacion = fechaModificacion;
        this.cantidad = cantidad;
    }

    public List<Cartuchos> getAll() {
        return this.students;
    }
}
