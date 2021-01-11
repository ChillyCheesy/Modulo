package fr.owle.hometracker.listener;

import fr.owle.hometracker.HTAPI;
import fr.owle.hometracker.events.EventManager;

/**
 * DefaultListenerRegister register basic {@link fr.owle.hometracker.utils.Listener} for the {@link HTAPI}.
 * @author henouille
 */
public class DefaultListenerRegisterer {

    /**
     * Registers all basic {@link fr.owle.hometracker.utils.Listener}.
     * @param htapi The main module.
     * @see HTAPIPageRequestListener
     */
    public void register(HTAPI htapi) {
        final EventManager eventManager = HTAPI.getEvent().getEventManager();
        final HTAPIPageRequestListener pageRequestListener = new HTAPIPageRequestListener();
        eventManager.registerEventListener(htapi, pageRequestListener);
    }

}
