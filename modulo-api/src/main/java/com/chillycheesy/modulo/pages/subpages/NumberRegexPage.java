package com.chillycheesy.modulo.pages.subpages;

import com.chillycheesy.modulo.pages.HttpRequest;
import com.chillycheesy.modulo.pages.PageResponse;

import java.util.function.Supplier;

public class NumberRegexPage extends RegexPage {

    public NumberRegexPage(HttpRequest requestType, PageResponse content) {
        super(requestType, "-?\\d+.?\\d*", content);
    }

    public NumberRegexPage(HttpRequest requestType, String content) {
        super(requestType, "-?\\d+.?\\d*", content);
    }

    public NumberRegexPage(HttpRequest requestType) {
        this(requestType, "");
    }

    public NumberRegexPage() { this(HttpRequest.ANY); }
}
