package com.mileem.mileem.models;

/**
 * Created by Juan-Asus on 09/09/2014.
 */
public class PublicationDetails {
    Number id;
    String title;
    Number price;
    String currency;
    String address;
    Number size;
    Number coveredSize;
    IdName environment;
    Number priority;
    String[] pictures;
    Video video;

    //Deprecated
    //Todo Estos atributos deben ser borrados cuando se saquen las responses moqueadas.
    //Utilizar los de arribas (Borrar también los setters y getters)
    String direccion;
    String thumbnailUrl;
    String m2;
    String cantAmbientes;
    String precio;

    public PublicationDetails() {
    }

    public PublicationDetails(String thumbnailUrl, String direccion, String m2, String cantAmbientes, String precio) {
        this.thumbnailUrl = thumbnailUrl;
        this.direccion = direccion;
        this.m2 = m2;
        this.cantAmbientes = cantAmbientes;
        this.precio = precio;
    }

    public Number getId() {
        return id;
    }

    public void setId(Number id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Number getPrice() {
        return price;
    }

    public void setPrice(Number price) {
        this.price = price;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Number getSize() {
        return size;
    }

    public void setSize(Number size) {
        this.size = size;
    }

    public Number getCoveredSize() {
        return coveredSize;
    }

    public void setCoveredSize(Number coveredSize) {
        this.coveredSize = coveredSize;
    }

    public IdName getEnvironment() {
        return environment;
    }

    public void setEnvironment(IdName environment) {
        this.environment = environment;
    }

    public Number getPriority() {
        return priority;
    }

    public void setPriority(Number priority) {
        this.priority = priority;
    }

    public String[] getPictures() {
        return pictures;
    }

    public void setPictures(String[] pictures) {
        this.pictures = pictures;
    }

    public Video getVideo() {
        return video;
    }

    public void setVideo(Video video) {
        this.video = video;
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
}
