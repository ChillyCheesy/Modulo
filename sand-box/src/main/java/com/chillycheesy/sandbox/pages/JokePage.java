package com.chillycheesy.sandbox.pages;

import com.chillycheesy.modulo.pages.factory.GetRequest;
import com.chillycheesy.modulo.pages.factory.ResponseResource;

public class JokePage {

    @GetRequest("/joke")
    @ResponseResource
    public String joke() {
        return "public";
    }

}
