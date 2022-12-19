package com.chillycheesy.modulo.pages;

import com.chillycheesy.modulo.pages.comparator.StrictMethodRequestMatcher;
import com.chillycheesy.modulo.pages.comparator.TemplatePathRequestMatcher;
import com.chillycheesy.modulo.pages.response.ResponseHandler;

import java.util.List;
import java.util.Map;

public class TemplatePage extends Page {

    private Map<String, String> templates;

    public TemplatePage(String name, String path, String method, ResponseHandler responseHandler, int priority) {
        super(name, path, method, responseHandler, List.of(new StrictMethodRequestMatcher(), new TemplatePathRequestMatcher()), priority);
    }

    public void saveTemplateVariable(String name, String value) {
        this.templates.put(name, value);
    }

    public String getTemplateVariable(String name) {
        return this.templates.get(name);
    }

}
