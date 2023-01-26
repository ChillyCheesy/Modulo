package com.chillycheesy.modulo.pages;

import com.chillycheesy.modulo.ModuloAPI;
import com.chillycheesy.modulo.modules.Module;
import com.chillycheesy.modulo.pages.factory.PageFactory;
import com.chillycheesy.modulo.utils.Logger;
import com.chillycheesy.modulo.utils.Manager;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
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

    public boolean buildAndRegisterPage(Module module, Object ...objects) {
        return Arrays.stream(objects).allMatch(object -> this.buildAndRegisterPage(module, object));
    }

    public boolean buildAndRegisterPage(Module module, Object object) {
        try {
            final List<Page> pages = PageFactory.createPages(object);
            return registerItem(module, pages.toArray(new Page[0]));
        } catch (InvocationTargetException | NoSuchMethodException | InstantiationException | IllegalAccessException e) {
            final Logger logger = ModuloAPI.getLogger();
            logger.error(module, "Error while building page for module " + module.getName());
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean registerItem(Module module, Page ...items) {
        return Arrays.stream(items).allMatch(item -> {
            final Optional<Page> pageWithSameName = super.getAllItems().stream().filter(page -> page.getName().equals(item.getName())).findAny();
            boolean success = true;
            if (pageWithSameName.isPresent()) {
                final Page findPage = pageWithSameName.get();
                final Module findPageModule = super.getModuleByItem(findPage);
                success = super.removeItem(findPageModule, findPage);
            }
            return success && super.registerItem(module, item);
        });
    }

    /**
     * Call all registered {@link Page} concern by the request.
     * @param request The request.
     * @param response The response.
     * @return True if the request has been handled by a {@link Page}.
     */
    public boolean response(HttpServletRequest request, HttpServletResponse response) throws IOException {
        final Queue<Page> pages = getMatchedPages(request);
        Page page;
        while ((page = pages.poll()) != null) {
            System.out.println(page.getName());
            if (page.response(request, response)) {
                return true;
            }
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
            .sorted(Comparator.comparingInt(page -> -page.getPath().length()))
            .collect(Collectors.toCollection(LinkedBlockingQueue::new));
    }

}