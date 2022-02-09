package com.chillycheesy.modulo.event;

import com.chillycheesy.modulo.events.Event;
import com.chillycheesy.modulo.modules.Module;
import com.chillycheesy.modulo.pages.Page;

/**
 * This event was wen a http request was emit.
 * @author henouille
 */
public class RequestEvent extends Event {

    private String moduleName;
    private String pageName;
    private String path;
    private String param;
    private String body;

    private Module module;
    private Page page;
    private String content;

    /**
     * Create a new request event.
     * @param moduleName The target module.
     * @param pageName The target page. (it should be a page of the target module)
     * @param path The target path of the page.
     * @param param The param of the page.
     * @param body The body request.
     */
    public RequestEvent(String moduleName, String pageName, String path, String param, String body) {
        this.moduleName = moduleName;
        this.pageName = pageName;
        this.path = path;
        this.body = body;
        this.param = param;

        content = null;
        module = null;
        page = null;
    }

    /**
     * Getter for the target module name.
     * @return The target module name.
     */
    public String getModuleName() {
        return moduleName;
    }

    /**
     * Setter for the target module name.
     * @param moduleName The new target module name.
     */
    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
    }

    /**
     * Getter for the page name.
     * @return The target page name.
     */
    public String getPageName() {
        return pageName;
    }

    /**
     * Setter for the page name.
     * @param pageName The new target page name.
     */
    public void setPageName(String pageName) {
        this.pageName = pageName;
    }

    /**
     * Getter for the target path of the page.
     * @return The target path of the page.
     */
    public String getPath() {
        return path;
    }

    /**
     * Setter for the target path of the page.
     * @param path The new target path of the page.
     */
    public void setPath(String path) {
        this.path = path;
    }

    /**
     * Getter for the request body.
     * @return The request body.
     */
    public String getBody() {
        return body;
    }

    /**
     * Setter for the request body.
     * @param body The new request body.
     */
    public void setBody(String body) {
        this.body = body;
    }

    /**
     * Getter for the request param.
     * @return The request param.
     */
    public String getParam() {
        return param;
    }

    /**
     * Setter for the request param.
     * @param param The new request param.
     */
    public void setParam(String param) {
        this.param = param;
    }

    /**
     * Getter for the content.
     * The content was the response of the request.
     * @return The response of the request.
     */
    public String getContent() {
        return content;
    }

    /**
     * Setter for the content.
     * @param content The new content.
     */
    public void setContent(String content) {
        this.content = content;
    }

    /**
     * Getter for the target page.
     * @return The target page.
     */
    public Page getPage() {
        return page;
    }

    /**
     * Setter for the target page.
     * @param page The new target page.
     */
    public void setPage(Page page) {
        this.page = page;
    }

    /**
     * Setter for the target module.
     * @param module The target module.
     */
    public void setModule(Module module) {
        this.module = module;
    }

    /**
     * Getter for the target module.
     * @return The target module.
     */
    public Module getModule() {
        return module;
    }
}
