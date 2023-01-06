package com.chillycheesy.moduloserver;

import com.chillycheesy.modulo.config.Configuration;
import com.chillycheesy.modulo.config.ConfigurationLoader;
import com.chillycheesy.modulo.config.FileConfigurationFactory;
import com.chillycheesy.modulo.config.YamlConfigurationStrategy;
import com.chillycheesy.modulo.modules.ModuleConfig;
import com.chillycheesy.modulo.modules.Module;
import com.chillycheesy.moduloserver.services.ModuleService;
import com.chillycheesy.moduloserver.services.ServerListenerService;
import com.chillycheesy.modulo.utils.exception.FileIsNotAModuleDirectoryException;
import com.chillycheesy.modulo.utils.exception.MissingDependenciesModuleException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@Component
public class ServerModule extends Module {

    private static final String CONFIG = "config.yml";
    private static final String MESSAGE_CONFIG = "messages.yml";

    @Autowired private ModuleService moduleService;
    @Autowired private ServerListenerService serverListenerService;
    @Autowired private ModuleConfig serverConfig;

    private Configuration messages;
    private List<Module> modules;

    @PostConstruct
    private void init() throws MissingDependenciesModuleException {
        this.setConfig(serverConfig);
        final ConfigurationLoader defaultLoader = this.loadConfig(CONFIG);
        final ConfigurationLoader messageLoader = this.loadConfig(MESSAGE_CONFIG);
        updateConfigurationLoader(defaultLoader);
        messageLoader.load(this);
        messages = messageLoader.getConfiguration();
        serverListenerService.registerListener(this);
        moduleService.loadAndStartModule(this);
    }

    @Override
    protected void onLoad() throws IOException, FileIsNotAModuleDirectoryException {
        final String prepareLog = messages.getString("loader.load.prepare");
        final String doneLog = messages.getString("loader.load.done");
        info(prepareLog);
        modules = moduleService.loadModules();
        info(String.format(doneLog, modules.size()));
    }

    @Override
    protected void onStart() throws MissingDependenciesModuleException {
        final String prepareLog = messages.getString("loader.start.prepare");
        final String doneLog = messages.getString("loader.start.done");
        info(prepareLog);
        moduleService.startModules(modules);
        info(String.format(doneLog, modules.size()));
    }

    @Override
    protected void onStop() {
        final String prepareLog = messages.getString("loader.stop.prepare");
        final String doneLog = messages.getString("loader.stop.done");
        info(prepareLog);
        moduleService.stopModules(modules);
        info(String.format(doneLog, modules.size()));
    }

    private ConfigurationLoader loadConfig(String configName) {
        final String path = String.format("configurations/%s", configName);
        final InputStream sourceInputStream = getClass().getClassLoader().getResourceAsStream(path);
        final FileConfigurationFactory configurationFactory = new FileConfigurationFactory(this, configName, sourceInputStream);
        final YamlConfigurationStrategy loaderStrategy = new YamlConfigurationStrategy();
        return new ConfigurationLoader(configurationFactory, loaderStrategy);
    }

}
