package com.chillycheesy.moduloserver.configurations;

import com.chillycheesy.modulo.modules.ModuleContainer;
import com.chillycheesy.modulo.pages.PageContainer;
import com.chillycheesy.modulo.signals.SignalContainer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.chillycheesy.modulo.ModuloAPI;
import com.chillycheesy.modulo.events.EventContainer;
import com.chillycheesy.modulo.modules.ModuleConfig;
import com.chillycheesy.modulo.utils.Log;
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
    public PageContainer page() {
        return ModuloAPI.getPage();
    }

    @Bean
    public SignalContainer signal() {
        return ModuloAPI.getSignal();
    }

    @Bean
    public ModuleConfig serverConfig() throws IOException {
        final File file = inputStreamToFile(getClass().getClassLoader().getResourceAsStream("module.yml"));
        final ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
        mapper.findAndRegisterModules();
        return mapper.readValue(file, ModuleConfig.class);
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
