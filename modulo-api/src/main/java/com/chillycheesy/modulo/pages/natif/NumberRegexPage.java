package com.chillycheesy.modulo.pages.natif;

import com.chillycheesy.modulo.pages.HttpRequestType;
import com.chillycheesy.modulo.pages.PageResponse;

public class NumberRegexPage extends RegexPage {

    public NumberRegexPage(HttpRequestType requestType, PageResponse content) {
        super(requestType, "-?\\d+.?\\d*", content);
    }

    public NumberRegexPage(HttpRequestType requestType, String content) {
        super(requestType, "-?\\d+.?\\d*", content);
    }

    public NumberRegexPage(HttpRequestType requestType) {
        this(requestType, "");
    }

    public NumberRegexPage() { this(HttpRequestType.ANY); }
}
