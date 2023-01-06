package com.chillycheesy.modulo.config;

import java.io.IOException;

public interface ConfigurationFactory {

        Configuration createConfiguration(ConfigurationLoaderStrategy loaderStrategy);

        void saveConfiguration(Configuration configuration, ConfigurationLoaderStrategy loaderStrategy);

}
