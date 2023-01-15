package com.chillycheesy.modulo.event.controllers;

import com.chillycheesy.modulo.controllers.Controller;
import com.chillycheesy.modulo.events.Cancelable;
import com.chillycheesy.modulo.events.CancelableAction;

public class ControllerAppliedEvent extends ControllerEvent implements Cancelable {

    protected CancelableAction cancelableAction;
    protected boolean canceled;

    public ControllerAppliedEvent(Controller controller) {
        super(controller);
    }

    @Override
    public boolean isCanceled() {
        return canceled;
    }

    @Override
    public void setCanceled(boolean cancel) {
        canceled = cancel;
    }

    @Override
    public Cancelable setCancelableAction(CancelableAction action) {
        this.cancelableAction = action;
        return this;
    }

    @Override
    public CancelableAction getCancelableAction() {
        return cancelableAction;
    }
}
