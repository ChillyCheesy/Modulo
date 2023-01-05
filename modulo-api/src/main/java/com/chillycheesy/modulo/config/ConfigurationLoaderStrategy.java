package com.chillycheesy.modulo.config;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Loads and saves a configuration from Input and Output Stream.
 *
 * @author chillycheesy
 */
public interface ConfigurationLoaderStrategy {

    /**
     * Loads a configuration from an input stream.
     * @param inputStream The input stream to load from.
     * @return The loaded configuration.
     * @throws IOException If an error occurs while loading the configuration.
     */
    Configuration loadConfiguration(InputStream inputStream) throws IOException;

    /**
     * Saves a configuration to an output stream.
     * @param configuration The configuration to save.
     * @param outputStream The output stream to save to.
     * @throws IOException If an error occurs while saving the configuration.
     */
    void saveConfiguration(Configuration configuration, OutputStream outputStream) throws IOException;
}
