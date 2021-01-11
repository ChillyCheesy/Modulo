package fr.owle.hometracker.utils.exception;

import fr.owle.hometracker.modules.HTModule;

/**
 * This exception will be throws when The {@link fr.owle.hometracker.events.EventManager} try to invoke
 * an annotated method by the {@link fr.owle.hometracker.events.EventHandler} Annotation with a bad number of parameter.
 *
 * Please check the {@link fr.owle.hometracker.utils.Listener} documentation for more information about
 * the {@link fr.owle.hometracker.events.EventHandler} implementation.
 *
 * @author hénouille
 */
public class InvalidParameterEventHandlerException extends Exception {

    /**
     * Create a new Exception.
     * @param module Origin of the exception.
     */
    public InvalidParameterEventHandlerException(HTModule module) {
        super(module.getName() + ": Listeners methods need to have a unique parameter of fr.owle.hometracker.events.Event type.");
    }

}
