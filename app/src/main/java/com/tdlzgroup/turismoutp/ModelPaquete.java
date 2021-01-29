package com.tdlzgroup.turismoutp;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ModelPaquete implements Serializable {

    private String idPaquete;
    private String nombre;
    private String descripcion;
    private String url;
    private double latitud;
    private double longitud;
    private double puntaje;
    private int votos;
    private List<ModelOpcion> opciones;

    public ModelPaquete() {
        opciones = new ArrayList<>();
    }

    public String getIdPaquete() {
        return idPaquete;
    }

    public void setIdPaquete(String idPaquete) {
        this.idPaquete = idPaquete;
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

    public double getPuntaje() {
        return puntaje;
    }

    public void setPuntaje(double puntaje) {
        this.puntaje = puntaje;
    }

    public int getVotos() {
        return votos;
    }

    public void setVotos(int votos) {
        this.votos = votos;
    }

    public List<ModelOpcion> getOpciones() {
        return opciones;
    }

    public void setOpciones(List<ModelOpcion> opciones) {
        this.opciones = opciones;
    }
}
