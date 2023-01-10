package com.chillycheesy.sandbox.pages;

import com.chillycheesy.modulo.pages.TemplatePage;
import com.chillycheesy.modulo.pages.factory.*;

public class JokePage {

//    @GetRequest("/joke")
//    @ResponseResource
//    public String joke() {
//        return "public";
//    }
//
//    @GetRequest("/joke/uppercase")
//    @ResponseBody
//    public String uppercase(@HttpParam String input) {
//        return input.toUpperCase();
//    }

    @GetRequest("/joke/uppercase/{input}")
    @PageType(TemplatePage.class)
    @ResponseBody
    public String uppercasePathVariable(@PathVariable String input) {
        System.out.println("input = " + input);
        return input.toUpperCase();
    }

}
