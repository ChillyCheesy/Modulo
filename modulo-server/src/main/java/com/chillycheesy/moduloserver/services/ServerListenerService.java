package com.chillycheesy.moduloserver.services;

import com.chillycheesy.moduloserver.ServerModule;
import com.chillycheesy.moduloserver.listeners.LogListener;
import com.chillycheesy.modulo.events.EventContainer;
import com.chillycheesy.modulo.listener.ListenerManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ServerListenerService {

    @Autowired private EventContainer events;
    @Autowired private LogListener logListener;

    public void registerListener(ServerModule serverModule) {
        final ListenerManager listenerManager = events.getEventManager();
        listenerManager.registerItem(serverModule, logListener);
    }

}
