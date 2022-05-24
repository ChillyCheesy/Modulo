package com.chillycheesy.moduloserver.services;

import com.chillycheesy.modulo.modules.ModuleContainer;
import com.chillycheesy.modulo.modules.Module;
import com.chillycheesy.modulo.modules.ModuleLoader;
import com.chillycheesy.modulo.modules.ModuleManager;
import com.chillycheesy.modulo.utils.Log;
import com.chillycheesy.modulo.utils.exception.FileIsNotAModuleDirectoryException;
import com.chillycheesy.modulo.utils.exception.MissingDependenciesModuleException;
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
        loader.startModule(module);
    }

    public List<Module> loadModules() throws IOException, FileIsNotAModuleDirectoryException {
        final File modules = new File(MODULES_FILE);
        if (modules.mkdirs() || modules.exists()) {
            final ModuleLoader loader = module.getModuleLoader();
            return loader.loadModules(modules);
        }
        return new ArrayList<>();
    }

    public List<Module> startModules(List<Module> modules) throws MissingDependenciesModuleException {
        final ModuleLoader loader = module.getModuleLoader();
        return loader.startModules(modules);
    }

    public void stopModules(List<Module> modules) {
        final ModuleManager manager = module.getModuleManager();
        manager.stopModules(modules);
    }

}
