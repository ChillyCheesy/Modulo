package com.chillycheesy.modulo.event;

import com.chillycheesy.modulo.events.Event;
import com.chillycheesy.modulo.pages.Page;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SendPageEvent extends Event {

    protected Page page;
    protected HttpServletRequest request;
    protected HttpServletResponse response;
    protected String body;

    public SendPageEvent(Page page, HttpServletRequest request, HttpServletResponse response, String body) {
        this.page = page;
        this.request = request;
        this.response = response;
        this.body = body;
    }

    public void setPage(Page page) {
        this.page = page;
    }

    public void setRequest(HttpServletRequest request) {
        this.request = request;
    }

    public void setResponse(HttpServletResponse response) {
        this.response = response;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public Page getPage() {
        return page;
    }

    public HttpServletRequest getRequest() {
        return request;
    }

    public HttpServletResponse getResponse() {
        return response;
    }

    public String getBody() {
        return body;
    }
}
