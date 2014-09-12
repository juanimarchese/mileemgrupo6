package com.mileem.mileem.models;

/**
 * Created by Juan-Asus on 09/09/2014.
 */
public class PublicationDetails {
    String thumbnailUrl;
    String direccion;
    String m2;
    String cantAmbientes;
    String precio;
    TipoPublicacion tipo;

    public PublicationDetails() {
    }

    public PublicationDetails(String thumbnailUrl, String direccion, String m2, String cantAmbientes, String precio, TipoPublicacion tipo) {
        this.thumbnailUrl = thumbnailUrl;
        this.direccion = direccion;
        this.m2 = m2;
        this.cantAmbientes = cantAmbientes;
        this.precio = precio;
        this.tipo = tipo;
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    public void setThumbnailUrl(String thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getM2() {
        return m2;
    }

    public void setM2(String m2) {
        this.m2 = m2;
    }

    public String getCantAmbientes() {
        return cantAmbientes;
    }

    public void setCantAmbientes(String cantAmbientes) {
        this.cantAmbientes = cantAmbientes;
    }

    public String getPrecio() {
        return precio;
    }

    public void setPrecio(String precio) {
        this.precio = precio;
    }

    public TipoPublicacion getTipo() {
        return tipo;
    }

    public void setTipo(TipoPublicacion tipo) {
        this.tipo = tipo;
    }
}
