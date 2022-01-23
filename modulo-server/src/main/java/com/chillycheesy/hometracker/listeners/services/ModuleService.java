package com.chillycheesy.hometracker.listeners.services;

import com.chillycheesy.hometracker.modules.ModuleContainer;
import com.chillycheesy.hometracker.modules.Module;
import com.chillycheesy.hometracker.modules.ModuleLoader;
import com.chillycheesy.hometracker.modules.ModuleManager;
import com.chillycheesy.hometracker.utils.Log;
import com.chillycheesy.hometracker.utils.exception.FileIsNotAModuleDirectoryException;
import com.chillycheesy.hometracker.utils.exception.MissingDependenciesModuleException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class ModuleService {

    public static final String MODULES_FILE = "./modules";

    @Autowired private Log logger;
    @Autowired private ModuleContainer module;

    public void loadAndStartModule(Module module) throws MissingDependenciesModuleException {
        final ModuleLoader loader = this.module.getModuleLoader();
        loader.loadModule(module);
        loader.startModules();
    }

    public List<Module> loadModules() throws IOException, FileIsNotAModuleDirectoryException {
        final File modules = new File(MODULES_FILE);
        if (modules.mkdirs()) {
            final ModuleLoader loader = module.getModuleLoader();
            return loader.loadModules(modules);
        }
        return new ArrayList<>();
    }

    public List<Module> startModules(List<Module> modules) throws MissingDependenciesModuleException {
        final ModuleLoader loader = module.getModuleLoader();
        return loader.startModules(modules);
    }

    public void stopModules() {
        final ModuleManager manager = module.getModuleManager();
        manager.stopAllModules();
    }

}
