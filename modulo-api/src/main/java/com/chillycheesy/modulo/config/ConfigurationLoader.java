package com.chillycheesy.modulo.config;

import com.chillycheesy.modulo.modules.Module;
import com.chillycheesy.modulo.modules.ModuleEntityAdapter;

import java.util.Objects;

public class ConfigurationLoader extends ModuleEntityAdapter {

    private static final String DEFAULT_CONFIG_PATH = "config.yml";

    protected ConfigurationFactory configurationFactory;
    protected ConfigurationLoaderStrategy loaderStrategy;
    protected Configuration defaultConfiguration;

    protected boolean autoSave = true;

    public ConfigurationLoader(ConfigurationFactory configurationFactory, ConfigurationLoaderStrategy loaderStrategy) {
        this.configurationFactory = configurationFactory;
        this.loaderStrategy = loaderStrategy;
    }

    public ConfigurationLoader(Module module, String configPath, ConfigurationLoaderStrategy loaderStrategy) {
        this(new FileConfigurationFactory(module, configPath), loaderStrategy);
    }

    public ConfigurationLoader(Module module, String configPath) {
        this(module, configPath, new YamlConfigurationStrategy());
    }

    public ConfigurationLoader(Module module) {
        this(module, DEFAULT_CONFIG_PATH);
    }

    public ConfigurationLoader() { }

    @Override
    public void load(Module module) {
        if (Objects.isNull(configurationFactory) || Objects.isNull(loaderStrategy)) {
            final ConfigurationLoader configLoader = new ConfigurationLoader(module);
            updateConfigurationLoader(configLoader);
        }
        defaultConfiguration = configurationFactory.createConfiguration(loaderStrategy);
    }

    @Override
    public void stop() {
        if (autoSave)
            configurationFactory.saveConfiguration(defaultConfiguration, loaderStrategy);
    }

    public void updateConfigurationLoader(ConfigurationLoader configLoader) {
        this.configurationFactory = configLoader.configurationFactory;
        this.loaderStrategy = configLoader.loaderStrategy;
    }

    public void enableAutoSave(boolean autoSave) {
        this.autoSave = autoSave;
    }

    public Configuration getConfiguration() {
        return defaultConfiguration;
    }
}
