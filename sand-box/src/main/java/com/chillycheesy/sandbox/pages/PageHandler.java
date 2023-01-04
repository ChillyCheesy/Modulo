package com.chillycheesy.sandbox.pages;

import com.chillycheesy.modulo.pages.Page;
import com.chillycheesy.modulo.pages.response.ResponseHandler;
import com.chillycheesy.sandbox.SandBoxModule;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class PageHandler implements ResponseHandler {

    @Override
    public boolean response(Page page, HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.getOutputStream().println("Hello World!");
        SandBoxModule.instance.info("PageHandler.response() called " + request.getMethod());
        return true;
    }

}
