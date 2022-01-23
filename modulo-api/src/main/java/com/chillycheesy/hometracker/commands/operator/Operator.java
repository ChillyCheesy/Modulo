package com.chillycheesy.hometracker.commands.operator;

import java.lang.annotation.*;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Inherited
public @interface Operator {
    int value();
}
