package com.chillycheesy.modulo.pages.builder;

public @interface Path {
    String value();
    String defaultValue() default "";
}
