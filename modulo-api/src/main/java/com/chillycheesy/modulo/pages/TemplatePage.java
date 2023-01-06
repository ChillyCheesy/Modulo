package com.chillycheesy.modulo.pages;

import com.chillycheesy.modulo.pages.comparator.StrictMethodRequestMatcher;
import com.chillycheesy.modulo.pages.comparator.TemplatePathRequestMatcher;
import com.chillycheesy.modulo.pages.response.ResponseHandler;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TemplatePage extends Page {

    private final Map<String, String> templates;

    public TemplatePage(String name, String path, String method, ResponseHandler responseHandler, int priority) {
        super(name, path, method, responseHandler, List.of(new StrictMethodRequestMatcher(), new TemplatePathRequestMatcher()), priority);
        this.templates = new HashMap<>();
    }

    public TemplatePage(String name, String path, String method, ResponseHandler responseHandler) {
        this(name, path, method, responseHandler, 0);
    }

    public TemplatePage(String name, String path, ResponseHandler responseHandler) {
        this(name, path, "GET", responseHandler);
    }

    public TemplatePage() {
        this("", "", "", null);
    }

    public void saveTemplateVariable(Map<String, String> templates) {
        this.templates.putAll(templates);
    }

    public String getTemplateVariable(String name) {
        return this.templates.get(name);
    }

}
