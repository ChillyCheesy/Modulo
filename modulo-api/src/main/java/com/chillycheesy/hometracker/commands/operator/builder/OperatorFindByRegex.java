package com.chillycheesy.hometracker.commands.operator.builder;

import java.lang.annotation.*;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Inherited
public @interface OperatorFindByRegex {
    String value();
}
