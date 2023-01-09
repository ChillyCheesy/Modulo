package com.chillycheesy.modulo.pages.factory;

import java.lang.annotation.*;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.PARAMETER})
@Inherited
public @interface PathVariable {
    String value() default "?";
    String defaultValue() default "";
}
