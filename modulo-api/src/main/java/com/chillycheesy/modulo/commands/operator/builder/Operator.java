package com.chillycheesy.modulo.commands.operator.builder;

import java.lang.annotation.*;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Inherited
public @interface Operator {
    int value();
}
