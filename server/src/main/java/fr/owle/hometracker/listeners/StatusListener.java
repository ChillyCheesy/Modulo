package fr.owle.hometracker.listeners;

import fr.owle.hometracker.HTAPI;
import fr.owle.hometracker.event.OnLoadEvent;
import fr.owle.hometracker.event.OnStartEvent;
import fr.owle.hometracker.event.OnStopEvent;
import fr.owle.hometracker.events.EventHandler;
import fr.owle.hometracker.utils.Listener;
import org.springframework.stereotype.Component;

@Component
public class StatusListener implements Listener {

    @EventHandler(-1)
    public void onStart(OnStartEvent e){
        HTAPI.getLogger().info(HTAPI.getHTAPI(), "Module \"" + e.getModule().getName() + "\" is starting.");
    }

    @EventHandler(-1)
    public void onStop(OnStopEvent e){
        HTAPI.getLogger().info(HTAPI.getHTAPI(), "Module \"" + e.getModule().getName() + "\" is stopping.");
    }

    @EventHandler(-1)
    public void onLoad(OnLoadEvent e){
        HTAPI.getLogger().info(HTAPI.getHTAPI(), "Module \"" + e.getModule().getName() + "\" is loading.");
    }
}
