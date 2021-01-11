package fr.owle.hometracker.pages;

import java.lang.annotation.*;

/**
 * Method decorator that represent the post http request.
 * The post request is used when you want to update an object from the database
 *
 * @see GetRequest
 * @see DeleteRequest
 * @see PutRequest
 *
 * @author henouille
 */
@Inherited
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface PostRequest {
    String value();
}
