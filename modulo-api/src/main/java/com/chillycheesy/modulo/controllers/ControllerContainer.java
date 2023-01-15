package com.chillycheesy.modulo.controllers;

public class ControllerContainer {

    private static ModuloControllerManager manager;

    public static ModuloControllerManager getManager() {
        return manager = manager == null ? new ModuloControllerManager() : manager;
    }

}
