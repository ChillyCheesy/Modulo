package fr.owle.hometracker.listeners;

import fr.owle.hometracker.event.*;
import fr.owle.hometracker.events.EventHandler;
import fr.owle.hometracker.modules.HTModule;
import fr.owle.hometracker.utils.Listener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class LogListener implements Listener {

    private static final Logger logger = LoggerFactory.getLogger(LogListener.class);

    @EventHandler(-1)
    public void logDisplay(InfoLogEvent event) {
        if (!event.isCanceled())
            logger.info(format(event));
    }

    @EventHandler(-1)
    public void logDisplay(ErrorLogEvent event) {
        if (!event.isCanceled())
            logger.error(format(event));
    }

    @EventHandler(-1)
    public void logDisplay(WarnLogEvent event) {
        if (!event.isCanceled())
            logger.warn(format(event));
    }

    @EventHandler(-1)
    public void logDisplay(DebugLogEvent event) {
        if (!event.isCanceled())
            logger.debug(format(event));
    }

    private String format(LogEvent event) {
        final HTModule module = event.getEmitter();
        final String message = event.getMessage();
        final String moduleName = (module == null ? "null" : module.getName());
        return "[" + moduleName + "]: " + message;
    }

}
