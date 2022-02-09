package com.chillycheesy.modulo.pages.subpages;

import com.chillycheesy.modulo.pages.HttpRequest;

import java.util.function.Supplier;

public class BooleanRegexPage extends RegexPage {
    public BooleanRegexPage(HttpRequest requestType, Supplier<String> content) {
        super(requestType, "true|false", content);
    }

    public BooleanRegexPage(HttpRequest requestType) {
        this(requestType, () -> "");
    }

    public BooleanRegexPage() { this(HttpRequest.ANY); }
}
