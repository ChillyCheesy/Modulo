package fr.owle.hometracker.event;

/**
 * Event emitted when a warn log is emit by the {@link fr.owle.hometracker.utils.Log}.
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
