package com.mileem.mileem.models;

/**
 * Created by ramirodiaz on 26/09/14.
 */
public class Multimedia {
    public enum Type {
        IMAGE, VIDEO;
    }
    private String previewUrl;
    private Multimedia.Type type = null;
    private String url;

    public Multimedia(Type type, String previewUrl, String url) {
        this.type = type;
        this.url = url;
        this.previewUrl = url;
    }

    public Type getType() { return type; }

    public void setType(Type type) {
        this.type = type;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getPreviewUrl() { return previewUrl; }

    public void setPreviewUrl(String previewUrl) { this.previewUrl = previewUrl; }
}
