package com.chillycheesy.modulo.controllers;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Controller that test the http methods.
 * Check if the method match with the target method.
 */
public class HttpMethodController implements Controller {

    /**
     * Target method
     */
    private final String method;

    /**
     * Next controller
     */
    private Controller controller;

    /**
     * Constructor.
     *
     * @param method the target method.
     */
    public HttpMethodController(String method) {
        this.method = method;
    }

    /**
     * Apply the controller.
     * If the method match with the target method then return the next controller result.
     * @param request the http request.
     * @param response the http response.
     * @return the response.
     */
    @Override
    public String apply(HttpServletRequest request, HttpServletResponse response) {
        if (request.getMethod().equals(method) && controller != null) {
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
