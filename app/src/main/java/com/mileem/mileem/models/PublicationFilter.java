package com.mileem.mileem.models;

/**
 * Created by ramirodiaz on 13/09/14.
 */
public class PublicationFilter {
    Number[] neighborhoods;
    Number[] propertyTypes;
    Number[] operationTypes;
    Number[] environments;
    Number minPrice;
    Number maxPrice;
    Number minSize;
    Number minCoveredSize;
    Number minPublishDate;

    public Number[] getNeighborhoods() {
        return neighborhoods;
    }

    public void setNeighborhoods(Number[] neighborhoods) {
        this.neighborhoods = neighborhoods;
    }

    public Number[] getPropertyTypes() {
        return propertyTypes;
    }

    public void setPropertyTypes(Number[] propertyTypes) {
        this.propertyTypes = propertyTypes;
    }

    public Number[] getOperationTypes() {
        return operationTypes;
    }

    public void setOperationTypes(Number[] operationTypes) {
        this.operationTypes = operationTypes;
    }

    public Number[] getEnvironments() {
        return environments;
    }

    public void setEnvironments(Number[] environments) {
        this.environments = environments;
    }

    public Number getMinPrice() {
        return minPrice;
    }

    public void setPrice(Number minPrice, Number maxPrice) {
        this.minPrice = minPrice;
        this.maxPrice = maxPrice;
    }

    public Number getMaxPrice() {
        return maxPrice;
    }

    public Number getMinSize() {
        return minSize;
    }

    public void setMinSize(Number minSize) {
        this.minSize = minSize;
    }

    public Number getMinCoveredSize() {
        return minCoveredSize;
    }

    public void setMinCoveredSize(Number minCoveredSize) {
        this.minCoveredSize = minCoveredSize;
    }

    public Number getMinPublishDate() {
        return minPublishDate;
    }

    public void setMinPublishDate(Number minPublishDate) {
        this.minPublishDate = minPublishDate;
    }
}


