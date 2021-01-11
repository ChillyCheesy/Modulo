package fr.owle.hometracker.configurations;

import fr.owle.hometracker.HTAPI;
import fr.owle.hometracker.events.EventContainer;
import fr.owle.hometracker.events.EventManager;
import fr.owle.hometracker.modules.ModuleContainer;
import fr.owle.hometracker.modules.ModuleLoader;
import fr.owle.hometracker.modules.ModuleManager;
import fr.owle.hometracker.pages.PageContainer;
import fr.owle.hometracker.pages.PageManager;
import fr.owle.hometracker.utils.Log;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class HTAPIManagerConfiguration {

    @Bean
    public ModuleManager moduleManager() {
        final ModuleContainer moduleContainer = HTAPI.getModule();
        return moduleContainer.getModuleManager();
    }

    @Bean
    public PageManager pageManager() {
        final PageContainer pageContainer = HTAPI.getPage();
        return pageContainer.getPageManager();
    }

    @Bean
    public EventManager eventManager() {
        final EventContainer eventContainer = HTAPI.getEvent();
        return eventContainer.getEventManager();
    }

    @Bean
    public Log logger() {
        return HTAPI.getLogger();
    }

    @Bean
    public ModuleLoader moduleLoader() {
        final ModuleContainer moduleContainer = HTAPI.getModule();
        return moduleContainer.getModuleLoader();
    }

}
