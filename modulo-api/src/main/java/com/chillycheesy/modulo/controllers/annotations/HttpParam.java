package com.chillycheesy.modulo.controllers.annotations;

import java.lang.annotation.*;

/**
 * This decorator is used inside a method interpreted by the {@link com.chillycheesy.modulo.controllers.MethodController} Object.
 * It is used to get the registered keys by the {@link com.chillycheesy.modulo.controllers.HttpParamVariableController}.
 * The value of the annotation is the name of the key.
 *
 * @author ChillyCheesy
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.PARAMETER})
@Inherited
public @interface HttpParam {

    /**
     * The value of the parameter key. By default, it assigns to "?".
     * If the value is "?", the value will be the parameter name.
     *
     * @return The name of the parameter.
     */
    String value() default "?";
}
