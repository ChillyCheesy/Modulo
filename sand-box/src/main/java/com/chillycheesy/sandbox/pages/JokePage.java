package com.chillycheesy.sandbox.pages;

import com.chillycheesy.modulo.modules.Module;
import com.chillycheesy.modulo.pages.factory.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class JokePage {

    private Module module;

    public JokePage(Module module) {
        this.module = module;
    }

    @GetRequest("/joke/uppercase/{input}")
    @ResponseBody
    public String uppercasePathVariable(@PathVariable String input) {
        System.out.println("input = " + input);
        return input.toUpperCase();
    }

    @GetRequest("/benoit")
    @ResponseBody
    public String benoit(HttpServletRequest request, HttpServletResponse response) {
        return "Benoit";
    }

}
