package com.chillycheesy.modulo.pages.factory;

public interface MethodAnnotationApplier {

    PageBuilder applyPageTypeAnnotation(PageType pageType, PageBuilder builder);

    PageBuilder applyResponseResourceAnnotation(ResponseResource responseResource, PageBuilder builder);

    PageBuilder applyRequestAnnotation(Request request, PageBuilder builder);

    PageBuilder applyGetRequestAnnotation(GetRequest request, PageBuilder builder);

    PageBuilder applyPostRequestAnnotation(PostRequest request, PageBuilder builder);

    PageBuilder applyPutRequestAnnotation(PutRequest request, PageBuilder builder);

    PageBuilder applyDeleteRequestAnnotation(DeleteRequest request, PageBuilder builder);


}
