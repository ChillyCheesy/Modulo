package com.chillycheesy.hometracker.services;

import com.chillycheesy.hometracker.ServerModule;
import com.chillycheesy.hometracker.listeners.LogListener;
import com.chillycheesy.hometracker.events.EventContainer;
import com.chillycheesy.hometracker.listener.ListenerManager;
import com.chillycheesy.hometracker.listeners.StatusListener;
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
