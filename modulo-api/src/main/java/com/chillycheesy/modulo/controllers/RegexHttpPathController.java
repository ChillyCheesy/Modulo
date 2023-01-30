package com.chillycheesy.modulo.controllers;

import com.chillycheesy.modulo.config.Configuration;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Controller that test if the http path match with the regex.
 * If the path match with the regex then return the next controller result.
 */
public class RegexHttpPathController implements Controller {

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
    public RegexHttpPathController(String regex) {
        this.regex = regex;
    }

    /**
     * Apply the controller.
     * If the path match with the regex then return the next controller result.
     * @param request the http request.
     * @param response the http response.
     * @param configuration the configuration.
     * @return the response.
     */
    @Override
    public Object apply(HttpServletRequest request, HttpServletResponse response, Configuration configuration) throws Exception {
        final Pattern pattern = Pattern.compile(regex);
        final Matcher matcher = pattern.matcher(request.getRequestURI());
        if (matcher.matches() && controller != null) {
            return controller.apply(request, response, configuration);
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
