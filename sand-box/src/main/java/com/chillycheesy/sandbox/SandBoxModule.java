package com.chillycheesy.sandbox;

import com.chillycheesy.modulo.modules.Module;
import com.chillycheesy.sandbox.controllers.ControllerInitializer;

public class SandBoxModule extends Module {

    private ControllerInitializer controllerInitializer;

    @Override
    protected void onLoad() {
        controllerInitializer = new ControllerInitializer();
        controllerInitializer.load(this);
    }

    @Override
    protected void onStart() {
        controllerInitializer.start();
        /*info(defaultConfiguration.getString("message"));*/
    }

    @Override
    protected void onStop() {
        controllerInitializer.stop();
    }

}
