package com.chillycheesy.modulo.event;

import com.chillycheesy.modulo.utils.Logger;

/**
 * Event emitted when a debug log is emit by the {@link Logger}.
 * @see LogEvent
 * @author henouille.
 */
public class DebugLogEvent extends LogEvent {

    /**
     * Create a new DebugLogEvent.
     * @param message The message to log.
     */
    public DebugLogEvent(String message) {
        super(message);
    }

}
