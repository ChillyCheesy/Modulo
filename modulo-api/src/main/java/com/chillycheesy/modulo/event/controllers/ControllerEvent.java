package com.chillycheesy.modulo.event.controllers;

import com.chillycheesy.modulo.controllers.Controller;
import com.chillycheesy.modulo.events.Cancelable;
import com.chillycheesy.modulo.events.CancelableAction;
import com.chillycheesy.modulo.events.Event;

public class ControllerEvent extends Event {

    protected Controller controller;

    public ControllerEvent(Controller controller) {
        this.controller = controller;
    }

    public Controller getController() {
        return controller;
    }

    public void setController(Controller controller) {
        this.controller = controller;
    }

}
