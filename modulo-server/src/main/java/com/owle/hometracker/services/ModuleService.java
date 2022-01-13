package com.owle.hometracker.services;

import com.owle.hometracker.ModuloAPI;
import com.owle.hometracker.modules.ModuleLoader;
import com.owle.hometracker.modules.ModuleManager;
import com.owle.hometracker.utils.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.loader.LaunchedURLClassLoader;
import org.springframework.stereotype.Service;

import java.io.*;
import java.net.URL;
import java.util.Arrays;
import java.util.Objects;

@Service
public class ModuleService {

    public static final String MODULES_FILE = "./modules";

    @Autowired
    private ModuloAPI moduloAPI;

    @Autowired
    private ModuleLoader loader;

    @Autowired
    private Log logger;

    @Autowired
    private ModuleManager moduleManager;

    public void loadModules() {
        final File modules = new File(MODULES_FILE);
        assert modules.mkdirs();
        loader.loadModules(moduloAPI);
        Arrays.stream(Objects.requireNonNull(modules.listFiles())).forEach(file -> {
            try {
                loader.loadModule(file, new LaunchedURLClassLoader(new URL[]{file.toURI().toURL()}, getClass().getClassLoader()));
            } catch (IOException e) {
                final String message = e.getMessage();
                logger.error(null, message);
                e.printStackTrace();
            }
        });
        logger.info(null, "-- Prepare to start modules. --");
        loader.startModules();
    }

    public void stopModules() {
        moduleManager.stopAllModules();
    }

}
