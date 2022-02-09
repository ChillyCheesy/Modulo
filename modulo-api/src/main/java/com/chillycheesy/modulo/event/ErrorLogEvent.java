package com.chillycheesy.modulo.event;

import com.chillycheesy.modulo.utils.Log;

/**
 * Event emitted when a error log is emit by the {@link Log}.
 * @see LogEvent
 * @author henouille
 */
public class ErrorLogEvent extends LogEvent {

    /**
     * Create a new Error log Event.
     * @param message The error message.
     */
    public ErrorLogEvent(String message) {
        super(message);
    }

}
