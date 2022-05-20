package com.chillycheesy.modulo.pages.builder;

import com.chillycheesy.modulo.pages.Page;

import java.lang.annotation.*;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD})
@Inherited
public @interface PageType {
    Class<? extends Page> value();
}
