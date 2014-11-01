package com.mileem.mileem.models;

/**
 * Created by ramirodiaz on 12/09/14.
 */
public class Video {
    private String url;
    private String embedUrl;
    private String thumbnail;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getEmbedUrl() {
        return embedUrl;
    }

    public void setEmbedUrl(String embedUrl) {
        this.embedUrl = embedUrl;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public Boolean hasVideo() {
        return this.getUrl() != null && this.getUrl().length() > 0;
    }
}
