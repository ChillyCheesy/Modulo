package com.chillycheesy.moduloserver.configurations;

import com.chillycheesy.modulo.config.Configuration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.server.ConfigurableWebServerFactory;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.stereotype.Component;

/**
 * @author chillycheesy
 */
@Component
public class ServerPortCustomizer implements WebServerFactoryCustomizer<ConfigurableWebServerFactory> {

    private static final int DEFAULT_PORT = 8080;

    @Autowired
    private Configuration configuration;

    @Override
    public void customize(ConfigurableWebServerFactory factory) {
        final int port = configuration.getInteger("server.port", DEFAULT_PORT);
        factory.setPort(port);
    }

}
