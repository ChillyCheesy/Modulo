package fr.owle.hometracker.pages;

import java.lang.annotation.*;

/**
 * Represent a parameter that you can add in a method.
 * The path parameter is used to get a value from the query of the path of the url.
 * It allow you to set default value to the parameter.
 */
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface QueryParam {
    String value() default "";
    String defaultValue() default "";
}
