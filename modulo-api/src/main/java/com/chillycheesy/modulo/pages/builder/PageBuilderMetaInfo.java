package com.chillycheesy.modulo.pages.builder;

import com.chillycheesy.modulo.pages.Page;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class PageBuilderMetaInfo {

    private PageVisitor visitor;
    private Page page;
    private HttpServletRequest request;
    private HttpServletResponse response;
    private Object object;

    public PageBuilderMetaInfo(PageVisitor visitor, Page page, HttpServletRequest request, HttpServletResponse response, Object object) {
        this.visitor = visitor;
        this.page = page;
        this.response = response;
        this.request = request;
        this.object = object;
    }

    public PageBuilderMetaInfo(PageVisitor visitor, Page page) {
        this.visitor = visitor;
        this.page = page;
    }

    public PageVisitor getVisitor() {
        return visitor;
    }

    public Page getPage() {
        return page;
    }

    public HttpServletResponse getResponse() {
        return response;
    }

    public HttpServletRequest getRequest() {
        return request;
    }

    public Object getObject() {
        return object;
    }
}
