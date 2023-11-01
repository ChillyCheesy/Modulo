package com.chillycheesy.moduloserver;

import com.chillycheesy.modulo.modules.Module;
import com.chillycheesy.moduloserver.services.ModuleService;
import com.chillycheesy.modulo.utils.exception.FileIsNotAModuleDirectoryException;
import com.chillycheesy.modulo.utils.exception.MissingDependenciesModuleException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

@Component
public class ServerModule extends Module {

    @Autowired private ModuleService moduleService;

    private List<Module> modules;

    @Override
    protected void onLoad() throws IOException, FileIsNotAModuleDirectoryException {
        final String prepareLog = defaultConfiguration.getString("loader.load.prepare");
        final String doneLog = defaultConfiguration.getString("loader.load.done");
        info(prepareLog);
        modules = moduleService.loadModules();
        info(String.format(doneLog, modules.size()));
    }

    @Override
    protected void onStart() throws MissingDependenciesModuleException {
        final String prepareLog = defaultConfiguration.getString("loader.start.prepare");
        final String doneLog = defaultConfiguration.getString("loader.start.done");
        info(prepareLog);
        moduleService.startModules(modules);
        info(String.format(doneLog, modules.size()));
    }

    @Override
    protected void onStop() {
        final String prepareLog = defaultConfiguration.getString("loader.stop.prepare");
        final String doneLog = defaultConfiguration.getString("loader.stop.done");
        info(prepareLog);
        moduleService.stopModules(modules);
        info(String.format(doneLog, modules.size()));
    }

}
