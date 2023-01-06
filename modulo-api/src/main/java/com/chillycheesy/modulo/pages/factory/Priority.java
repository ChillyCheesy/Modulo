package com.chillycheesy.modulo.pages.factory;

import java.lang.annotation.*;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
@Inherited
public @interface Priority {
    int value() default 0;
}
