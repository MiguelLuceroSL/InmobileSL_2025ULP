package com.miguel.inmobile.modelo;

import java.io.Serializable;

public class Inmueble implements Serializable {
    private int idInmueble;
    private String direccion;
    private String tipo;
    private String uso;
    private int ambientes;
    private double superficie;
    private double latitud;

    private double valor;
    private String imagen;
    private boolean disponible;
    private double longitud;
    private int idPropietario;
    private Propietario duenio;

    public Inmueble() {
    }

    public Inmueble(int idInmueble, String direccion, String tipo, String uso, int ambientes, double superficie, double latitud, double valor, boolean disponible, String imagen, double longitud, int idPropietario, Propietario duenio) {
        this.idInmueble = idInmueble;
        this.direccion = direccion;
        this.tipo = tipo;
        this.uso = uso;
        this.ambientes = ambientes;
        this.superficie = superficie;
        this.latitud = latitud;
        this.valor = valor;
        this.disponible = disponible;
        this.imagen = imagen;
        this.longitud = longitud;
        this.idPropietario = idPropietario;
        this.duenio = duenio;
    }

    public int getIdInmueble() {
        return idInmueble;
    }

    public void setIdInmueble(int idInmueble) {
        this.idInmueble = idInmueble;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getUso() {
        return uso;
    }

    public void setUso(String uso) {
        this.uso = uso;
    }

    public int getAmbientes() {
        return ambientes;
    }

    public void setAmbientes(int ambientes) {
        this.ambientes = ambientes;
    }

    public double getSuperficie() {
        return superficie;
    }

    public void setSuperficie(double superficie) {
        this.superficie = superficie;
    }

    public double getLatitud() {
        return latitud;
    }

    public void setLatitud(double latitud) {
        this.latitud = latitud;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public double getLongitud() {
        return longitud;
    }

    public void setLongitud(double longitud) {
        this.longitud = longitud;
    }

    public boolean isDisponible() {
        return disponible;
    }

    public void setDisponible(boolean disponible) {
        this.disponible = disponible;
    }

    public int getIdPropietario() {
        return idPropietario;
    }

    public void setIdPropietario(int idPropietario) {
        this.idPropietario = idPropietario;
    }

    public Propietario getDuenio() {
        return duenio;
    }

    public void setDuenio(Propietario duenio) {
        this.duenio = duenio;
    }

    @Override
    public String toString() {
        return "Inmueble{" +
                "idInmueble=" + idInmueble +
                ", direccion='" + direccion + '\'' +
                ", tipo='" + tipo + '\'' +
                ", uso='" + uso + '\'' +
                ", ambientes=" + ambientes +
                ", superficie=" + superficie +
                ", latitud=" + latitud +
                ", valor=" + valor +
                ", imagen='" + imagen + '\'' +
                ", disponible=" + disponible +
                ", longitud=" + longitud +
                ", idPropietario=" + idPropietario +
                ", duenio=" + duenio +
                '}';
    }
}