package com.chillycheesy.modulo.pages.response;

import com.chillycheesy.modulo.pages.Page;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public interface ResponseHandler {
    boolean response(Page page, HttpServletRequest request, HttpServletResponse response) throws IOException;
}


