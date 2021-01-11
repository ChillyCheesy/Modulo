package fr.owle.hometracker.event;

/**
 * Event emitted when a error log is emit by the {@link fr.owle.hometracker.utils.Log}.
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
