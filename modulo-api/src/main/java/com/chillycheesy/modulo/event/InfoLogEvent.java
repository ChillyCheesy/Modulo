package com.chillycheesy.modulo.event;

import com.chillycheesy.modulo.utils.Logger;

/**
 * Event emitted when a info log is emit by the {@link Logger}.
 * @see LogEvent
 * @author henouille
 */
public class InfoLogEvent extends LogEvent {

    /**
     * Create a new Info log Event.
     * @param message The info message.
     */
    public InfoLogEvent(String message) {
        super(message);
    }

}
