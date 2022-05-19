package com.chillycheesy.modulo.pages.natif;

import com.chillycheesy.modulo.pages.HttpRequestType;
import com.chillycheesy.modulo.pages.PageResponse;

public class StringRegexPage extends RegexPage {

    public StringRegexPage(HttpRequestType requestType, PageResponse content) {
        super(requestType, "\\w+", content);
    }

    public StringRegexPage(HttpRequestType requestType, String content) {
        super(requestType, "\\w+", content);
    }

    public StringRegexPage(HttpRequestType requestType) {
        this(requestType, "");
    }

    public StringRegexPage() { this(HttpRequestType.ANY); }
}
