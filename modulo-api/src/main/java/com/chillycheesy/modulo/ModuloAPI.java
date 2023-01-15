package com.chillycheesy.modulo;

import com.chillycheesy.modulo.controllers.ControllerContainer;
import com.chillycheesy.modulo.events.EventContainer;
import com.chillycheesy.modulo.modules.ModuleContainer;
import com.chillycheesy.modulo.pages.PageContainer;
import com.chillycheesy.modulo.signals.SignalContainer;
import com.chillycheesy.modulo.utils.Logger;

/**
 * ModuloAPI was the Central Class of the API.
 * With it, you can have access to all APIs logic sections.
 *
 * @author hénouille
 */
public class ModuloAPI {

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
     * Page manager.
     */
    private static PageContainer page;
    /**
     * Controller container.
     */
    private static ControllerContainer controller;
    /**
     * Logger section.
     */
    private static Logger logger;

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
    public static Logger getLogger() {
        return logger = logger == null ? new Logger(getEvent()) : logger;
    }

    /**
     * Getter for Controller logic section.
     *
     * @return The Controller section.
     */
    public static ControllerContainer getController() {
        return controller = controller == null ? new ControllerContainer() : controller;
    }
}