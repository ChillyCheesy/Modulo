package com.chillycheesy.modulo.pages.subpages;

import com.chillycheesy.modulo.pages.HttpRequest;
import com.chillycheesy.modulo.pages.PageResponse;

public class BooleanRegexPage extends RegexPage {

    public BooleanRegexPage(HttpRequest requestType, PageResponse content) {
        super(requestType, "true|false", content);
    }

    public BooleanRegexPage(HttpRequest requestType, String content) {
        super(requestType, "true|false", content);
    }

    public BooleanRegexPage(HttpRequest requestType) {
        this(requestType, "");
    }

    public BooleanRegexPage() { this(HttpRequest.ANY); }
}
