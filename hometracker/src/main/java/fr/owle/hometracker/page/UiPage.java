package fr.owle.hometracker.page;

import fr.owle.hometracker.pages.GetRequest;
import fr.owle.hometracker.pages.Index;
import fr.owle.hometracker.pages.Page;
import fr.owle.hometracker.pages.Resource;

/**
 * A {@link Page} representing the main page of HomeTracker
 */
@Index("ui")
public class UiPage implements Page {

    @Resource
    @GetRequest("")
    public String getInterface() {
        return "hometracker-ui";
    }

}
