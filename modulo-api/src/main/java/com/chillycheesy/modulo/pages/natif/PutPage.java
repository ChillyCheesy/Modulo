package com.chillycheesy.modulo.pages.natif;

import com.chillycheesy.modulo.pages.HttpRequestType;
import com.chillycheesy.modulo.pages.Page;

public abstract class PutPage extends Page {
    public PutPage(String subpath) {
        super(HttpRequestType.PUT, subpath);
    }
}
