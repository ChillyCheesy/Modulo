package com.chillycheesy.moduloserver.controllers;

import com.chillycheesy.modulo.ModuloAPI;
import com.chillycheesy.modulo.event.*;
import com.chillycheesy.modulo.events.EventContainer;
import com.chillycheesy.modulo.events.EventManager;
import com.chillycheesy.modulo.pages.*;
import com.chillycheesy.moduloserver.ServerModule;
import com.chillycheesy.modulo.utils.exception.No404SubPageException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;

@RestController
public class PageController {

    @Autowired private ServerModule serverModule;
    @Autowired private EventContainer eventContainer;

    @GetMapping("/**")
    public @ResponseBody void getRedirect(HttpServletRequest request, HttpServletResponse response) throws IOException {
        final GetRequestEvent event = new GetRequestEvent(request, response);
        redirect(request, response, event, HttpRequestType.GET);
    }

    @PostMapping("/**")
    public @ResponseBody void postRedirect(HttpServletRequest request, HttpServletResponse response) throws IOException {
        final PostRequestEvent event = new PostRequestEvent(request, response);
        redirect(request, response, event, HttpRequestType.POST);
    }

    @PutMapping("/**")
    public @ResponseBody void putRedirect(HttpServletRequest request, HttpServletResponse response) throws IOException {
        final PutRequestEvent event = new PutRequestEvent(request, response);
        redirect(request, response, event, HttpRequestType.PUT);
    }

    @DeleteMapping("/**")
    public @ResponseBody void deleteRedirect(HttpServletRequest request, HttpServletResponse response) throws IOException {
        final DeleteRequestEvent event = new DeleteRequestEvent(request, response);
        redirect(request, response, event, HttpRequestType.DELETE);
    }

    private void redirect(HttpServletRequest request, HttpServletResponse response, RequestEvent event, HttpRequestType httpRequest) throws IOException {
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
