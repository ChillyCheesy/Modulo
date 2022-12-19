package com.chillycheesy.modulo.pages.comparator;

import com.chillycheesy.modulo.pages.Page;
import com.chillycheesy.modulo.pages.TemplatePage;

import javax.servlet.http.HttpServletRequest;

public class TemplatePathRequestMatcher implements RequestMatcher<TemplatePage> {

    @Override
    public boolean compare(HttpServletRequest request, TemplatePage page) {
        final String requestPath = request.getRequestURI();
        final String pagePath = page.getPath();
        final String[] requestPathArgs = requestPath.split("/");
        final String[] pagePathArgs = pagePath.split("/");


        return false;
    }

}
