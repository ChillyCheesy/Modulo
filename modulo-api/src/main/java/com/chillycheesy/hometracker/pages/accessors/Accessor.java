package com.chillycheesy.hometracker.pages.accessors;

import com.chillycheesy.hometracker.pages.HttpRequest;

public abstract class Accessor {
    /**
     * The subpath that will reach this accessor.
     */
    private String subpath;
    /**
     * The http request type (GET, PUT, POST, DELETE)
     */
    private HttpRequest requestType;

    public Accessor(HttpRequest requestType, String subpath) {
        this.requestType = requestType;
        this.subpath = subpath;
    }


    public String getSubpath() {
        return subpath;
    }

    public void setSubpath(String subpath) {
        this.subpath = subpath;
    }

    /**
     * The content or the resource to
     */
    abstract String content();
}
