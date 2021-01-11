package fr.owle.hometracker.pages;

import java.lang.annotation.*;

/**
 * Method decorator that represent the delete http request.
 * The delete request is used when you want to remove an object from the database
 *
 * @see GetRequest
 * @see PostRequest
 * @see PutRequest
 *
 * @author henouille
 */
@Inherited
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface DeleteRequest {
    String value();
}
