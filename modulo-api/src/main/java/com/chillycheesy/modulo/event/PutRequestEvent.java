package com.chillycheesy.modulo.event;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * This event was wen a http PUT request was emit.
 * @see RequestEvent
 * @author henouille
 */
public class PutRequestEvent extends RequestEvent {


    /**
     * Creates a new PutRequestEvent.
     *
     * @param request  The request that was made.
     * @param response The response that was made.
     */
    public PutRequestEvent(HttpServletRequest request, HttpServletResponse response) {
        super(request, response);
    }
}
