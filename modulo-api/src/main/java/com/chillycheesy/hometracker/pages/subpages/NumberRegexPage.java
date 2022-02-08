package com.chillycheesy.hometracker.pages.subpages;

import com.chillycheesy.hometracker.pages.HttpRequest;

import java.util.function.Supplier;

public class NumberRegexPage extends RegexPage {
    public NumberRegexPage(HttpRequest requestType, Supplier<String> content) {
        super(requestType, "-?\\d+.?\\d*", content);
    }

    public NumberRegexPage(HttpRequest requestType) {
        this(requestType, () -> "");
    }

    public NumberRegexPage() { this(HttpRequest.ANY); }
}
