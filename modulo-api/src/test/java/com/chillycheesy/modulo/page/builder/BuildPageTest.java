package com.chillycheesy.modulo.page.builder;

import com.chillycheesy.modulo.pages.HttpRequestType;
import com.chillycheesy.modulo.pages.Page;
import com.chillycheesy.modulo.pages.builder.HttpRequest;
import com.chillycheesy.modulo.pages.builder.PageType;

public class BuildPageTest {

    class MyPageToBuild {

        @PageType(Page.class)
        @HttpRequest(type = HttpRequestType.GET, path = "")
        public String myMethod() {
            return "";
        }

    }

}
