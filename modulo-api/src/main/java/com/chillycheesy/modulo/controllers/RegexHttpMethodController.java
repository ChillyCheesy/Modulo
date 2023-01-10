package com.chillycheesy.modulo.controllers;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Controller that check if the http methods match with the target regex.
 * Check if the method match with the target regex then return next step result.
 *
 * @author ChillyCheesy
 */
public class RegexHttpMethodController implements Controller {

    /**
     * The target regex.
     */
    private final String regex;

    /**
     * The next controller.
     */
    private Controller controller;

    /**
     * Constructor.
     *
     * @param regex the target regex.
     */
    public RegexHttpMethodController(String regex) {
        this.regex = regex;
    }

    /**
     * Apply the controller.
     * If the method match with the target regex then return next step result.
     * @param request the http request.
     * @param response the http response.
     * @return the response.
     */
    @Override
    public String apply(HttpServletRequest request, HttpServletResponse response) {
        final Pattern pattern = Pattern.compile(regex);
        final Matcher matcher = pattern.matcher(request.getMethod());
        if (matcher.matches() && controller != null) {
            return controller.apply(request, response);
        }
        return null;
    }

    /**
     * Define the next controller.
     * @param controller The next controller.
     */
    @Override
    public void setNextStep(Controller controller) {
        this.controller = controller;
    }
}
