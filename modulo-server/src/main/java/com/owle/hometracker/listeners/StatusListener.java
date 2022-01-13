package com.owle.hometracker.listeners;

import com.owle.hometracker.ModuloAPI;
import com.owle.hometracker.event.OnLoadEvent;
import com.owle.hometracker.event.OnStartEvent;
import com.owle.hometracker.event.OnStopEvent;
import com.owle.hometracker.events.Event;
import com.owle.hometracker.events.EventHandler;
import com.owle.hometracker.modules.Module;
import com.owle.hometracker.listener.Listener;
import org.springframework.stereotype.Component;

@Component
public class StatusListener implements Listener {

    @EventHandler(Event.MISERABLE)
    public void onStart(OnStartEvent e){
        final Module module = e.getModule();
        ModuloAPI.getLogger().info(module, "Module \"" + module.getName() + "\" version \"" + module.getVersion() + "\" is starting.");
    }

    @EventHandler(Event.MISERABLE)
    public void onStop(OnStopEvent e){
        final Module module = e.getModule();
        ModuloAPI.getLogger().info(module, "Module \"" + module.getName() + "\" version \"" + module.getVersion() + "\" is stopping.");
    }

    @EventHandler(Event.MISERABLE)
    public void onLoad(OnLoadEvent e) {
        final Module module = e.getModule();
        ModuloAPI.getLogger().info(module, "Module \"" + module.getName() + "\" version \"" + module.getVersion() + "\" is loading.");
    }

}
