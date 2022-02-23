package com.chillycheesy.modulo.pages.builder;

public @interface Path {
    String value() default "";
    String defaultValue() default "";
}
