package com.chillycheesy.modulo.pages.builder;

import java.lang.annotation.*;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.PARAMETER})
@Inherited
public @interface Path {
    String value() default "value";
}
