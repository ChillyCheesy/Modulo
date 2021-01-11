package fr.owle.hometracker.events;

import fr.owle.hometracker.modules.HTModule;

/**
 * An Event can be create, emit and catch by every module.
 * Each event contain some information about it.
 * @author henouille
 */
public class Event {

    public static final int DIVINE = 3;
    public static final int MAJOR = 2;
    public static final int IMPORTANT = 1;
    public static final int NEUTRAL = 0;
    public static final int MISERABLE = -1;

    /**
     * The emitter module.
     */
    protected HTModule emitter;

    /**
     * Create a new Event with a null emitter.
     */
    public Event() {
        this(null);
    }

    /**
     * Create a new Event.
     * @param emitter The emitter module.
     */
    public Event(HTModule emitter) {
        this.emitter = emitter;
    }

    /**
     * Getter for the emitter.
     * @return The emitter module.
     */
    public HTModule getEmitter() {
        return emitter;
    }

    /**
     * Setter for the emitter.
     * @param emitter The new emitter module.
     */
    public void setEmitter(HTModule emitter) {
        this.emitter = emitter;
    }

}
