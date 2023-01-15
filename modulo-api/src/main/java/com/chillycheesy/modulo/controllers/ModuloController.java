package com.chillycheesy.modulo.controllers;

import com.chillycheesy.modulo.config.Configuration;
import com.chillycheesy.modulo.utils.Priority;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Controller that represent a root {@link Controller}.
 *
 *
 * @author ChillyCheesy
 */
public class ModuloController implements Controller {

    protected String name;
    protected int priority;
    protected Controller controller;

    public ModuloController(String name, int priority) {
        this.name = name;
        this.priority = priority;
    }

    public ModuloController(String name) {
        this(name, Priority.NEUTRAL);
    }

    @Override
    public Object apply(HttpServletRequest request, HttpServletResponse response, Configuration configuration) throws Exception {
        return controller.apply(request, response, configuration);
    }

    @Override
    public void setNextStep(Controller controller) {
        this.controller = controller;
    }

    public String getName() {
        return name;
    }

    public int getPriority() {
        return priority;
    }

    public Controller getController() {
        return controller;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }
}
