package com.chillycheesy.modulo.controllers;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Controller that check if the path match with the target path.
 * If the path match then return the next controller result.
 *
 * @author ChillyCheesy
 */
public class HttpPathController implements Controller {

    /**
     * Target path.
     */
    private final String path;

    /**
     * Next controller.
     */
    private Controller controller;

    /**
     * Constructor.
     *
     * @param path The target path.
     */
    public HttpPathController(String path) {
        this.path = path;
    }

    /**
     * Apply the controller.
     * If the path match with the target path then return the next controller result.
     * @param request the http request.
     * @param response the http response.
     * @return the response.
     */
    @Override
    public String apply(HttpServletRequest request, HttpServletResponse response) {
        if (request.getRequestURI().equals(path)) {
            return controller.apply(request, response);
        }
        return null;
    }

    /**
     * Set the next controller.
     * @param controller the next controller.
     */
    @Override
    public void setNextStep(Controller controller) {
        this.controller = controller;
    }
}
