package com.owle.hometracker.configurations;

import com.owle.hometracker.ModuloAPI;
import com.owle.hometracker.events.EventContainer;
import com.owle.hometracker.events.EventManager;
import com.owle.hometracker.modules.ModuleContainer;
import com.owle.hometracker.modules.ModuleLoader;
import com.owle.hometracker.modules.ModuleManager;
import com.owle.hometracker.pages.PageContainer;
import com.owle.hometracker.pages.PageManager;
import com.owle.hometracker.utils.Log;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class HTAPIManagerConfiguration {

    @Bean
    public ModuleManager moduleManager() {
        final ModuleContainer moduleContainer = ModuloAPI.getModule();
        return moduleContainer.getModuleManager();
    }

    @Bean
    public PageManager pageManager() {
        final PageContainer pageContainer = ModuloAPI.getPage();
        return pageContainer.getPageManager();
    }

    @Bean
    public EventManager eventManager() {
        final EventContainer eventContainer = ModuloAPI.getEvent();
        return eventContainer.getEventManager();
    }

    @Bean
    public Log logger() {
        return ModuloAPI.getLogger();
    }

    @Bean
    public ModuleLoader moduleLoader() {
        final ModuleContainer moduleContainer = ModuloAPI.getModule();
        return moduleContainer.getModuleLoader();
    }

}
