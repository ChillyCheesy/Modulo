package com.chillycheesy.modulo.event.controllers;

import com.chillycheesy.modulo.controllers.Controller;

public class ControllerCanceledEvent extends ControllerEvent {
    public ControllerCanceledEvent(Controller controller) {
        super(controller);
    }

}
