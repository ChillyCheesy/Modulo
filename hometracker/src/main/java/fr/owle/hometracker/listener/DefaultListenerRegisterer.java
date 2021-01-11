package fr.owle.hometracker.listener;

import fr.owle.hometracker.HTAPI;
import fr.owle.hometracker.events.EventManager;
import fr.owle.hometracker.pages.Page;
import fr.owle.hometracker.pages.PageManager;

/**
 * DefaultListenerRegister register basic {@link fr.owle.hometracker.utils.Listener} for the {@link HTAPI}.
 * @author henouille
 */
public class DefaultListenerRegisterer {

    private EventManager eventManager;

    public DefaultListenerRegisterer(EventManager eventManager) {
        this.eventManager = eventManager;
    }

    public DefaultListenerRegisterer() {
        this(HTAPI.getEvent().getEventManager());
    }

    /**
     * Registers all basic {@link fr.owle.hometracker.utils.Listener}.
     * @param htapi The main module.
     * @see HTAPIPageRequestListener
     */
    public void register(HTAPI htapi) {
        final HTAPIPageRequestListener pageRequestListener = new HTAPIPageRequestListener();

        eventManager.registerEventListener(htapi, pageRequestListener);
    }

    public EventManager getEventManager() {
        return eventManager;
    }
}
