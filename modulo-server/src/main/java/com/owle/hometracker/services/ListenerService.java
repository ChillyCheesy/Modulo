package com.owle.hometracker.services;

import com.owle.hometracker.listeners.LogListener;
import com.owle.hometracker.listeners.StatusListener;
import com.owle.hometracker.ModuloAPI;
import com.owle.hometracker.events.EventManager;
import com.owle.hometracker.modules.Module;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ListenerService {

    @Autowired
    private StatusListener status;

    @Autowired
    private LogListener logger;

    public void registerListener(Module module) {
        final EventManager eventManager = ModuloAPI.getEvent().getEventManager();
        eventManager.registerEventListener(module, logger, status);
    }

}
