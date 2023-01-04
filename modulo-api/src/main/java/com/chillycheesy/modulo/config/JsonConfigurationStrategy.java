package com.chillycheesy.modulo.config;

import com.fasterxml.jackson.core.JsonFactory;

public class JsonConfigurationStrategy extends JacksonConfigurationStrategy {

    public JsonConfigurationStrategy() {
        super(new JsonFactory());
    }

}
