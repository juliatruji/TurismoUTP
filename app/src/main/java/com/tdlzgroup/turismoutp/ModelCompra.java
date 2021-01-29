package com.tdlzgroup.turismoutp;

import java.io.Serializable;
import java.util.Date;

public class ModelCompra implements Serializable {

    private String idUsuario;
    private String nombre;
    private String descripcion;
    private String url;
    private double latitud;
    private double longitud;

    private String idCompra;
    private String idPaquete;
    private Date fechaCompra;
    private Date fechaViaje;
    private int cantAdulto;
    private int cantNino;
    private String opcion;
    private int precioTotal;

    public ModelCompra() {
    }

    public String getIdPaquete() {
        return idPaquete;
    }

    public void setIdPaquete(String idPaquete) {
        this.idPaquete = idPaquete;
    }

    public String getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(String idUsuario) {
        this.idUsuario = idUsuario;
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

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public double getLatitud() {
        return latitud;
    }

    public void setLatitud(double latitud) {
        this.latitud = latitud;
    }

    public double getLongitud() {
        return longitud;
    }

    public void setLongitud(double longitud) {
        this.longitud = longitud;
    }

    public String getIdCompra() {
        return idCompra;
    }

    public void setIdCompra(String idCompra) {
        this.idCompra = idCompra;
    }

    public Date getFechaCompra() {
        return fechaCompra;
    }

    public void setFechaCompra(Date fechaCompra) {
        this.fechaCompra = fechaCompra;
    }

    public Date getFechaViaje() {
        return fechaViaje;
    }

    public void setFechaViaje(Date fechaViaje) {
        this.fechaViaje = fechaViaje;
    }

    public int getCantAdulto() {
        return cantAdulto;
    }

    public void setCantAdulto(int cantAdulto) {
        this.cantAdulto = cantAdulto;
    }

    public int getCantNino() {
        return cantNino;
    }

    public void setCantNino(int cantNino) {
        this.cantNino = cantNino;
    }

    public String getOpcion() {
        return opcion;
    }

    public void setOpcion(String opcion) {
        this.opcion = opcion;
    }

    public int getPrecioTotal() {
        return precioTotal;
    }

    public void setPrecioTotal(int precioTotal) {
        this.precioTotal = precioTotal;
    }
}
