package fr.owle.hometracker.event;

import fr.owle.hometracker.modules.HTModule;

/**
 * Event emits when a module is stopping.
 * @see ModuleStatusEvent
 * @author henouille
 */
public class OnStopEvent extends ModuleStatusEvent {

    /**
     * Create a new Stop Module.
     * @param module the module that is stopping.
     */
    public OnStopEvent(HTModule module) {
        super(module);
    }

}
