package com.chillycheesy.modulo.event;

import com.chillycheesy.modulo.events.Cancelable;
import com.chillycheesy.modulo.events.CancelableAction;
import com.chillycheesy.modulo.events.Event;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * This event was wen a http request was emit.
 */
public class RequestEvent extends Event implements Cancelable {

    /**
     * The request that was made.
     */
    protected HttpServletRequest request;
    /**
     * The response that was made.
     */
    protected HttpServletResponse response;

    protected boolean isCanceled;
    protected CancelableAction cancelableAction;

    /**
     * Creates a new RequestEvent.
     * @param request The request that was made.
     * @param response The response that was made.
     */
    public RequestEvent(HttpServletRequest request, HttpServletResponse response) {
        this.request = request;
        this.response = response;
    }

    /**
     * Gets the request that was made.
     * @return The request that was made.
     */
    public HttpServletRequest getRequest() {
        return request;
    }

    /**
     * Gets the response that was made.
     * @return The response that was made.
     */
    public HttpServletResponse getResponse() {
        return response;
    }

    /**
     * Sets the request that was made.
     * @param request The request that was made.
     */
    public void setRequest(HttpServletRequest request) {
        this.request = request;
    }

    /**
     * Sets the response that was made.
     * @param response The response that was made.
     */
    public void setResponse(HttpServletResponse response) {
        this.response = response;
    }

    @Override
    public boolean isCanceled() {
        return isCanceled;
    }

    @Override
    public void setCanceled(boolean cancel) {
        this.isCanceled = cancel;
    }

    @Override
    public Cancelable setCancelableAction(CancelableAction action) {
        this.cancelableAction = action;
        return this;
    }

    @Override
    public CancelableAction getCancelableAction() {
        return cancelableAction;
    }

}
