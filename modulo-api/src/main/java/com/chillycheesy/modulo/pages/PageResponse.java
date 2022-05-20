package com.chillycheesy.modulo.pages;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * You can use this class to create a response to a request on your {@link Page}.
 *
 * You get a reference to the request and response objects.
 * You can modify the response object to set the response code and headers.
 * You can also set the response body.
 */
public interface PageResponse {

    /**
     * Get the HTTP response body.
     * @param request The HTTP request.
     * @param response The HTTP response.
     * @return The response body.
     */
    String buildBody(HttpServletRequest request, HttpServletResponse response);

}
