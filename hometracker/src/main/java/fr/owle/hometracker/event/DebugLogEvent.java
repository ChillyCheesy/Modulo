package fr.owle.hometracker.event;

/**
 * Event emitted when a debug log is emit by the {@link fr.owle.hometracker.utils.Log}.
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
