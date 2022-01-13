package com.owle.hometracker.event;

import com.owle.hometracker.modules.Module;

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
    public OnStartEvent(Module module) {
        super(module);
    }

}
