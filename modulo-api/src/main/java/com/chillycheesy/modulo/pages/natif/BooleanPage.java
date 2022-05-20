package com.chillycheesy.modulo.pages.natif;

import com.chillycheesy.modulo.pages.HttpRequestType;
import com.chillycheesy.modulo.pages.PageResponse;

public class BooleanPage extends RegexPage {

    public BooleanPage(HttpRequestType requestType, PageResponse content) {
        super(requestType, "true|false", content);
    }

    public BooleanPage(HttpRequestType requestType, String content) {
        super(requestType, "true|false", content);
    }

    public BooleanPage(HttpRequestType requestType) {
        this(requestType, "");
    }

    public BooleanPage() { this(HttpRequestType.ANY); }
}
