package com.mileem.mileem.managers;

import java.util.Collection;
import com.mileem.mileem.models.IdName;

/**
 * Created by ramirodiaz on 09/09/14.
 */

public class DefinitionsManager {
    private static volatile DefinitionsManager instance = null;

    private Collection<IdName> neightboardsCollection = null;
    private Collection<IdName> environmentsTypesCollection = null;
    private Collection<IdName> operationTypesCollection = null;
    private Collection<IdName> propertyTypesCollection = null;
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

    public Collection<IdName> getNeightboardsCollection() {
        return neightboardsCollection;
    }

    public void setNeightboardsCollection(Collection<IdName> neightboardsCollection) {
        this.neightboardsCollection = neightboardsCollection;
    }

    public Collection<IdName> getEnvironmentsTypesCollection() {
        return environmentsTypesCollection;
    }

    public void setEnvironmentsTypesCollection(Collection<IdName> environmentsTypesCollection) {
        this.environmentsTypesCollection = environmentsTypesCollection;
    }

    public Collection<IdName> getOperationTypesCollection() {
        return operationTypesCollection;
    }

    public void setOperationTypesCollection(Collection<IdName> operationTypesCollection) {
        this.operationTypesCollection = operationTypesCollection;
    }

    public Collection<IdName> getPropertyTypesCollection() {
        return propertyTypesCollection;
    }

    public void setPropertyTypesCollection(Collection<IdName> propertyTypesCollection) {
        this.propertyTypesCollection = propertyTypesCollection;
    }
}