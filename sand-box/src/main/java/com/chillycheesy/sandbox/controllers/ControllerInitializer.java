package com.chillycheesy.sandbox.controllers;

import com.chillycheesy.modulo.ModuloAPI;
import com.chillycheesy.modulo.controllers.ModuloControllerManager;
import com.chillycheesy.modulo.modules.ModuloEntity;
import com.chillycheesy.sandbox.SandBoxModule;

public class ControllerInitializer implements ModuloEntity<SandBoxModule> {

    private SandBoxModule module;

    @Override
    public void load(SandBoxModule module) {
        this.module = module;
    }

    @Override
    public void start() {
        final EndorClock endorClock = new EndorClock();
        final ModuloControllerManager manager = ModuloAPI.getController().getManager();
        manager.buildAndRegisterController(module, endorClock);

    }

    @Override
    public void stop() {
        final ModuloControllerManager manager = ModuloAPI.getController().getManager();
        manager.removeAllItems(module);
    }

}
