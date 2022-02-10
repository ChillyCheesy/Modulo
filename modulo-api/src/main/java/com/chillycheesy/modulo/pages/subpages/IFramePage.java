package com.chillycheesy.modulo.pages.subpages;

import com.chillycheesy.modulo.pages.HttpRequest;
import com.chillycheesy.modulo.pages.Page;
import com.chillycheesy.modulo.pages.PageResponse;
import org.apache.commons.io.IOUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.IOException;

public class IFramePage extends Page {

    public IFramePage(HttpRequest requestType, String path, PageResponse content) {
        super(requestType, path, content);
    }

    public IFramePage(HttpRequest requestType, String path, String content) {
        super(requestType, path, content);
    }

    public IFramePage(String path, String content) {
        super(path, content);
    }

    public IFramePage(String path, PageResponse content) {
        super(path, content);
    }

    public IFramePage(HttpRequest requestType, String path) {
        super(requestType, path);
    }

    public IFramePage(String path) {
        super(path);
    }

    @Override
    public String getContent(HttpServletRequest request, HttpServletResponse response, boolean pushInResponse) throws IOException {
        //final PageManager pageManager = ModuloAPI.getPage().getPageManager();
        final String builtContent = "<iframe src=\"" + content.buildBody(request, response) + "\"></iframe>";
        final ByteArrayInputStream inputStream = new ByteArrayInputStream(builtContent.getBytes());
        if (pushInResponse) IOUtils.copy(inputStream, response.getOutputStream());
        return builtContent;
    }
}
