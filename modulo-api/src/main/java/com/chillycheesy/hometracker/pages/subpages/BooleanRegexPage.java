package com.chillycheesy.hometracker.pages.subpages;

import com.chillycheesy.hometracker.pages.HttpRequest;

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
