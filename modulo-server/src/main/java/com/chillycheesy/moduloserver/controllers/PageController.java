package com.chillycheesy.moduloserver.controllers;

import com.chillycheesy.modulo.ModuloAPI;
import com.chillycheesy.moduloserver.ServerModule;
import com.chillycheesy.modulo.pages.HttpRequest;
import com.chillycheesy.modulo.pages.PageContainer;
import com.chillycheesy.modulo.utils.exception.No404SubPageException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
public class PageController {

    @Autowired private PageContainer page;
    @Autowired private ServerModule serverModule;

    @GetMapping("/**")
    public String getRedirect(HttpServletRequest request, HttpServletResponse response) {
        try {
            return page.getPageManager().redirect(HttpRequest.GET, request.getRequestURI()).getContent(request, response);
        } catch (No404SubPageException e) {
            ModuloAPI.getLogger().error(serverModule, e.getMessage());
        }
        return null;
    }
}
