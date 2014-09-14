package com.mileem.mileem.models;

/**
 * Created by Juan-Asus on 09/09/2014.
 */
public class PublicationDetails {
    private int id;
    private String title;
    private int price;
    private String currency;
    private String address;
    private int size;
    private int coveredSize;
    private IdName environment;
    private int priority;
    private String[] pictures;
    private Video video;

    //Deprecated
    //Todo Estos atributos deben ser borrados cuando se saquen las responses moqueadas.
    //Utilizar los de arribas (Borrar también los setters y getters)
    String direccion;
    String thumbnailUrl;
    String m2;
    String cantAmbientes;
    String precio;
    private TipoPublicacion tipo;

    public PublicationDetails() {
    }

    public PublicationDetails(String thumbnailUrl, String direccion, String m2, String cantAmbientes, String precio) {
        this.thumbnailUrl = thumbnailUrl;
        this.direccion = direccion;
        this.m2 = m2;
        this.cantAmbientes = cantAmbientes;
        this.precio = precio;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
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

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getCoveredSize() {
        return coveredSize;
    }

    public void setCoveredSize(int coveredSize) {
        this.coveredSize = coveredSize;
    }

    public IdName getEnvironment() {
        return environment;
    }

    public void setEnvironment(IdName environment) {
        this.environment = environment;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
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

    public TipoPublicacion getTipo() {
        return tipo;
    }
}
