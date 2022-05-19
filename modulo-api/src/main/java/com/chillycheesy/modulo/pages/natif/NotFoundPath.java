package com.chillycheesy.modulo.pages.natif;

import com.chillycheesy.modulo.pages.HttpRequestType;
import com.chillycheesy.modulo.pages.Page;

public abstract class NotFoundPath extends Page {
    public NotFoundPath() {
        super(HttpRequestType.ANY, "*");
    }
}
