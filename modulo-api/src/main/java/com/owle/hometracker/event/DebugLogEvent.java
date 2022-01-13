package com.owle.hometracker.event;

import com.owle.hometracker.utils.Log;

/**
 * Event emitted when a debug log is emit by the {@link Log}.
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
