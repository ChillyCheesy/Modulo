package fr.owle.hometracker.listeners;

import fr.owle.hometracker.HTAPI;
import fr.owle.hometracker.event.OnLoadEvent;
import fr.owle.hometracker.event.OnStartEvent;
import fr.owle.hometracker.event.OnStopEvent;
import fr.owle.hometracker.events.Event;
import fr.owle.hometracker.events.EventHandler;
import fr.owle.hometracker.modules.HTModule;
import fr.owle.hometracker.utils.Listener;
import org.springframework.stereotype.Component;

@Component
public class StatusListener implements Listener {

    @EventHandler(Event.MISERABLE)
    public void onStart(OnStartEvent e){
        final HTModule module = e.getModule();
        HTAPI.getLogger().info(HTAPI.getHTAPI(), "Module \"" + module.getName() + "\" version \"" + module.getVersion() + "\" is starting.");
    }

    @EventHandler(Event.MISERABLE)
    public void onStop(OnStopEvent e){
        final HTModule module = e.getModule();
        HTAPI.getLogger().info(HTAPI.getHTAPI(), "Module \"" + module.getName() + "\" version \"" + module.getVersion() + "\" is stopping.");
    }

    @EventHandler(Event.MISERABLE)
    public void onLoad(OnLoadEvent e){
        final HTModule module = e.getModule();
        HTAPI.getLogger().info(HTAPI.getHTAPI(), "Module \"" + module.getName() + "\" version \"" + module.getVersion() + "\" is loading.");
    }

}
