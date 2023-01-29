package com.chillycheesy.modulo.controllers.methodcontroller;

import java.lang.annotation.*;

/**
 * This decorator is used inside a method interpreted by the {@link com.chillycheesy.modulo.controllers.MethodController} Object.
 * It is used to get the http request body. If the parameter is a primitive type, it will be converted to the primitive type.
 * Else the parameter body is read has a json object, and it is parsed by Jackson.
 *
 * @author ChillyCheesy
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.PARAMETER})
@Inherited
public @interface RequestBody {
}
