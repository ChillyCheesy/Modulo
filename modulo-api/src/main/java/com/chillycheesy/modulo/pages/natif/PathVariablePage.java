package com.chillycheesy.modulo.pages.natif;

import com.chillycheesy.modulo.pages.HttpRequestType;
import com.chillycheesy.modulo.pages.PageResponse;

public class PathVariablePage extends StringRegexPage {

    private String key;

    public PathVariablePage(HttpRequestType requestType, PageResponse content) {
        super(requestType, content);
    }

    public PathVariablePage(HttpRequestType requestType, String content) {
        super(requestType, content);
    }

    public PathVariablePage(HttpRequestType requestType) {
        super(requestType);
    }

    public PathVariablePage() {
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
