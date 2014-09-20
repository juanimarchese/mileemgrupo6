package com.mileem.mileem.managers;

import java.util.ArrayList;
import java.util.Collection;
import com.mileem.mileem.models.IdName;

/**
 * Created by ramirodiaz on 09/09/14.
 */

public class DefinitionsManager {
    private static volatile DefinitionsManager instance = null;

    private ArrayList<IdName> neightboardsCollection = null;
    private ArrayList<IdName> environmentsTypesCollection = null;
    private ArrayList<IdName> operationTypesCollection = null;
    private ArrayList<IdName> propertyTypesCollection = null;
    private ArrayList<IdName> dateRangesCollection = null;

    private DefinitionsManager() {};

    public static DefinitionsManager getInstance() {
        if (instance == null) {
            synchronized (DefinitionsManager.class) {
                if (instance == null) {
                    instance = new DefinitionsManager();
                }
            }

        }
        return instance;
    }

    public ArrayList<IdName> getNeightboardsCollection() {
        return neightboardsCollection;
    }

    public void setNeightboardsCollection(ArrayList<IdName> neightboardsCollection) {
        this.neightboardsCollection = neightboardsCollection;
    }

    public ArrayList<IdName> getEnvironmentsTypesCollection() {
        return environmentsTypesCollection;
    }

    public void setEnvironmentsTypesCollection(ArrayList<IdName> environmentsTypesCollection) {
        this.environmentsTypesCollection = environmentsTypesCollection;
    }

    public ArrayList<IdName> getOperationTypesCollection() {
        return operationTypesCollection;
    }

    public void setOperationTypesCollection(ArrayList<IdName> operationTypesCollection) {
        this.operationTypesCollection = operationTypesCollection;
    }

    public ArrayList<IdName> getPropertyTypesCollection() {
        return propertyTypesCollection;
    }

    public void setPropertyTypesCollection(ArrayList<IdName> propertyTypesCollection) {
        this.propertyTypesCollection = propertyTypesCollection;
    }

    public ArrayList<IdName> getDateRangesCollection() { return dateRangesCollection;}

    public void setDateRangesCollections(ArrayList<IdName> dateRangesCollection) {
        this.dateRangesCollection = dateRangesCollection;
    }
}