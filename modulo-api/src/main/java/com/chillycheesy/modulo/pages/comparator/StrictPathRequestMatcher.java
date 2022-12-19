package com.chillycheesy.modulo.pages.comparator;

import com.chillycheesy.modulo.pages.Page;

import javax.servlet.http.HttpServletRequest;

public class StrictPathRequestMatcher implements RequestMatcher<Page> {

    @Override
    public boolean compare(HttpServletRequest request, Page page) {
        final String requestPath = request.getRequestURI();
        final String pagePath = page.getPath();
        return requestPath.equals(pagePath);
    }

}
