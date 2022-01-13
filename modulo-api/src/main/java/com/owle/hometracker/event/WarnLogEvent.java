package com.owle.hometracker.event;

import com.owle.hometracker.utils.Log;

/**
 * Event emitted when a warn log is emit by the {@link Log}.
 * @see LogEvent
 * @author henouille
 */
public class WarnLogEvent extends LogEvent {

    /**
     * Create a new Warn log event.
     * @param message The info message.
     */
    public WarnLogEvent(String message) {
        super(message);
    }
}
