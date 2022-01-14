package com.owle.hometracker.services;

import com.owle.hometracker.ServerModule;
import com.owle.hometracker.events.EventContainer;
import com.owle.hometracker.listener.ListenerManager;
import com.owle.hometracker.listeners.LogListener;
import com.owle.hometracker.listeners.StatusListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ServerListenerService {

    @Autowired private EventContainer events;
    @Autowired private LogListener logListener;
    @Autowired private StatusListener statusListener;

    public void registerListener(ServerModule serverModule) {
        final ListenerManager listenerManager = events.getEventManager();
        listenerManager.registerItem(serverModule, logListener, statusListener);
    }

}
