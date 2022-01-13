package com.owle.hometracker.services;

import com.owle.hometracker.event.RequestEvent;
import com.owle.hometracker.utils.exception.HTModuleNotFoundException;
import com.owle.hometracker.utils.exception.PageMissingIndexAnnotationException;
import com.owle.hometracker.utils.exception.PageNotFoundException;
import com.owle.hometracker.events.EventManager;
import com.owle.hometracker.modules.Module;
import com.owle.hometracker.modules.ModuleManager;
import com.owle.hometracker.pages.Page;
import com.owle.hometracker.pages.PageManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PagesService {

    @Autowired
    private PageManager pageManager;

    @Autowired
    private EventManager eventManager;

    @Autowired
    private ModuleManager moduleManager;

    public String readContent(RequestEvent requestEvent) throws HTModuleNotFoundException, PageMissingIndexAnnotationException, PageNotFoundException {
       final Module module = moduleManager.getModule(requestEvent.getModuleName());
       final Page page = pageManager.getPage(module, requestEvent.getPageName());
       requestEvent.setModule(module);
       requestEvent.setPage(page);
       eventManager.emitEvent(module, requestEvent);
       return requestEvent.getContent();
    }

}
