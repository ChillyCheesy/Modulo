package com.chillycheesy.sandbox.pages;

import com.chillycheesy.modulo.pages.factory.*;

public class JokePage {

    @GetRequest("/joke")
    @ResponseResource
    public String joke() {
        return "public";
    }

    @GetRequest("/joke/uppercase")
    @ResponseBody
    public String uppercase(@HttpParam String input) {
        return input.toUpperCase();
    }

}
