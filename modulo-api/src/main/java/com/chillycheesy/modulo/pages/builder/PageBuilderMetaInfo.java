package com.chillycheesy.modulo.pages.builder;

import com.chillycheesy.modulo.pages.Page;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.TypeVariable;

public class PageBuilderMetaInfo {

    private PageVisitor visitor;
    private Page page;
    private HttpServletRequest request;
    private HttpServletResponse response;
    private TypeVariable<?> typeVariable;
    private Object object;

    public PageBuilderMetaInfo(PageVisitor visitor, Page page, HttpServletRequest request, HttpServletResponse response, TypeVariable<?> typeVariable, Object object) {
        this.visitor = visitor;
        this.page = page;
        this.response = response;
        this.request = request;
        this.typeVariable = typeVariable;
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

    public TypeVariable<?> getTypeVariable() {
        return typeVariable;
    }

    public Object getObject() {
        return object;
    }
}
