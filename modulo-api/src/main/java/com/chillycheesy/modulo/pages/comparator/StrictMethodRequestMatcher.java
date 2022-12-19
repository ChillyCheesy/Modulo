package com.chillycheesy.modulo.pages.comparator;

import com.chillycheesy.modulo.pages.Page;

import javax.servlet.http.HttpServletRequest;

public class StrictMethodRequestMatcher implements RequestMatcher<Page> {

    @Override
    public boolean compare(HttpServletRequest request, Page page) {
        final String requestMethod = request.getMethod();
        final String pageMethod = page.getMethod();
        return requestMethod.equals(pageMethod);
    }

}
