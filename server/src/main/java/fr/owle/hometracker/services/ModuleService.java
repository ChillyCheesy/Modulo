package fr.owle.hometracker.services;

import fr.owle.hometracker.HTAPI;
import fr.owle.hometracker.modules.ModuleLoader;
import fr.owle.hometracker.modules.ModuleManager;
import fr.owle.hometracker.utils.Log;
import fr.owle.hometracker.utils.exception.FileIsNotAModuleDirectoryException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.loader.LaunchedURLClassLoader;
import org.springframework.stereotype.Component;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.Objects;

@Component
public class ModuleService {

    public static final String MODULE_FILE = "./modules";

    @Autowired
    private HTAPI htapi;

    @Autowired
    private ModuleLoader loader;

    @Autowired
    private Log logger;

    @Autowired
    private ModuleManager moduleManager;

    public void loadModules() {
        final File modules = new File(MODULE_FILE);
        assert modules.exists() || modules.mkdirs();
        loader.loadModules(htapi);
        Arrays.stream(Objects.requireNonNull(modules.listFiles())).forEach(file -> {
            try {
                loader.loadModule(file, new LaunchedURLClassLoader(new URL[]{file.toURI().toURL()}, getClass().getClassLoader()));
            } catch (IOException e) {
                final String message = e.getMessage();
                logger.error(HTAPI.getHTAPI(), message);
            }
        });
        logger.info(HTAPI.getHTAPI(), "-- Prepare to start modules. --");
        loader.startModules();
    }

    public void stopModules() {
        moduleManager.stopAllModules();
    }

}
