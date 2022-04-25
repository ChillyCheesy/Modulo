package com.chillycheesy.modulo.pages.builder;

import com.chillycheesy.modulo.pages.Page;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class PageBuilderMetaInfo {

    private PageVisitor visitor;
    private Page page;
    private HttpServletRequest request;
    private HttpServletResponse response;
    private Class<?> type;
    private Object object;

    public PageBuilderMetaInfo(PageVisitor visitor, Page page, HttpServletRequest request, HttpServletResponse response, Class<?> type, Object object) {
        this.visitor = visitor;
        this.page = page;
        this.response = response;
        this.request = request;
        this.type = type;
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

    public Class<?> getTypeVariable() {
        return type;
    }

    public Object getObject() {
        return object;
    }

    public void setVisitor(PageVisitor visitor) {
        this.visitor = visitor;
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

    public void setType(Class<?> type) {
        this.type = type;
    }

    public void setObject(Object object) {
        this.object = object;
    }
}
