package com.chillycheesy.modulo.pages.subpages;

import com.chillycheesy.modulo.pages.HttpRequest;
import com.chillycheesy.modulo.pages.Page;

public abstract class DeleteSubPage extends Page {
    public DeleteSubPage(String subpath) {
        super(HttpRequest.DELETE, subpath);
    }
}
