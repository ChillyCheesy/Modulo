package fr.owle.hometracker.events;

import java.lang.annotation.*;

/**
 * EventHandler annotation is used over methods on Listener classes.
 * @author hénouille
 */
@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface EventHandler {

    /**
     * Handler priority.
     * <i>A higher priority will be called first in the
     * method stack.</i>
     * @return priority value.
     */
    int value() default Event.NEUTRAL;
}
