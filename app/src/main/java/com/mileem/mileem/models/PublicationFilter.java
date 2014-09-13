package com.mileem.mileem.models;

/**
 * Created by ramirodiaz on 13/09/14.
 */
public class PublicationFilter {
    int[] neighborhoods;
    int[] propertyTypes;
    int[] operationTypes;
    int[] environments;
    int minPrice = 0;
    int maxPrice = 0;
    int minSize = 0;
    int minCoveredSize = 0;
    int minPublishDate = 0;

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

    public int getMinCoveredSize() {
        return minCoveredSize;
    }

    public void setMinCoveredSize(int minCoveredSize) {
        this.minCoveredSize = minCoveredSize;
    }

    public int getMinPublishDate() {
        return minPublishDate;
    }

    public void setMinPublishDate(int minPublishDate) {
        this.minPublishDate = minPublishDate;
    }
}


