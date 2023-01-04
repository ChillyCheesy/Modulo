package com.chillycheesy.modulo.config;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public interface ConfigurationLoaderStrategy {

    Configuration loadConfiguration(InputStream inputStream) throws IOException;

    void saveConfiguration(Configuration configuration, OutputStream outputStream) throws IOException;
}
