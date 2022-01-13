package com.owle.hometracker;

import com.owle.hometracker.modules.Module;
import com.owle.hometracker.modules.ModuleLoader;
import com.owle.hometracker.services.ListenerService;
import com.owle.hometracker.services.ModuleService;

import com.owle.hometracker.modules.ModuleManager;
import com.owle.hometracker.utils.Log;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;

public class ServerModule extends Module {

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
        ModuloAPI.init(this);
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
