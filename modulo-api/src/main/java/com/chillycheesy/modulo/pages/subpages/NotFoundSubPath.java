package com.chillycheesy.modulo.pages.subpages;

import com.chillycheesy.modulo.pages.HttpRequest;
import com.chillycheesy.modulo.pages.Page;

public abstract class NotFoundSubPath extends Page {
    public NotFoundSubPath() {
        super(HttpRequest.ANY, "*");
    }
}
