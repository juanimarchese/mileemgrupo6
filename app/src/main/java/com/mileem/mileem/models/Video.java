package com.mileem.mileem.models;

/**
 * Created by ramirodiaz on 12/09/14.
 */
public class Video {
    String url;
    String embedUrl;
    String thumbnail;

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
        return this.getEmbedUrl() != null &&  this.getThumbnail() != null &&
            this.getEmbedUrl().length() > 0 && this.getThumbnail().length() > 0;
    }
}
