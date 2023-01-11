package com.chillycheesy.modulo.controllers;

import com.chillycheesy.modulo.config.Configuration;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SimpleController implements Controller {

    private final String content;

    public SimpleController(String content) {
        this.content = content;
    }

    @Override
    public Object apply(HttpServletRequest request, HttpServletResponse response, Configuration configuration) {
        return content;
    }

    @Override
    public void setNextStep(Controller controller) { }

}
