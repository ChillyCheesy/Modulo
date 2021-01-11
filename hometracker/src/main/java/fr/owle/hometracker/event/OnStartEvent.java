package fr.owle.hometracker.event;

import fr.owle.hometracker.modules.HTModule;

/**
 * Event emits when a module is starting.
 * @see ModuleStatusEvent
 * @author henouille
 */
public class OnStartEvent extends ModuleStatusEvent {

    /**
     * Create Start Event.
     * @param module The module that is starting.
     */
    public OnStartEvent(HTModule module) {
        super(module);
    }

}
