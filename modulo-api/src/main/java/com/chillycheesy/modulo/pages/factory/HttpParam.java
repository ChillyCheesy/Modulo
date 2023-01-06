package com.chillycheesy.modulo.pages.factory;

import java.lang.annotation.*;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.PARAMETER})
@Inherited
public @interface HttpParam {
        String value() default "?";
        String defaultValue() default "";
}
