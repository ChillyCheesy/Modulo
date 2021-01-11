package fr.owle.hometracker.services;

import fr.owle.hometracker.HTAPI;
import fr.owle.hometracker.events.EventManager;
import fr.owle.hometracker.listeners.LogListener;
import fr.owle.hometracker.listeners.StatusListener;
import fr.owle.hometracker.modules.HTModule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ListenerService {

    @Autowired
    private StatusListener status;

    @Autowired
    private LogListener logger;

    public void registerListener(HTModule module) {
        final EventManager eventManager = HTAPI.getEvent().getEventManager();
        eventManager.registerEventListener(module, logger, status);
    }

}
