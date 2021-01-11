package fr.owle.hometracker.event;

import fr.owle.hometracker.events.Cancelable;
import fr.owle.hometracker.events.Event;
import fr.owle.hometracker.modules.HTModule;

import java.util.Objects;

/**
 * Event emits when there's a change on a {@link HTModule} status.
 *
 * @see OnStartEvent
 * @see OnLoadEvent
 * @see OnStopEvent
 * @see Cancelable
 * @author henouille
 */
public abstract class ModuleStatusEvent extends Event implements Cancelable {

    private HTModule module;
    private boolean cancel;

    /**
     * Create a new Module status Event.
     * @param module the module which have a change on his status
     */
    public ModuleStatusEvent(HTModule module) {
        this.module = module;
        cancel = false;
    }

    /**
     * Getter for the module.
     * @return the module.
     */
    public HTModule getModule() {
        return module;
    }

    /**
     * Getter for the module.
     * @param module The new module.
     */
    public void setModule(HTModule module) {
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
