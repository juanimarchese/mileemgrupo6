package com.mileem.mileem.models;

/**
 * Created by ramirodiaz on 13/09/14.
 */
public class PublicationFilter {
    int[] neighborhoods;
    int[] propertyTypes;
    int[] operationTypes;
    int[] environments;
    int minPrice;
    int maxPrice;
    int minSize;
    int minCoveredSize;
    int minPublishDate;

    public PublicationFilter(int[] neighborhoods, int[] propertyTypes, int[] operationTypes, int[] environments) {
        this.neighborhoods = neighborhoods;
        this.propertyTypes = propertyTypes;
        this.operationTypes = operationTypes;
        this.environments = environments;
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

    public Number getMinPrice() {
        return minPrice;
    }

    public void setPrice(int minPrice, int maxPrice) {
        this.minPrice = minPrice;
        this.maxPrice = maxPrice;
    }

    public Number getMaxPrice() {
        return maxPrice;
    }

    public Number getMinSize() {
        return minSize;
    }

    public void setMinSize(int minSize) {
        this.minSize = minSize;
    }

    public Number getMinCoveredSize() {
        return minCoveredSize;
    }

    public void setMinCoveredSize(int minCoveredSize) {
        this.minCoveredSize = minCoveredSize;
    }

    public Number getMinPublishDate() {
        return minPublishDate;
    }

    public void setMinPublishDate(int minPublishDate) {
        this.minPublishDate = minPublishDate;
    }
}


