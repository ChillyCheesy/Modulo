package com.chillycheesy.modulo.config;

import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

public class YamlConfigurationStrategy extends JacksonConfigurationStrategy {

    public YamlConfigurationStrategy() {
        super(new YAMLFactory());
    }

}
