package com.chillycheesy.modulo.pages.subpages;

import com.chillycheesy.modulo.pages.HttpRequest;
import com.chillycheesy.modulo.pages.Page;

public abstract class PostSubPage extends Page {
    public PostSubPage(String subpath) {
        super(HttpRequest.POST, subpath);
    }
}
