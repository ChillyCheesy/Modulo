package com.owle.hometracker.utils.exception;

import com.owle.hometracker.events.EventHandler;
import com.owle.hometracker.events.EventManager;
import com.owle.hometracker.modules.Module;
import com.owle.hometracker.listener.Listener;

/**
 * This exception will be throws when The {@link EventManager} try to invoke
 * an annotated method by the {@link EventHandler} Annotation with a bad number of parameter.
 *
 * Please check the {@link Listener} documentation for more information about
 * the {@link EventHandler} implementation.
 *
 * @author hénouille
 */
public class InvalidParameterEventHandlerException extends Exception {

    /**
     * Create a new Exception.
     * @param module Origin of the exception.
     */
    public InvalidParameterEventHandlerException(Module module) {
        super(module.getName() + ": Listeners methods need to have a unique parameter of fr.owle.hometracker.events.Event type.");
    }

}
