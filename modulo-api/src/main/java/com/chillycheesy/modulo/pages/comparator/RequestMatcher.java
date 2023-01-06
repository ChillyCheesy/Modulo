package com.chillycheesy.modulo.pages.comparator;

import com.chillycheesy.modulo.pages.Page;

import javax.servlet.http.HttpServletRequest;

/**
 * Check if the path of the request is the same as the path of the page.
 *
 * @author Aymeric Hénouille
 */
public interface RequestMatcher {

    /**
     * Compare the path of the page with the path of the request.
     * @param request The request.
     * @param page The page.
     * @return True if the path of the page is the same as the path of the request.
     */
    boolean compare(HttpServletRequest request, Page page);
}

