package com.owle.hometracker.event;

import com.owle.hometracker.events.Cancelable;
import com.owle.hometracker.events.Event;
import com.owle.hometracker.modules.Module;

import java.util.Objects;

/**
 * Event emits when there's a change on a {@link Module} status.
 *
 * @see OnStartEvent
 * @see OnLoadEvent
 * @see OnStopEvent
 * @see Cancelable
 * @author henouille
 */
public abstract class ModuleStatusEvent extends Event implements Cancelable {

    private Module module;
    private boolean cancel;

    /**
     * Create a new Module status Event.
     * @param module the module which have a change on his status
     */
    public ModuleStatusEvent(Module module) {
        this.module = module;
        cancel = false;
    }

    /**
     * Getter for the module.
     * @return the module.
     */
    public Module getModule() {
        return module;
    }

    /**
     * Getter for the module.
     * @param module The new module.
     */
    public void setModule(Module module) {
        this.module = module;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ModuleStatusEvent that = (ModuleStatusEvent) o;
        return Objects.equals(module, that.module);
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
