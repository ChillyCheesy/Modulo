package com.chillycheesy.hometracker.event;

import com.chillycheesy.hometracker.modules.Module;

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
    public OnStopEvent(Module module) {
        super(module);
    }

}
