package com.chillycheesy.modulo.pages.subpages;

/**
 * This page display an existing website into an HTML iframe
 * @author Aymeric Defossez
 */
public class IFramePage /*extends Page*/ {
/*
    private int width;
    private int height;

    public IFramePage(HttpRequest requestType, String path, PageResponse content, int width, int height) {
        super(requestType, path, content);
        this.width = width;
        this.height = height;
    }

    public IFramePage(HttpRequest requestType, String path, String content, int width, int height) {
        super(requestType, path, content);
        this.width = width;
        this.height = height;
    }

    public IFramePage(String path, String content, int width, int height) {
        super(path, content);
        this.width = width;
        this.height = height;
    }

    public IFramePage(String path, PageResponse content, int width, int height) {
        super(path, content);
        this.width = width;
        this.height = height;
    }

    public IFramePage(HttpRequest requestType, String path, int width, int height) {
        super(requestType, path);
        this.width = width;
        this.height = height;
    }

    public IFramePage(String path, int width, int height) {
        super(path);
        this.width = width;
        this.height = height;
    }

    @Override
    public String getContent(HttpServletRequest request, HttpServletResponse response, boolean pushInResponse) throws IOException {
        //final PageManager pageManager = ModuloAPI.getPage().getPageManager();
        final String builtContent = "<iframe width=\"" + this.width + "\" height=\"" + this.height + "\" src=\"" + content.buildBody(request, response) + "\"></iframe>";
        final ByteArrayInputStream inputStream = new ByteArrayInputStream(builtContent.getBytes());
        if (pushInResponse) IOUtils.copy(inputStream, response.getOutputStream());
        return builtContent;
    }*/
}
