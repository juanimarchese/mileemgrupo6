package com.mileem.mileem.models;

/**
 * Created by ramirodiaz on 26/09/14.
 */
public class Multimedia {
    public enum Type {
        IMAGE, VIDEO;
    }

    Multimedia.Type type = null;
    String url;

    public Multimedia(Type type, String url) {
        this.type = type;
        this.url = url;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
