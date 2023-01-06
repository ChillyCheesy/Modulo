package com.chillycheesy.modulo.pages.factory;

import com.chillycheesy.modulo.pages.comparator.RequestMatcher;

import java.lang.annotation.*;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
@Inherited
public @interface Matcher {
    Class<? extends RequestMatcher>[] value() default {};
}
