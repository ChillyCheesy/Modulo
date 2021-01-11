package fr.owle.hometracker;

import fr.owle.hometracker.modules.HTModule;
import fr.owle.hometracker.modules.ModuleLoader;
import fr.owle.hometracker.services.ListenerService;
import fr.owle.hometracker.services.ModuleService;

import fr.owle.hometracker.modules.ModuleManager;
import fr.owle.hometracker.utils.Log;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;

public class ServerModule extends HTModule {

    @Autowired
    private ModuleLoader moduleLoader;

    @Autowired
    private ModuleManager moduleManager;

    @Autowired
    private ModuleService moduleService;

    @Autowired
    private ListenerService listenerService;

    @Autowired
    private Log logger;

    @PostConstruct
    public void init() {
        HTAPI.init(this);
        moduleLoader.loadModules(this);
        moduleLoader.startModules();
    }

    @Override
    protected void onLoad() {
        listenerService.registerListener(this);
    }

    @Override
    protected void onStart() {
        logger.info(this, "-- Prepare to load modules. --");
        moduleService.loadModules();
        final int size = moduleManager.getModuleCount();
        logger.info(this, size + " modules loaded and started.");
    }

    @Override
    protected void onStop() {
        logger.info(this, "Prepare to stop modules.");
        moduleService.stopModules();
        logger.info(this, "Stop servers.");
    }

}
