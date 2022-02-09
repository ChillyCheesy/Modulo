package com.chillycheesy.modulo.pages;

import java.lang.annotation.*;

/**
 * Method decorator that represent the put http request.
 * The put request is used when you want to remove an object from the database
 *
 * @see GetRequest
 * @see PostRequest
 * @see DeleteRequest
 *
 * @author henouille
 */
@Inherited
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface PutRequest {
    String value();
}
