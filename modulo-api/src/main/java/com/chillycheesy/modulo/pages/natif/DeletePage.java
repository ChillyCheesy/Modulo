package com.chillycheesy.modulo.pages.natif;

import com.chillycheesy.modulo.pages.HttpRequestType;
import com.chillycheesy.modulo.pages.Page;

public abstract class DeletePage extends Page {
    public DeletePage(String subpath) {
        super(HttpRequestType.DELETE, subpath);
    }
}
