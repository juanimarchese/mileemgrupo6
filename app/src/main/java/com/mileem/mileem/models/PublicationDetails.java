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
}
