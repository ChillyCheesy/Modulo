package com.chillycheesy.sandbox.controllers;

import com.chillycheesy.modulo.controllers.factory.Request;
import com.chillycheesy.modulo.controllers.factory.ServeResource;

public class EndorClock {

    @Request(path = "/public/**")
    @ServeResource
    public String dial() {
        return "public";
    }

    @Request(path = "/benoit/**")
    @ServeResource
    public String benoit() {
        return "benoit";
    }


    @Request(path = "/ewok/**")
    @ServeResource
    public String ewok() {
        return "ewok";
    }

}
