package com.chillycheesy.hometracker.configurations;

import com.chillycheesy.hometracker.modules.ModuleContainer;
import com.chillycheesy.hometracker.signals.SignalContainer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.chillycheesy.hometracker.ModuloAPI;
import com.chillycheesy.hometracker.events.EventContainer;
import com.chillycheesy.hometracker.modules.HTModuleConfig;
import com.chillycheesy.hometracker.utils.Log;
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
