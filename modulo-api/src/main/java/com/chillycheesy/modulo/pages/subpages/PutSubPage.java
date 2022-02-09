package com.chillycheesy.modulo.pages.subpages;

import com.chillycheesy.modulo.pages.HttpRequest;
import com.chillycheesy.modulo.pages.Page;

public abstract class PutSubPage extends Page {
    public PutSubPage(String subpath) {
        super(HttpRequest.PUT, subpath);
    }
}
