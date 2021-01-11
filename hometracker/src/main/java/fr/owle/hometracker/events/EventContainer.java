package fr.owle.hometracker.events;

/**
 * The EventContainer contain all about the events.
 * @author henouille
 */
public class EventContainer {

    private static EventManager manager;

    /**
     * Getter for the unique EventManager.
     * @return THe unique EventManager.
     */
    public EventManager getEventManager() {
        return manager = manager == null ? new EventManager() : manager;
    }

}
