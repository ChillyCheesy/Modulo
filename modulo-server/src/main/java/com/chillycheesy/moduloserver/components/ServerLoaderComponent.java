package com.chillycheesy.moduloserver.components;

import com.chillycheesy.modulo.config.*;
import com.chillycheesy.modulo.modules.ModuleConfig;
import com.chillycheesy.modulo.utils.exception.MissingDependenciesModuleException;
import com.chillycheesy.moduloserver.ServerModule;
import com.chillycheesy.moduloserver.services.ModuleService;
import com.chillycheesy.moduloserver.services.ServerListenerService;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.*;
import java.util.Objects;

@Component
public class ServerLoaderComponent {

    @Autowired
    private ModuleService moduleService;

    @Autowired
    private ServerListenerService serverListenerService;

    @Autowired
    private ModuleConfig serverConfig;

    @Autowired
    private ServerModule module;

    @PostConstruct
    public void onAppStart() throws MissingDependenciesModuleException, IOException {
        module.setConfig(serverConfig);
        final Configuration defaultConfiguration = loadConfiguration("config.yml");
        final Configuration messageConfiguration = loadConfiguration("messages.yml");
        final Configuration configuration = new Configuration(defaultConfiguration, messageConfiguration);
        configuration.forEach("properties", System::setProperty);
        module.setConfiguration(configuration);
        serverListenerService.registerListener(module);
        moduleService.loadAndStartModule(module);
    }

    private Configuration loadConfiguration(String configName) throws IOException {
        final ClassLoader classLoader = getClass().getClassLoader();
        final String configPath = String.format("configurations/%s", configName);
        final InputStream inputStream = classLoader.getResourceAsStream(configPath);
        final FileConfigurationFactory configurationFactory = new FileConfigurationFactory(module, configName, inputStream);
        final ConfigurationLoaderStrategy configurationLoaderStrategy = new YamlConfigurationStrategy();
        return configurationFactory.createConfiguration(configurationLoaderStrategy);
    }

}
