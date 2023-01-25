package com.chillycheesy.modulo.config;

import com.chillycheesy.modulo.modules.Module;
import com.chillycheesy.modulo.modules.ModuleEntityAdapter;

import java.util.Objects;

public class ConfigurableEntity extends ModuleEntityAdapter {

    private static final String DEFAULT_CONFIG_PATH = "config.yml";

    protected ConfigurationFactory configurationFactory;
    protected ConfigurationLoaderStrategy loaderStrategy;
    protected Configuration defaultConfiguration;

    protected boolean autoSave = true;

    public ConfigurableEntity(ConfigurationLoaderStrategy loaderStrategy) {
        this.loaderStrategy = loaderStrategy;
    }

    public ConfigurableEntity() {
        this(new YamlConfigurationStrategy());
    }

    @Override
    public void load(Module module) {
        configurationFactory = Objects.isNull(configurationFactory) ? new FileConfigurationFactory(module, DEFAULT_CONFIG_PATH) : configurationFactory;
        defaultConfiguration = Objects.isNull(defaultConfiguration) ? configurationFactory.createConfiguration(loaderStrategy) : defaultConfiguration;
    }

    @Override
    public void stop() {
        if (autoSave) configurationFactory.saveConfiguration(defaultConfiguration, loaderStrategy);
    }

    public void enableAutoSave(boolean autoSave) {
        this.autoSave = autoSave;
    }

    public Configuration getConfiguration() {
        return defaultConfiguration;
    }

    public void setConfigurationFactory(ConfigurationFactory configurationFactory) {
        this.configurationFactory = configurationFactory;
    }

    public void setConfiguration(Configuration configuration) {
        this.defaultConfiguration = configuration;
    }

    public ConfigurationFactory getConfigurationFactory() {
        return configurationFactory;
    }

    public ConfigurationLoaderStrategy getLoaderStrategy() {
        return loaderStrategy;
    }
}
