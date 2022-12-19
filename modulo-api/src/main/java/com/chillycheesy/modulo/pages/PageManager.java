package com.chillycheesy.modulo.pages;

import com.chillycheesy.modulo.ModuloAPI;
import com.chillycheesy.modulo.modules.Module;
import com.chillycheesy.modulo.utils.Log;
import com.chillycheesy.modulo.utils.Manager;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.stream.Collectors;

/**
 * Class that manage the different {@link Page} of the different {@link Module}.
 * It allows you to add, remove, get any {@link Page} from any {@link Module}.
 *
 * @author Aymeric Hénouille
 */
public class PageManager extends Manager<Page> {

    @Override
    public boolean registerItem(Module module, Page ...items) {
        return Arrays.stream(items).allMatch(item -> this.registerItem(module, item));
    }

    @Override
    public boolean registerItem(Module module, Page item) {
        final Optional<Page> pageWithSameName = super.getAllItems().stream().filter(page -> page.getName().equals(item.getName())).findAny();
        boolean success = true;
        if (pageWithSameName.isPresent()) {
            final Page findPage = pageWithSameName.get();
            final Module findPageModule = super.getModuleByItem(findPage);
            success = super.removeItem(findPageModule, findPage);
        }
        return success && super.registerItem(module, item);
    }

    /**
     * Call all registered {@link Page} concern by the request.
     * @param request The request.
     * @param response The response.
     * @return True if the request has been handled by a {@link Page}.
     */
    public boolean response(HttpServletRequest request, HttpServletResponse response) throws IOException {
        final Log logger = ModuloAPI.getLogger();
        final Queue<Page> pages = getMatchedPages(request);
        final int size = pages.size();
        Page page;
        while ((page = pages.poll()) != null) {
            final Module module = super.getModuleByItem(page);
            if (page.response(request, response)) return true;
            if (size > 1)
                logger.warn(module, "The page \"" + page.getName() + "\" generate conflict with this path: \"" + page.getPath() + "\", request : \"" + request.getServletPath() + "\"");
        }
        return false;
    }

    /**
     * Get all the {@link Page} that match the request.
     * @param request The request.
     * @return The list of {@link Page} that match the request.
     */
    private Queue<Page> getMatchedPages(HttpServletRequest request) {
        return super.managedItems.keySet().stream()
            .flatMap(module -> super.managedItems.get(module).stream())
            .filter(page -> page.isMatch(request))
            .sorted(Comparator.comparingInt(Page::getPriority))
            .collect(Collectors.toCollection(LinkedBlockingQueue::new));
    }

}