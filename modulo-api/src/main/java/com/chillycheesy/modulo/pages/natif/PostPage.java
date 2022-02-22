package com.chillycheesy.modulo.pages.natif;

import com.chillycheesy.modulo.pages.HttpRequestType;
import com.chillycheesy.modulo.pages.Page;

public abstract class PostPage extends Page {
    public PostPage(String subpath) {
        super(HttpRequestType.POST, subpath);
    }
}
