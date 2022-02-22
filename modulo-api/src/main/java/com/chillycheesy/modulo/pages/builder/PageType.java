package com.chillycheesy.modulo.pages.builder;

import com.chillycheesy.modulo.pages.Page;

public @interface PageType {
    Class<? extends Page> value();
}
