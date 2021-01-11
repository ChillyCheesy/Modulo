package fr.owle.hometracker.services;

import fr.owle.hometracker.event.*;
import fr.owle.hometracker.events.EventManager;
import fr.owle.hometracker.modules.HTModule;
import fr.owle.hometracker.modules.ModuleManager;
import fr.owle.hometracker.pages.Page;
import fr.owle.hometracker.pages.PageManager;
import fr.owle.hometracker.utils.exception.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PagesService {

    @Autowired
    private PageManager pageManager;

    @Autowired
    private EventManager eventManager;

    @Autowired
    private ModuleManager moduleManager;

    public String readContent(RequestEvent requestEvent) throws HTModuleNotFoundException, PageMissingIndexAnnotationException, PageNotFoundException {
       final HTModule module = moduleManager.getModule(requestEvent.getModuleName());
       final Page page = pageManager.getPage(module, requestEvent.getPageName());
       requestEvent.setModule(module);
       requestEvent.setPage(page);
       eventManager.emitEvent(module, requestEvent);
       return requestEvent.getContent();
    }

}
