package com.chillycheesy.modulo.utils;

import com.chillycheesy.modulo.events.EventContainer;
import com.chillycheesy.modulo.events.EventManager;
import com.chillycheesy.modulo.modules.Module;
import com.chillycheesy.modulo.event.DebugLogEvent;
import com.chillycheesy.modulo.event.ErrorLogEvent;
import com.chillycheesy.modulo.event.InfoLogEvent;
import com.chillycheesy.modulo.event.WarnLogEvent;

/**
 * Logger class can be used to display information in the server console.
 *
 * @author hénouille
 */
public class Logger {

    /**
     * The Event Manager.
     */
    private final EventManager eventManager;

    /**
     * Initialize a new Logger.
     * <i>It si not necessary to instantiate a new Logger.
     * If you want to display a log, use <code>ModuloAPI.getLogger()</code>.</i>
     * @param container EventContainer instance.
     */
    public Logger(EventContainer container) {
        eventManager = container.getEventManager();
    }

    /**
     * Broadcast an info log in the console.
     * @param module Module that is logging the message.
     * @param content The Object to broadcast <i>(The console use the toString() method of the object to broadcast it.)</i>.
     */
    public void info(Module module, Object content) {
        final InfoLogEvent infoLogEvent = new InfoLogEvent(content != null ? content.toString() : null);
        eventManager.emitEvent(module, infoLogEvent);
    }

    /**
     * Broadcast warn log in the console.
     * @param module Module that is logging the message.
     * @param content The Object to broadcast <i>(The console use the toString() method of the object to broadcast it.)</i>.
     */
    public void warn(Module module, Object content) {
        final WarnLogEvent warnLogEvent = new WarnLogEvent(content != null ? content.toString() : null);
        eventManager.emitEvent(module, warnLogEvent);
    }

    /**
     * Broadcast an error log in the console.
     * @param module Module that is logging the message.
     * @param content The Object to broadcast <i>(The console use the toString() method of the object to broadcast it.)</i>.
     */
    public void error(Module module, Object content) {
        final ErrorLogEvent errorLogEvent = new ErrorLogEvent(content != null ? content.toString() : null);
        eventManager.emitEvent(module, errorLogEvent);
    }

    /**
     * Broadcast a debug log in the console.
     * @param module Module that is logging the message.
     * @param content The Object to broadcast <i>(The console use the toString() method of the object to broadcast it.)</i>.
     */
    public void debug(Module module, Object content) {
        final DebugLogEvent debugLogEvent = new DebugLogEvent(content != null ? content.toString() : null);
        eventManager.emitEvent(module, debugLogEvent);
    }

}
