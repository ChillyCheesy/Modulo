package com.chillycheesy.modulo.controllers;

import java.util.ArrayList;
import java.util.List;

/**
 * Builds a controller.
 * @author ChillyCheesy
 */
public class ControllerBuilder {

    /**
     * The list of controllers.
     * The first controller is the first controller to be called.
     */
    private final List<Controller> controllers;

    /**
     * Creates a new controller builder.
     *
     * @param controllers The List of controller.
     */
    public ControllerBuilder(List<Controller> controllers) {
        this.controllers = controllers;
    }

    /**
     * Creates a new controller builder.
     */
    public ControllerBuilder() {
        this(new ArrayList<>());
    }

    /**
     * Adds a controller to the list of controllers.
     * @param controller The controller to add.
     * @return The controller builder.
     */
    public ControllerBuilder add(Controller controller) {
        this.controllers.add(controller);
        return this;
    }

    /**
     * Builds the controller.
     * @return The controller.
     */
    public Controller build() {
        for (int i = 0; i < controllers.size() - 1; i++) {
            final Controller controller = controllers.get(i);
            final Controller nextController = controllers.get(i + 1);
            controller.setNextStep(nextController);
        }
        return controllers.get(0);
    }

}
