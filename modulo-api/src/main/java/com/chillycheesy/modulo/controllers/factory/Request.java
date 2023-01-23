package com.chillycheesy.modulo.controllers.factory;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
@Inherited
public @interface Request {
    String method() default "GET";
    String path() default "";
}
