package com.chillycheesy.modulo.controllers;

import com.chillycheesy.modulo.config.Configuration;

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
     * @param configuration the configuration.
     * @return the response.
     */
    @Override
    public Object apply(HttpServletRequest request, HttpServletResponse response, Configuration configuration) throws Exception {
        if (request.getMethod().equals(method) && controller != null) {
            return controller.apply(request, response, configuration);
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
