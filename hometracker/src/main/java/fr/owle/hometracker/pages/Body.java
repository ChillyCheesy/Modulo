package fr.owle.hometracker.pages;

import java.lang.annotation.*;

/**
 * Decorator that decorate the body of a http request.
 * HomeTracker will be able to parse the decorated object to a JSON representation,
 * or otherwise parse a JSON String body to an Object.
 *
 * To make HomeTracker able to parse correctly your object, you need to implement all the getters setters of all attributes,
 * and also implement a constructor without parameter.
 *
 * @author henouille
 */
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Body {
}
