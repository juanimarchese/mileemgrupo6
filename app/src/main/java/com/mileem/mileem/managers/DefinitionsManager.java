package com.mileem.mileem.managers;

import java.util.ArrayList;
import java.util.Collection;
import com.mileem.mileem.models.IdName;

/**
 * Created by ramirodiaz on 09/09/14.
 */

public class DefinitionsManager {
    private static volatile DefinitionsManager instance = null;

    private ArrayList<IdName> neighborhoods = null;
    private ArrayList<IdName> environments = null;
    private ArrayList<IdName> operationTypes = null;
    private ArrayList<IdName> propertyTypes = null;
    private ArrayList<IdName> dateRanges = null;
    private ArrayList<IdName> amenitieTypes = null;

    private DefinitionsManager() {};

    public static void setInstance (DefinitionsManager dm) {
        if (instance == null) {
            instance = dm;
        }
    }
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

    public ArrayList<IdName> getNeighborhoods() {
        return neighborhoods;
    }

    public void setNeighborhoods(ArrayList<IdName> neighborhoods) {
        this.neighborhoods = neighborhoods;
    }

    public ArrayList<IdName> getEnvironments() {
        return environments;
    }

    public void setEnvironments(ArrayList<IdName> environments) {
        this.environments = environments;
    }

    public ArrayList<IdName> getOperationTypes() {
        return operationTypes;
    }

    public void setOperationTypes(ArrayList<IdName> operationTypes) {
        this.operationTypes = operationTypes;
    }

    public ArrayList<IdName> getPropertyTypes() {
        return propertyTypes;
    }

    public void setPropertyTypes(ArrayList<IdName> propertyTypes) {
        this.propertyTypes = propertyTypes;
    }

    public ArrayList<IdName> getDateRanges() {
        return dateRanges;
    }

    public void setDateRanges(ArrayList<IdName> dateRanges) {
        this.dateRanges = dateRanges;
    }

    public ArrayList<IdName> getAmenitieTypes() {
        return amenitieTypes;
    }

    public void setAmenitieTypes(ArrayList<IdName> amenitieTypes) {
        this.amenitieTypes = amenitieTypes;
    }
}