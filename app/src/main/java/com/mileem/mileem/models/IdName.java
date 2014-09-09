package com.mileem.mileem.models;

/**
 * Created by ramirodiaz on 08/09/14.
 */
public class IdName {
    String name;
    Number id;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public IdName(String name, Number id) {
        this.name = name;
        this.id = id;
    }
}
