package com.chillycheesy.modulo.pages.subpages;

import com.chillycheesy.modulo.pages.HttpRequest;
import com.chillycheesy.modulo.pages.Page;

public abstract class GetSubPage extends Page {

    public GetSubPage(String subpath) {
        super(HttpRequest.GET, subpath);
    }
}
