package com.chillycheesy.modulo.controllers;

import com.chillycheesy.modulo.config.Configuration;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Iterator;
import java.util.Objects;

/**
 * Controller that register all parameters name of the request inside the configuration.
 * Each parameter name is register as key inside the "param-variable" section.
 *
 * @author ChillyCheesy
 */
public class HttpParamVariableController implements Controller {

    public static final String PARAM_VARIABLE_SECTION = "param-variable";

    private Controller nextController;

    @Override
    public Object apply(HttpServletRequest request, HttpServletResponse response, Configuration configuration) throws Exception {
        final Iterator<String> parameterNames = request.getParameterNames().asIterator();
        while (parameterNames.hasNext()) {
            final String parameterName = parameterNames.next();
            final String parameterValue = request.getParameter(parameterName);
            configuration.set(String.format("%s.%s", PARAM_VARIABLE_SECTION, parameterName), parameterValue);
        }
        return Objects.isNull(nextController) ? null : nextController.apply(request, response, configuration);
    }

    @Override
    public void setNextStep(Controller controller) {
        this.nextController = controller;
    }
}
