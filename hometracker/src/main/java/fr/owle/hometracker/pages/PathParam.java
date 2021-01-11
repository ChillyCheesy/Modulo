package fr.owle.hometracker.pages;

import java.lang.annotation.*;

/**
 * Represent a parameter that you can add in a method.
 * The path parameter is used to get a value from the path of the url.
 */
@Inherited
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface PathParam {
    String value() default "";
}
