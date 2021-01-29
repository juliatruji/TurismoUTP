package com.tdlzgroup.turismoutp;

import java.io.Serializable;

public class ModelOpcion implements Serializable {
    private String nombre;
    private int precioAdulto;
    private int precioNino;

    public ModelOpcion() {
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getPrecioAdulto() {
        return precioAdulto;
    }

    public void setPrecioAdulto(int precioAdulto) {
        this.precioAdulto = precioAdulto;
    }

    public int getPrecioNino() {
        return precioNino;
    }

    public void setPrecioNino(int precioNino) {
        this.precioNino = precioNino;
    }
}
