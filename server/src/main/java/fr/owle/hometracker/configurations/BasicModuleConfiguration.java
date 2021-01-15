package fr.owle.hometracker.configurations;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import fr.owle.hometracker.HTAPI;
import fr.owle.hometracker.ServerModule;
import fr.owle.hometracker.modules.*;
import org.apache.commons.io.IOUtils;
import org.springframework.boot.loader.LaunchedURLClassLoader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.*;
import java.net.URL;

@Configuration
public class BasicModuleConfiguration {

    @Bean
    public ServerModule serverModule() throws IOException {
        final ServerModule serverModule = new ServerModule();
        serverModule.setConfig(getModuleConfig());
        return serverModule;
    }

    @Bean
    public HTAPI htapi() throws IOException {
        final String version = getModuleConfig().getVersion();
        final File homeTracker = inputStreamToFile(getClass().getClassLoader().getResourceAsStream("api/hometracker-api-" + version + ".jar"), "hometracker", ".jar");
        final HTAPI htapi = (HTAPI) ModuleBuilder.build(homeTracker, new LaunchedURLClassLoader(new URL[]{homeTracker.toURI().toURL()}, getClass().getClassLoader()));
        homeTracker.deleteOnExit();
        return htapi;
    }

    private HTModuleConfig getModuleConfig() throws IOException {
        final File file = inputStreamToFile(getClass().getClassLoader().getResourceAsStream("module-server.yml"), "module", "server.yml");
        final ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
        mapper.findAndRegisterModules();
        return mapper.readValue(file, HTModuleConfig.class);
    }

    private File inputStreamToFile(InputStream in, String prefix, String suffix) throws IOException {
        final File tempFile = File.createTempFile(prefix, suffix);
        tempFile.deleteOnExit();
        try (FileOutputStream out = new FileOutputStream(tempFile)) {
            IOUtils.copy(in, out);
        }
        return tempFile;
    }

}
