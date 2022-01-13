package com.owle.hometracker.utils.exception;

import com.owle.hometracker.modules.Module;
import com.owle.hometracker.signals.SignalHandler;
import com.owle.hometracker.signals.SignalManager;
import com.owle.hometracker.listener.Listener;

/**
 * This exception will be throws when The {@link SignalManager} try to invoke
 * an annotated method by the {@link SignalHandler} Annotation with a bad type and number of parameter.
 *
 * Please check the {@link Listener} documentation for more information about
 * the {@link SignalHandler} implementation.
 *
 * @author hénouille
 */
public class InvalidParameterSignalHandlerException extends Exception {

    /**
     * Create a new Exception.
     * @param module Origin of the exception.
     */
    public InvalidParameterSignalHandlerException(Module module) {
        super(module.getName() + ": Listeners methods need to have a unique parameter of String[] type.");
    }

}
