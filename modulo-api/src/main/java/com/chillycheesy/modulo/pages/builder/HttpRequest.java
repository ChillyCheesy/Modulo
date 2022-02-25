package com.chillycheesy.modulo.pages.builder;

import com.chillycheesy.modulo.pages.HttpRequestType;

import java.lang.annotation.*;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD})
@Inherited
public @interface HttpRequest {
    HttpRequestType type();
    String path();
}
