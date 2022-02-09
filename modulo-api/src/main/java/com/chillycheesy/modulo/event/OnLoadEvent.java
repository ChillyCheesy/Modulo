package com.chillycheesy.modulo.event;

import com.chillycheesy.modulo.modules.Module;

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
    public OnLoadEvent(Module module) {
        super(module);
    }

}
