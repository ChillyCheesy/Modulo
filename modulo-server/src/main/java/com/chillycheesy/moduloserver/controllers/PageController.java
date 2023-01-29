package com.chillycheesy.moduloserver.controllers;

import com.chillycheesy.modulo.controllers.ControllerContainer;
import com.chillycheesy.modulo.controllers.ModuloControllerManager;
import com.chillycheesy.modulo.event.*;
import com.chillycheesy.modulo.events.EventContainer;
import com.chillycheesy.modulo.events.EventManager;
import com.chillycheesy.moduloserver.ServerModule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
public class PageController {

    @Autowired private ServerModule serverModule;
    @Autowired private EventContainer eventContainer;
    @Autowired private ControllerContainer controllerContainer;

    @GetMapping("/**")
    public void getRedirect(HttpServletRequest request, HttpServletResponse response) throws Exception {
        final GetRequestEvent event = new GetRequestEvent(request, response);
        redirect(request, response, event);
    }

    @PostMapping("/**")
    public void postRedirect(HttpServletRequest request, HttpServletResponse response) throws Exception {
        final PostRequestEvent event = new PostRequestEvent(request, response);
        redirect(request, response, event);
    }

    @PutMapping("/**")
    public void putRedirect(HttpServletRequest request, HttpServletResponse response) throws Exception {
        final PutRequestEvent event = new PutRequestEvent(request, response);
        redirect(request, response, event);
    }

    @DeleteMapping("/**")
    public void deleteRedirect(HttpServletRequest request, HttpServletResponse response) throws Exception {
        final DeleteRequestEvent event = new DeleteRequestEvent(request, response);
        redirect(request, response, event);
    }

    private void redirect(HttpServletRequest request, HttpServletResponse response, RequestEvent event) throws Exception {
        final ModuloControllerManager manager = controllerContainer.getManager();
        final EventManager eventManager = eventContainer.getEventManager();
        eventManager.emitEvent(serverModule, event);
        if (!event.isCanceled())
            manager.apply(request, response);
    }

}
