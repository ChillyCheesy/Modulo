package com.owle.hometracker.event;

/**
 * This event was wen a http UPDATE request was emit.
 * @see RequestEvent
 * @author henouille
 */
public class UpdateRequestEvent extends RequestEvent {

    /**
     * Create a new update request event.
     * @param moduleName The target module.
     * @param pageName The target page. (it should be a page of the target module)
     * @param path The target path of the page.
     * @param param The param of the page.
     * @param body The body request.
     */
    public UpdateRequestEvent(String moduleName, String pageName, String path, String param, String body) {
        super(moduleName, pageName, path, param, body);
    }

}
