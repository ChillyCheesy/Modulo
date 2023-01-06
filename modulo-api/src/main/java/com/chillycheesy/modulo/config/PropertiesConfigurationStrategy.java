package com.chillycheesy.modulo.config;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;

/**
 * Loads and saves a configuration from a properties file.<br>
 * <strong>NOTE: This strategy don't support the configuration properties inside a List</strong>
 *
 * @author chillycheesy
 */
public class PropertiesConfigurationStrategy implements ConfigurationLoaderStrategy {

    /**
     * Loads a configuration from an input stream.
     * @param inputStream The input stream to load from.
     * @return The loaded configuration.
     * @throws IOException If an error occurs while loading the configuration.
     */
    @Override
    public Configuration loadConfiguration(InputStream inputStream) throws IOException {
        final Configuration configuration = new Configuration();
        final Properties properties = new Properties();
        properties.load(inputStream);
        properties.forEach((key, value) -> configuration.setDefault(key.toString(), value));
        return configuration;
    }

    /**
     * Saves a configuration to an output stream.
     * @param configuration The configuration to save.
     * @param outputStream The output stream to save to.
     * @throws IOException If an error occurs while saving the configuration.
     */
    @Override
    public void saveConfiguration(Configuration configuration, OutputStream outputStream) throws IOException {
        final Properties properties = new Properties();
        configuration.forEach(properties::setProperty);
        properties.store(outputStream, null);
    }

}
