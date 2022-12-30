package com.chillycheesy.modulo.pages;

import com.chillycheesy.modulo.pages.response.ResourceResponse;
import com.chillycheesy.modulo.utils.Priority;

public class ResourcePage extends TemplatePage {

    private static final ResourceResponse RESOURCE_RESPONSE = new ResourceResponse();
    private static final String DEFAULT_METHOD = "GET";

    private static final int DEFAULT_PRIORITY = Priority.NEUTRAL;

    private String resourcePath;

    public ResourcePage(String name, String path, String resource, int priority) {
        super(name, path, DEFAULT_METHOD, RESOURCE_RESPONSE, priority);
        this.resourcePath = resource;
    }

    public ResourcePage(String name, String path, String resource) {
        this(name, path, resource, DEFAULT_PRIORITY);
    }

    public ResourcePage() {
        this("", "", "");
    }

    public void setResourcePath(String resourcePath) {
        this.resourcePath = resourcePath;
    }

    public String getResourcePath() {
        return resourcePath;
    }

    @Override
    public String getPath() {
        final String path = super.getPath();
        return path.replaceAll(".+", "$0/?**");
    }

    public String getResourcePath(String requestPath) {
        final String path = super.getPath();
        final String additionalPath = requestPath.replaceAll(path, "");
        return (resourcePath + additionalPath).replaceAll("/+", "/");
    }
}
