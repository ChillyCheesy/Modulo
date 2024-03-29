package com.chillycheesy.moduloserver.configurations;

import com.chillycheesy.modulo.controllers.ControllerContainer;
import com.chillycheesy.modulo.modules.ModuleContainer;
import com.chillycheesy.modulo.utils.Logger;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.chillycheesy.modulo.ModuloAPI;
import com.chillycheesy.modulo.events.EventContainer;
import com.chillycheesy.modulo.modules.ModuleConfig;

import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.*;

@Configuration
public class ModuleConfiguration {

    @Bean
    public Logger log() {
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
    public ControllerContainer controller() {
        return ModuloAPI.getController();
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
            out.flush();
        }
        return tempFile;
    }
}
