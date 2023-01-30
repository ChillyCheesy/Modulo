package com.chillycheesy.modulo.controllers;

import com.chillycheesy.modulo.config.Configuration;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Defines the methods that a controller must implement.
 * A Controller is a class that handles a http request and returns a response.
 * if the response is null then the request is forwarded to the next controller.
 *
 * @author ChillyCheesy
 */
public interface Controller {

    /**
     * Call the method that handles the request.
     *
     * @param request the http request.
     * @param response the http response.
     * @param configuration the configuration.
     * @return the response.
     */
    Object apply(HttpServletRequest request, HttpServletResponse response, Configuration configuration) throws Exception;

    /**
     * Set the next controller.
     * @param controller The next controller.
     */
    void setNextStep(Controller controller);

}
