package fr.owle.hometracker.event;

import fr.owle.hometracker.modules.HTModule;

/**
 * Event emits when a module is loading.
 * @see ModuleStatusEvent
 * @author henouille
 */
public class OnLoadEvent extends ModuleStatusEvent {

    /**
     * Create a new Load Event
     * @param module the module that is changing of status
     */
    public OnLoadEvent(HTModule module) {
        super(module);
    }

}
