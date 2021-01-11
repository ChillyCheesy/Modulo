package fr.owle.hometracker.utils.exception;

import fr.owle.hometracker.modules.HTModule;

/**
 * This exception will be throws when The {@link fr.owle.hometracker.signals.SignalManager} try to invoke
 * an annotated method by the {@link fr.owle.hometracker.signals.SignalHandler} Annotation with a bad type and number of parameter.
 *
 * Please check the {@link fr.owle.hometracker.utils.Listener} documentation for more information about
 * the {@link fr.owle.hometracker.signals.SignalHandler} implementation.
 *
 * @author hénouille
 */
public class InvalidParameterSignalHandlerException extends Exception {

    /**
     * Create a new Exception.
     * @param module Origin of the exception.
     */
    public InvalidParameterSignalHandlerException(HTModule module) {
        super(module.getName() + ": Listeners methods need to have a unique parameter of String[] type.");
    }

}
