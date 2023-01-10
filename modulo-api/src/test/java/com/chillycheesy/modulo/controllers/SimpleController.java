package com.chillycheesy.modulo.controllers;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SimpleController implements Controller {

    private final String content;

    public SimpleController(String content) {
        this.content = content;
    }

    @Override
    public String apply(HttpServletRequest request, HttpServletResponse response) {
        return content;
    }

    @Override
    public void setNextStep(Controller controller) { }

}
