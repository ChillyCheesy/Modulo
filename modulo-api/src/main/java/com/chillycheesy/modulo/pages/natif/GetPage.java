package com.chillycheesy.modulo.pages.natif;

import com.chillycheesy.modulo.pages.HttpRequestType;
import com.chillycheesy.modulo.pages.Page;

public abstract class GetPage extends Page {

    public GetPage(String subpath) {
        super(HttpRequestType.GET, subpath);
    }
}
