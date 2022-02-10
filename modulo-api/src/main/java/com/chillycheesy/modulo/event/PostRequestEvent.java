package com.chillycheesy.modulo.event;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * This event was wen a http POST request was emit.
 * @see RequestEvent
 * @author henouille
 */
public class PostRequestEvent extends RequestEvent {


    /**
     * Creates a new PostRequestEvent.
     *
     * @param request  The request that was made.
     * @param response The response that was made.
     */
    public PostRequestEvent(HttpServletRequest request, HttpServletResponse response) {
        super(request, response);
    }
}
