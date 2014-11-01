package com.mileem.mileem.models;

/**
 * Created by ramirodiaz on 13/09/14.
 */
public class PublicationFilter {
    private int[] neighborhoods;
    private int[] propertyTypes;
    private int[] operationTypes;
    private int[] environments;
    private int minPrice = 0;
    private int maxPrice = 999999999;
    private int minSize = 0;
    //private int minCoveredSize = 0;
    private int minPublishDate = 0;
    private String currency = "";

    public PublicationFilter(int[] neighborhoods, int[] propertyTypes, int[] operationTypes, int[] environments) {
        this.neighborhoods = neighborhoods;
        this.propertyTypes = propertyTypes;
        this.operationTypes = operationTypes;
        this.environments = environments;
    }

    public PublicationFilter(int[] neighborhoods, int[] propertyTypes, int[] operationTypes, int[] environments, Long minPrice, Long maxPrice, Integer minSize, Integer minPublishDate, String currency) {
        this.neighborhoods = neighborhoods;
        this.propertyTypes = propertyTypes;
        this.operationTypes = operationTypes;
        this.environments = environments;
        if(minPrice != null)
            this.minPrice = minPrice.intValue();
        if(maxPrice != null)
            this.maxPrice = maxPrice.intValue();
        if(minSize != null)
            this.minSize = minSize;
        if(minPublishDate != null)
            this.minPublishDate = minPublishDate;
        if(currency != null && !currency.isEmpty() && !currency.equals("Todas"))
            this.currency = currency;
    }

    public void setMinPrice(int minPrice) {
        this.minPrice = minPrice;
    }

    public void setMaxPrice(int maxPrice) {
        this.maxPrice = maxPrice;
    }

    public int[] getNeighborhoods() {
        return neighborhoods;
    }

    public void setNeighborhoods(int[] neighborhoods) {
        this.neighborhoods = neighborhoods;
    }

    public int[] getPropertyTypes() {
        return propertyTypes;
    }

    public void setPropertyTypes(int[] propertyTypes) {
        this.propertyTypes = propertyTypes;
    }

    public int[] getOperationTypes() {
        return operationTypes;
    }

    public void setOperationTypes(int[] operationTypes) {
        this.operationTypes = operationTypes;
    }

    public int[] getEnvironments() {
        return environments;
    }

    public void setEnvironments(int[] environments) {
        this.environments = environments;
    }

    public int getMinPrice() {
        return minPrice;
    }

    public void setPrice(int minPrice, int maxPrice) {
        this.minPrice = minPrice;
        this.maxPrice = maxPrice;
    }

    public int getMaxPrice() {
        return maxPrice;
    }

    public int getMinSize() {
        return minSize;
    }

    public void setMinSize(int minSize) {
        this.minSize = minSize;
    }

    public int getMinPublishDate() {
        return minPublishDate;
    }

    public void setMinPublishDate(int minPublishDate) {
        this.minPublishDate = minPublishDate;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }
}


