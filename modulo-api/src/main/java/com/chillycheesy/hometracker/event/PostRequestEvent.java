package com.chillycheesy.hometracker.event;

/**
 * This event was wen a http POST request was emit.
 * @see RequestEvent
 * @author henouille
 */
public class PostRequestEvent extends RequestEvent {

    /**
     * Create a new post request event.
     * @param moduleName The target module.
     * @param pageName The target page. (it should be a page of the target module)
     * @param path The target path of the page.
     * @param param The param of the page.
     * @param body The body request.
     */
    public PostRequestEvent(String moduleName, String pageName, String path, String param, String body) {
        super(moduleName, pageName, path, param, body);
    }

}
