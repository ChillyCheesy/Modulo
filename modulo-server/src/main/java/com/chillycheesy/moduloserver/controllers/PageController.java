package com.chillycheesy.moduloserver.controllers;

import com.chillycheesy.modulo.ModuloAPI;
import com.chillycheesy.moduloserver.ServerModule;
import com.chillycheesy.modulo.pages.HttpRequest;
import com.chillycheesy.modulo.pages.PageContainer;
import com.chillycheesy.modulo.utils.exception.No404SubPageException;
import org.springframework.beans.Mergeable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Base64;

@RestController
public class PageController {

    @Autowired private PageContainer page;
    @Autowired private ServerModule serverModule;

    @GetMapping("/**")
    public byte[] getRedirect(HttpServletRequest request, HttpServletResponse response) {
        try {
            final String encrypted = page.getPageManager().redirect(HttpRequest.GET, request.getRequestURI()).getContent(request, response);
            return encrypted.getBytes();
        } catch (No404SubPageException e) {
            ModuloAPI.getLogger().error(serverModule, e.getMessage());
        }
        return new byte[0];
    }
}
