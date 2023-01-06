package com.chillycheesy.moduloserver.controllers;

import com.chillycheesy.modulo.ModuloAPI;
import com.chillycheesy.modulo.event.*;
import com.chillycheesy.modulo.events.EventContainer;
import com.chillycheesy.modulo.events.EventManager;
import com.chillycheesy.modulo.pages.PageManager;
import com.chillycheesy.modulo.utils.exception.No404SubPageException;
import com.chillycheesy.moduloserver.ServerModule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;

@RestController
public class PageController {

    @Autowired private ServerModule serverModule;
    @Autowired private EventContainer eventContainer;

    @GetMapping("/**")
    public void getRedirect(HttpServletRequest request, HttpServletResponse response) throws IOException {
        final GetRequestEvent event = new GetRequestEvent(request, response);
        redirect(request, response, event);
    }

    @PostMapping("/**")
    public void postRedirect(HttpServletRequest request, HttpServletResponse response) throws IOException {
        final PostRequestEvent event = new PostRequestEvent(request, response);
        redirect(request, response, event);
    }

    @PutMapping("/**")
    public void putRedirect(HttpServletRequest request, HttpServletResponse response) throws IOException {
        final PutRequestEvent event = new PutRequestEvent(request, response);
        redirect(request, response, event);
    }

    @DeleteMapping("/**")
    public void deleteRedirect(HttpServletRequest request, HttpServletResponse response) throws IOException {
        final DeleteRequestEvent event = new DeleteRequestEvent(request, response);
        redirect(request, response, event);
    }

    private void redirect(HttpServletRequest request, HttpServletResponse response, RequestEvent event) throws IOException {
        final EventManager eventManager = eventContainer.getEventManager();
        final PageManager pageManager = ModuloAPI.getPage().getPageManager();
        event.setCancelableAction(() -> {
            try {
                if(!pageManager.response(request, response))
                    throw new No404SubPageException();
            } catch (No404SubPageException e) {
                ModuloAPI.getLogger().error(serverModule, "Http 404 error: " + request.getRequestURI());
                response.sendError(404);
            }
        });
        eventManager.emitEvent(serverModule, event);
    }

}
