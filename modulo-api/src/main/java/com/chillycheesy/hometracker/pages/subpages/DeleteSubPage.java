package com.chillycheesy.hometracker.pages.subpages;

import com.chillycheesy.hometracker.pages.HttpRequest;
import com.chillycheesy.hometracker.pages.Page;

public abstract class DeleteSubPage extends Page {
    public DeleteSubPage(String subpath) {
        super(HttpRequest.DELETE, subpath);
    }
}
