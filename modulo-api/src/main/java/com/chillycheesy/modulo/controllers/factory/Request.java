package com.chillycheesy.modulo.controllers.factory;

public @interface Request {
    String method() default "GET";
    String path() default "";
}
