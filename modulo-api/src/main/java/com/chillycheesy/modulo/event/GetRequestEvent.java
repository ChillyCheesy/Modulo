package com.chillycheesy.modulo.event;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * This event was wen a http GET request was emit.
 * @see RequestEvent
 * @author henouille
 */
public class GetRequestEvent extends RequestEvent {


    /**
     * Creates a new GetRequestEvent.
     *
     * @param request  The request that was made.
     * @param response The response that was made.
     */
    public GetRequestEvent(HttpServletRequest request, HttpServletResponse response) {
        super(request, response);
    }
}
