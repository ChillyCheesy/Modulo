package com.chillycheesy.modulo.event;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * This event was wen a http DELETE request was emit.
 * @see RequestEvent
 * @author henouille
 */
public class DeleteRequestEvent extends RequestEvent {

    /**
     * Creates a new DeleteRequestEvent.
     *
     * @param request  The request that was made.
     * @param response The response that was made.
     */
    public DeleteRequestEvent(HttpServletRequest request, HttpServletResponse response) {
        super(request, response);
    }
}
