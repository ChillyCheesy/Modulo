package com.chillycheesy.modulo.pages.subpages;

import com.chillycheesy.modulo.pages.HttpRequest;
import com.chillycheesy.modulo.pages.Page;
import com.chillycheesy.modulo.pages.PageResponse;
import org.apache.commons.io.IOUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.IOException;

public class RedirectPage extends Page {

    public RedirectPage(HttpRequest requestType, String path, PageResponse content) {
        super(requestType, path, content);
    }

    public RedirectPage(HttpRequest requestType, String path, String content) {
        super(requestType, path, content);
    }

    public RedirectPage(String path, String content) {
        super(path, content);
    }

    public RedirectPage(String path, PageResponse content) {
        super(path, content);
    }

    public RedirectPage(HttpRequest requestType, String path) {
        super(requestType, path);
    }

    public RedirectPage(String path) {
        super(path);
    }

    @Override
    public String getContent(HttpServletRequest request, HttpServletResponse response, boolean pushInResponse) throws IOException {
        final String builtContent = "<meta http-equiv=\"refresh\" content=\"0; URL=" + content.buildBody(request, response) + "\" />";
        if (pushInResponse) response.sendRedirect(content.buildBody(request, response));;
        return builtContent;
    }
}
