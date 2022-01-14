package com.chillycheesy.hometracker.listeners;

import com.chillycheesy.hometracker.event.*;
import com.owle.hometracker.event.*;
import com.chillycheesy.hometracker.events.Event;
import com.chillycheesy.hometracker.events.EventHandler;
import com.chillycheesy.hometracker.modules.Module;
import com.chillycheesy.hometracker.listener.Listener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class LogListener implements Listener {

    private static final Logger logger = LoggerFactory.getLogger(LogListener.class);

    @EventHandler(Event.CANCELABLE)
    public void logDisplay(InfoLogEvent event) {
        logger.info(format(event));
    }

    @EventHandler(Event.CANCELABLE)
    public void logDisplay(ErrorLogEvent event) {
        logger.error(format(event));
    }

    @EventHandler(Event.CANCELABLE)
    public void logDisplay(WarnLogEvent event) {
        logger.warn(format(event));
    }

    @EventHandler(Event.CANCELABLE)
    public void logDisplay(DebugLogEvent event) {
        logger.debug(format(event));
    }

    private String format(LogEvent event) {
        final Module module = event.getEmitter();
        final String message = event.getMessage();
        final String moduleName = (module == null ? "null" : module.getName());
        return "[" + moduleName + "]: " + message;
    }

}
