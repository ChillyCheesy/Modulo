package com.owle.hometracker.configurations;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.owle.hometracker.ModuloAPI;
import com.owle.hometracker.ServerModule;
import com.owle.hometracker.events.EventContainer;
import com.owle.hometracker.modules.HTModuleConfig;
import com.owle.hometracker.modules.ModuleContainer;
import com.owle.hometracker.signals.SignalContainer;
import com.owle.hometracker.utils.Log;
import org.apache.commons.io.IOUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

@Configuration
public class ModuleConfiguration {

    @Bean
    public Log log() {
        return ModuloAPI.getLogger();
    }

    @Bean
    public EventContainer event() {
        return ModuloAPI.getEvent();
    }

    @Bean
    public ModuleContainer module() {
        return ModuloAPI.getModule();
    }

    @Bean
    public SignalContainer signal() {
        return ModuloAPI.getSignal();
    }

    @Bean
    public HTModuleConfig serverConfig() throws IOException {
        final File file = inputStreamToFile(getClass().getClassLoader().getResourceAsStream("module.yml"));
        final ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
        mapper.findAndRegisterModules();
        return mapper.readValue(file, HTModuleConfig.class);
    }

    private File inputStreamToFile(InputStream in) throws IOException {
        final File tempFile = File.createTempFile("module", "server.yml");
        tempFile.deleteOnExit();
        try (FileOutputStream out = new FileOutputStream(tempFile)) {
            IOUtils.copy(in, out);
        }
        return tempFile;
    }
}
