package com.chillycheesy.modulo.pages;

import java.lang.annotation.*;

/**
 * Method decorator that represent the get http request.
 * The get request is used when you want to get an object from the database
 *
 * @see DeleteRequest
 * @see PostRequest
 * @see PutRequest
 *
 * @author henouille
 */
@Inherited
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface GetRequest {
    String value();
}
