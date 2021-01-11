package fr.owle.hometracker.event;

import fr.owle.hometracker.events.Cancelable;
import fr.owle.hometracker.events.Event;

import java.util.Objects;

/**
 * The Subclass of the log events.
 * A log event was emit when a message are going to be emitted.
 * @see DebugLogEvent
 * @see LogEvent
 * @see ErrorLogEvent
 * @see WarnLogEvent
 * @see Cancelable
 * @author henouille.
 */
public class LogEvent extends Event implements Cancelable {

    private String message;
    private boolean cancel;

    /**
     * Create a new LogEvent.
     * @param message The message to log.
     */
    public LogEvent(String message) {
        this.message = message;
        cancel = false;
    }

    /**
     * Getter for the message to load.
     * @return The message to load.
     */
    public String getMessage() {
        return message;
    }

    /**
     * Setter for the message to load.
     * @param message The message to load.
     */
    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LogEvent logEvent = (LogEvent) o;
        return Objects.equals(getEmitter(), logEvent.getEmitter()) &&
                Objects.equals(message, logEvent.message);
    }

    @Override
    public boolean isCanceled() {
        return cancel;
    }

    @Override
    public void setCanceled(boolean cancel) {
        this.cancel = cancel;
    }
}
