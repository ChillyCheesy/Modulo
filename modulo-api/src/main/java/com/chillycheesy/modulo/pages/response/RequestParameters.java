package com.chillycheesy.modulo.pages.response;

import javax.servlet.http.HttpServletRequest;

public class RequestParameters {

    private final HttpServletRequest request;
    private final String body;

    public RequestParameters(HttpServletRequest request, String body) {
        this.request = request;
        this.body = body;
    }

    public String getParameter(String parameter) {
        return request.getParameter(parameter);
    }

    public String getBody() {
        return body;
    }

    public HttpServletRequest getRequest() {
        return request;
    }



}
