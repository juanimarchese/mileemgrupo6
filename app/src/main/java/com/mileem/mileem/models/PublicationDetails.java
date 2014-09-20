package com.mileem.mileem.models;

import java.util.ArrayList;

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
    private ArrayList<String> pictures;
    private Video video;
    private int age;
    private String description;
    private float latitude;
    private float longitude;
    private int expenses;
    private IdName operationType;
    private IdName propertyType;
    private IdName publicationType;
    private IdName neighborhood;

    public PublicationDetails() {
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

    public ArrayList<String> getPictures() {
        return pictures;
    }

    public void setPictures(ArrayList<String> pictures) {
        this.pictures = pictures;
    }

    public Video getVideo() {
        return video;
    }

    public void setVideo(Video video) {
        this.video = video;
    }

    public boolean isPremium() {
        return this.getPriority() == 1;
    }

    public boolean isBasic() {
        return this.getPriority() == 2;
    }

    public boolean isFree() {
        return this.getPriority() != 2 && this.getPriority() != 1;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public float getLatitude() {
        return latitude;
    }

    public void setLatitude(int latitude) {
        this.latitude = latitude;
    }

    public float getLongitude() {
        return longitude;
    }

    public void setLongitude(int longitude) {
        this.longitude = longitude;
    }

    public int getExpenses() {
        return expenses;
    }

    public void setExpenses(int expenses) {
        this.expenses = expenses;
    }

    public IdName getOperationType() {
        return operationType;
    }

    public void setOperationType(IdName operationType) {
        this.operationType = operationType;
    }

    public IdName getPropertyType() {
        return propertyType;
    }

    public void setPropertyType(IdName propertyType) {
        this.propertyType = propertyType;
    }

    public IdName getPublicationType() {
        return publicationType;
    }

    public void setPublicationType(IdName publicationType) {
        this.publicationType = publicationType;
    }

    public IdName getNeighborhood() {
        return neighborhood;
    }

    public void setNeighborhood(IdName neighborhood) {
        this.neighborhood = neighborhood;
    }
}
