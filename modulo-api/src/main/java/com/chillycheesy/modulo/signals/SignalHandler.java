package com.chillycheesy.modulo.signals;

import java.lang.annotation.*;

/**
 * Method decorator to set the method as a SignalHandler.
 * The value is the name of the signal which will call your method when the same name signal will be emitted.
 */
@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface SignalHandler {
    String value();
    int priority() default 0;
}
