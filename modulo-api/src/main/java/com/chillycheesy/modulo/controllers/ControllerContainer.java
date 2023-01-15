package com.chillycheesy.modulo.controllers;

public class ControllerContainer {

    private ModuloControllerManager manager;

    public ModuloControllerManager getManager() {
        return manager = manager == null ? new ModuloControllerManager() : manager;
    }

}
