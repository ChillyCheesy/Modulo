package com.chillycheesy.hometracker;

import com.chillycheesy.hometracker.modules.ModuleConfig;
import com.chillycheesy.hometracker.modules.Module;
import com.chillycheesy.hometracker.services.ModuleService;
import com.chillycheesy.hometracker.services.ServerListenerService;
import com.chillycheesy.hometracker.utils.Log;
import com.chillycheesy.hometracker.utils.exception.FileIsNotAModuleDirectoryException;
import com.chillycheesy.hometracker.utils.exception.MissingDependenciesModuleException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.List;

@Component
public class ServerModule extends Module {

    @Autowired private Log logger;
    @Autowired private ModuleService moduleService;
    @Autowired private ServerListenerService serverListenerService;
    @Autowired private ModuleConfig serverConfig;

    private List<Module> modules;

    @PostConstruct
    private void init() throws MissingDependenciesModuleException {
        this.setConfig(serverConfig);
        serverListenerService.registerListener(this);
        this.moduleService.loadAndStartModule(this);
    }

    @Override
    protected void onLoad() throws IOException, FileIsNotAModuleDirectoryException {
        logger.info(this, "-- Prepare to load modules. --");
        modules = moduleService.loadModules();
        logger.info(this, modules.size() + " modules loaded.");
    }

    @Override
    protected void onStart() throws MissingDependenciesModuleException {
        logger.info(this, "-- Prepare to start modules. --");
        modules = moduleService.startModules(modules);
        logger.info(this, modules.size() + " modules started.");
    }

    @Override
    protected void onStop() {
        logger.info(this, "-- Prepare to stop modules. --");
        moduleService.stopModules();
        logger.info(this, "Servers stopped.");
    }

}
