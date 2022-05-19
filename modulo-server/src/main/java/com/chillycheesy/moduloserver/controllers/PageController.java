package com.chillycheesy.moduloserver.controllers;

import com.chillycheesy.modulo.ModuloAPI;
import com.chillycheesy.modulo.event.*;
import com.chillycheesy.modulo.events.EventContainer;
import com.chillycheesy.modulo.events.EventManager;
import com.chillycheesy.modulo.pages.*;
import com.chillycheesy.moduloserver.ServerModule;
import com.chillycheesy.modulo.utils.exception.No404SubPageException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
public class PageController {

    @Autowired private ServerModule serverModule;
    @Autowired private EventContainer eventContainer;

    @GetMapping("/**")
    public void getRedirect(HttpServletRequest request, HttpServletResponse response) {
        final GetRequestEvent event = new GetRequestEvent(request, response);
        redirect(request, response, event, HttpRequestType.GET);
    }

    @PostMapping("/**")
    public void postRedirect(HttpServletRequest request, HttpServletResponse response) {
        final PostRequestEvent event = new PostRequestEvent(request, response);
        redirect(request, response, event, HttpRequestType.POST);
    }

    @PutMapping("/**")
    public void putRedirect(HttpServletRequest request, HttpServletResponse response) {
        final PutRequestEvent event = new PutRequestEvent(request, response);
        redirect(request, response, event, HttpRequestType.PUT);
    }

    @DeleteMapping("/**")
    public void deleteRedirect(HttpServletRequest request, HttpServletResponse response) {
        final DeleteRequestEvent event = new DeleteRequestEvent(request, response);
        redirect(request, response, event, HttpRequestType.DELETE);
    }

    private void redirect(HttpServletRequest request, HttpServletResponse response, RequestEvent event, HttpRequestType httpRequest) {
        final EventManager eventManager = eventContainer.getEventManager();
        event.setCancelableAction(() -> {
            try {
                final Page page = ModuloAPI.getPage().getPageManager().redirect(httpRequest, request.getRequestURI());
                final String content = page.applyRequest(request, response);
                final SendPageEvent sendPageEvent = new SendPageEvent(page, request, response, content);
                eventManager.emitEvent(serverModule, sendPageEvent);
            } catch (IOException | No404SubPageException e) {
                ModuloAPI.getLogger().error(serverModule, e.getMessage());
            }
        });
        eventManager.emitEvent(serverModule, event);
    }
}
