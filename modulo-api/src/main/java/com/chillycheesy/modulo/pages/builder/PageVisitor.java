package com.chillycheesy.modulo.pages.builder;

import com.chillycheesy.modulo.pages.Page;

public interface PageVisitor {
    Page create(PageBuilderMetaInfo info, PageType annotation);
    Page create(PageBuilderMetaInfo info, HttpRequest annotation);
    Object createArgument(PageBuilderMetaInfo info, Path path);
}
