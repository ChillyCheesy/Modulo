package com.chillycheesy.modulo.pages.factory;


import com.chillycheesy.modulo.pages.Page;

import java.lang.annotation.*;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
@Inherited
public @interface PageType {

    Class<? extends Page> value() default Page.class;

}
