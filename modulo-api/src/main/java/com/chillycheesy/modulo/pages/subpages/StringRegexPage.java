package com.chillycheesy.modulo.pages.subpages;

import com.chillycheesy.modulo.pages.HttpRequest;
import com.chillycheesy.modulo.pages.PageResponse;

public class StringRegexPage extends RegexPage {

    public StringRegexPage(HttpRequest requestType, PageResponse content) {
        super(requestType, "\\w+", content);
    }

    public StringRegexPage(HttpRequest requestType, String content) {
        super(requestType, "\\w+", content);
    }

    public StringRegexPage(HttpRequest requestType) {
        this(requestType, "");
    }

    public StringRegexPage() { this(HttpRequest.ANY); }
}
