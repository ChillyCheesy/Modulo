package com.chillycheesy.modulo.utils.exception;

import com.chillycheesy.modulo.events.EventHandler;
import com.chillycheesy.modulo.events.EventManager;
import com.chillycheesy.modulo.listener.Listener;
import com.chillycheesy.modulo.modules.Module;

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
