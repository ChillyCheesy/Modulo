package com.chillycheesy.modulo.pages.subpages;

import com.chillycheesy.modulo.pages.HttpRequest;

import java.util.function.Supplier;

public class StringRegexPage extends RegexPage {

    public StringRegexPage(HttpRequest requestType, Supplier<String> content) {
        super(requestType, "\\w+", content);
    }

    public StringRegexPage(HttpRequest requestType) {
        this(requestType, () -> "");
    }

    public StringRegexPage() { this(HttpRequest.ANY); }
}
