package com.chillycheesy.modulo.pages;

import com.chillycheesy.modulo.utils.exception.No404SubPageException;

public interface RoutingRedirection {
    public Page redirect(HttpRequest httpRequest, String path) throws No404SubPageException;
}