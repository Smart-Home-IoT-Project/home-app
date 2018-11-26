package com.gti.equipo4.assistedhome.model;

public class Medicine {
    private String nombre;
    private String dias;
    private int cantidad;
    private String foto;

    public Medicine() {}

    public Medicine(String nombre, String dias, int cantidad) {
        this.nombre = nombre;
        this.dias = dias;
        this.cantidad = cantidad;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDias() {
        return dias;
    }

    public void setDias(String dias) {
        this.dias = dias;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    @Override
    public String toString() {
        return "Medicine{" +
                "nombre='" + nombre + '\'' +
                ", dias='" + dias + '\'' +
                '}';
    }
}
