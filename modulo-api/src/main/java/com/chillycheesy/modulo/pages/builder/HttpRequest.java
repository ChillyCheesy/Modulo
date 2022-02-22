package com.chillycheesy.modulo.pages.builder;

import com.chillycheesy.modulo.pages.HttpRequestType;

public @interface HttpRequest {
    HttpRequestType type();
    String path();
}
