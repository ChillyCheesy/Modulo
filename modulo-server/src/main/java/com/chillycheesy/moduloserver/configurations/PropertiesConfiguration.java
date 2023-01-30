package com.chillycheesy.moduloserver.configurations;

import com.chillycheesy.modulo.config.ConfigurationFactory;
import com.chillycheesy.modulo.config.FileConfigurationFactory;
import com.chillycheesy.modulo.config.PropertiesConfigurationStrategy;
import com.chillycheesy.moduloserver.ServerModule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.File;
import java.io.InputStream;

@Configuration
public class PropertiesConfiguration {

    @Autowired
    private ServerModule module;

    @Bean
    public com.chillycheesy.modulo.config.Configuration moduloProperties() {
        final File configFile = new File("modulo.properties");
        final ClassLoader classLoader = getClass().getClassLoader();
        final InputStream sourceInputStream = classLoader.getResourceAsStream("configurations/modulo.properties");
        final ConfigurationFactory factory = new FileConfigurationFactory(module, configFile, sourceInputStream);
        final PropertiesConfigurationStrategy strategy = new PropertiesConfigurationStrategy();
        final com.chillycheesy.modulo.config.Configuration configuration = factory.createConfiguration(strategy);

        return configuration;
    }

}
