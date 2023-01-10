package com.chillycheesy.modulo.controllers;

import java.util.ArrayList;
import java.util.List;

public class ControllerBuilder {

    private final List<Controller> controller;

    public ControllerBuilder(List<Controller> controller) {
        this.controller = controller;
    }

    public ControllerBuilder() {
        this(new ArrayList<>());
    }

    public ControllerBuilder add(Controller controller) {
        this.controller.add(controller);
        return this;
    }

    public Controller build() {
        for (int i = 0; i < controller.size() - 1; i++) {
            controller.get(i).setNextStep(controller.get(i + 1));
        }
        return controller.get(0);
    }

}
