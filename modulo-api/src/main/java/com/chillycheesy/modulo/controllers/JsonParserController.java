package com.chillycheesy.modulo.controllers;

import com.chillycheesy.modulo.config.Configuration;
import com.fasterxml.jackson.databind.json.JsonMapper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Objects;

/**
 * A controller that parses the result of the next controller into JSON.
 */
public class JsonParserController implements Controller {

    /**
     * The next controller.
     */
    private Controller nextController;

    /**
     * Parses the result of the next controller into Json
     * @param request the http request.
     * @param response the http response.
     * @param configuration the configuration.
     * @return the result of the next controller parsed into JSON.
     *
     * @throws Exception if an error occurs.
     */
    @Override
    public Object apply(HttpServletRequest request, HttpServletResponse response, Configuration configuration) throws Exception {
        if (Objects.nonNull(nextController)) {
            final Object result = nextController.apply(request, response, configuration);
            final JsonMapper mapper = new JsonMapper();
            return mapper.writeValueAsString(result);
        }
        return null;
    }

    /**
     * Sets the next controller.
     * @param controller the next controller.
     */
    @Override
    public void setNextStep(Controller controller) {
        this.nextController = controller;
    }
}
