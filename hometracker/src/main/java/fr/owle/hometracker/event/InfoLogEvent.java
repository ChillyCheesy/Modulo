package fr.owle.hometracker.event;

/**
 * Event emitted when a info log is emit by the {@link fr.owle.hometracker.utils.Log}.
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
