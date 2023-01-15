package com.chillycheesy.sandbox.controllers;

import com.chillycheesy.modulo.controllers.factory.Request;
import com.chillycheesy.modulo.controllers.factory.ServeResource;

public class EndorClock {

    @Request(path = "/endor/clock")
    @ServeResource
    public String dial() {
        return "benoit";
    }

}
