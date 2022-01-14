package com.chillycheesy.hometracker.event;

import com.chillycheesy.hometracker.utils.Log;

/**
 * Event emitted when a info log is emit by the {@link Log}.
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
