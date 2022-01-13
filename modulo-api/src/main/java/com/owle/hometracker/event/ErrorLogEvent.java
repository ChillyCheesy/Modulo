package com.owle.hometracker.event;

import com.owle.hometracker.utils.Log;

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
