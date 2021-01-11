package fr.owle.hometracker;

import fr.owle.hometracker.events.EventContainer;
import fr.owle.hometracker.listener.DefaultListenerRegisterer;
import fr.owle.hometracker.modules.HTModule;
import fr.owle.hometracker.modules.HTModuleAdapter;
import fr.owle.hometracker.modules.ModuleContainer;
import fr.owle.hometracker.page.DefaultPagesRegisterer;
import fr.owle.hometracker.page.UiPage;
import fr.owle.hometracker.pages.PageContainer;
import fr.owle.hometracker.pages.PageManager;
import fr.owle.hometracker.signals.SignalContainer;
import fr.owle.hometracker.utils.Log;
import fr.owle.hometracker.utils.exception.HTModuleNotFoundException;

/**
 * HTAPI was the Central Class of the API.
 * With it, you can have an access to all API's logic sections.
 *
 * @author hénouille
 */
public class HTAPI extends HTModuleAdapter {

    /**
     * Module section.
     */
    private static ModuleContainer module;

    /**
     * Event section.
     */
    private static EventContainer event;
    /**
     * Signal section.
     */
    private static SignalContainer signal;

    /**
     * Page manager
     */
    private static PageContainer page;
    /**
     * Log section.
     */
    private static Log logger;

    private static HTModule serverModule;

    public static void init(HTModule server) {
        if (HTAPI.getServerModule() == null) {
            serverModule = server;
        }
    }

    public static HTModule getServerModule() {
        return serverModule;
    }

    @Override
    protected void onLoad() throws Exception {
        super.onLoad();
        final DefaultListenerRegisterer defaultListenerRegisterer = new DefaultListenerRegisterer();
        final DefaultPagesRegisterer defaultPagesRegisterer = new DefaultPagesRegisterer();
        defaultListenerRegisterer.register(this);
        defaultPagesRegisterer.register(this);
    }

    /**
     * Getter for Module logic section.
     *
     * @return The Module section.
     */
    public static ModuleContainer getModule() {
        return module = module == null ? new ModuleContainer() : module;
    }

    /**
     * Getter for Event logic section.
     *
     * @return The Event section.
     */
    public static EventContainer getEvent() {
        return event = event == null ? new EventContainer() : event;
    }

    /**
     * Getter for Signal logic section.
     *
     * @return The Signal section.
     */
    public static SignalContainer getSignal() {
        return signal = signal == null ? new SignalContainer() : signal;
    }

    /**
     * Getter for Signal logic section.
     *
     * @return The Signal section.
     */
    public static PageContainer getPage() {
        return page = page == null ? new PageContainer() : page;
    }

    /**
     * Getter for Logger logic section.
     *
     * @return The Logger section.
     */
    public static Log getLogger() {
        return logger = logger == null ? new Log(getEvent()) : logger;
    }

    public static HTModule getHTAPI() {
        try {
            return getModule().getModuleManager().getModule("HomeTracker");
        } catch (HTModuleNotFoundException e) {
            return getServerModule();
        }
    }
}