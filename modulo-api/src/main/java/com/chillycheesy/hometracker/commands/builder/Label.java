package com.chillycheesy.hometracker.commands.builder;

import java.lang.annotation.*;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Inherited
public @interface Label {
    String[] value();
}
