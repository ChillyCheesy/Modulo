package fr.owle.hometracker.page;

import fr.owle.hometracker.HTAPI;
import fr.owle.hometracker.modules.HTModule;
import fr.owle.hometracker.modules.HTModuleConfig;
import fr.owle.hometracker.modules.ModuleManager;
import fr.owle.hometracker.pages.*;
import fr.owle.hometracker.utils.exception.HTModuleNotFoundException;
import fr.owle.hometracker.utils.exception.PageMissingIndexAnnotationException;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Class that implements {@link Page} and is used to handle the request about the {@link HTModule}.
 *
 * @author henouille
 */
@Index("modules")
public class HTModulePage implements Page {

    private final ModuleManager moduleManager;
    private final PageManager pageManager;

    public HTModulePage() {
        this(HTAPI.getModule().getModuleManager(), HTAPI.getPage().getPageManager());
    }

    public HTModulePage(ModuleManager moduleManager, PageManager pageManager) {
        this.moduleManager = moduleManager;
        this.pageManager = pageManager;
    }

    /**
     *
     * @return the list of all the {@link HTModuleConfig} in the {@link ModuleManager}
     */
    @GetRequest("")
    public List<HTModuleConfig> allModulesConfig() {
        return moduleManager.getModulesCopy().stream().map(HTModule::getConfig).collect(Collectors.toList());
    }

    /**
     * Get the {@link HTModuleConfig} of the module which name is passed in parameter
     * @param name of the {@link HTModule}
     * @return the {@link HTModuleConfig} of the module
     */
    @GetRequest("/{name}")
    public HTModuleConfig modulesConfigByName(@PathParam("name") String name) {
        final List<HTModuleConfig> config = allModulesConfig().stream()
                .filter(module -> module.getName().equals(name)).collect(Collectors.toList());
        return config.size() > 0 ? config.get(0) : null;
    }

    /**
     * Get the version of a {@link HTModule}
     * @param name of the {@link HTModule}
     * @return the version of the module
     */
    @GetRequest("/{name}/version")
    public String modulePageVersion(@PathParam("name") String name) {
        return modulesConfigByName(name).getVersion();
    }

    /**
     * Get the main class of a {@link HTModule}
     * @param name of the {@link HTModule}
     * @return the main class of the module
     */
    @GetRequest("/{name}/main")
    public String modulePageMain(@PathParam("name") String name) {
        return modulesConfigByName(name).getMain();
    }

    /**
     * Get the authors of a {@link HTModule}
     * @param name of the {@link HTModule}
     * @return the authors of the module
     */
    @GetRequest("/{name}/authors")
    public List<String> modulePageAuthors(@PathParam("name") String name) {
        return modulesConfigByName(name).getAuthors();
    }

    /**
     * Get the dependencies of a {@link HTModule}
     * @param name of the {@link HTModule}
     * @return the dependencies of the module
     */
    @GetRequest("/{name}/dependencies")
    public List<String> modulePageDependencies(@PathParam("name") String name) {
        return modulesConfigByName(name).getDependencies();
    }

    /**
     * Get the soft-dependencies of a {@link HTModule}
     * @param name of the {@link HTModule}
     * @return the soft-dependencies of the module
     */
    @GetRequest("/{name}/soft-dependencies")
    public List<String> modulePageSoftDependencies(@PathParam("name") String name) {
        return modulesConfigByName(name).getSoftDependencies();
    }

    /**
     * Get the main page name of a {@link HTModule}
     * @param name of the {@link HTModule}
     * @return the main page name of the module
     */
    @GetRequest("/{name}/main-page-name")
    public String modulePageMainPageName(@PathParam("name") String name) {
        return modulesConfigByName(name).getMainPageName();
    }

    /**
     * Get all the pages of a {@link HTModule}
     * @param name of the {@link HTModule}
     * @return the list of the module's pages
     */
    @GetRequest("/{name}/pages")
    public List<String> modulePages(@PathParam("name") String name) throws HTModuleNotFoundException {
        final HTModule module = moduleManager.getModule(name);
        return pageManager.getModulePages(module).stream().map(p -> {
            try {
                return pageManager.getIndexByPage(p);
            } catch (PageMissingIndexAnnotationException e) {
                HTAPI.getLogger().error(HTAPI.getHTAPI(), e.getMessage());
                return null;
            }
        }).collect(Collectors.toList());
    }

    public ModuleManager getModuleManager() {
        return moduleManager;
    }

    public PageManager getPageManager() {
        return pageManager;
    }
}
