package com.chillycheesy.hometracker.listeners;

import com.chillycheesy.hometracker.ModuloAPI;
import com.chillycheesy.hometracker.event.OnLoadEvent;
import com.chillycheesy.hometracker.event.OnStartEvent;
import com.chillycheesy.hometracker.event.OnStopEvent;
import com.chillycheesy.hometracker.events.Event;
import com.chillycheesy.hometracker.events.EventHandler;
import com.chillycheesy.hometracker.modules.Module;
import com.chillycheesy.hometracker.listener.Listener;
import org.springframework.stereotype.Component;

@Component
public class StatusListener implements Listener {

    @EventHandler(Event.CANCELABLE)
    public void onLoad(OnLoadEvent e) {
        final Module module = e.getModule();
        ModuloAPI.getLogger().info(module, "Module \"" + module.getName() + "\" version \"" + module.getVersion() + "\" is loading.");
    }

    @EventHandler(Event.CANCELABLE)
    public void onStart(OnStartEvent e) {
        final Module module = e.getModule();
        ModuloAPI.getLogger().info(module, "Module \"" + module.getName() + "\" version \"" + module.getVersion() + "\" is starting.");
    }

    @EventHandler(Event.CANCELABLE)
    public void onStop(OnStopEvent e) {
        final Module module = e.getModule();
        ModuloAPI.getLogger().info(module, "Module \"" + module.getName() + "\" version \"" + module.getVersion() + "\" is stopping.");
    }

}
