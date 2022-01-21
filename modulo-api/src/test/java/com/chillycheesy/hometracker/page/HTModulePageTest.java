package com.chillycheesy.hometracker.page;

import com.chillycheesy.hometracker.ModuloAPI;
import com.chillycheesy.hometracker.modules.ModuleConfig;
import com.chillycheesy.hometracker.modules.Module;
import com.chillycheesy.hometracker.modules.ModuleManager;
import com.chillycheesy.hometracker.pages.*;
import com.chillycheesy.hometracker.utils.exception.HTModuleNotFoundException;
import com.chillycheesy.hometracker.utils.exception.PageMissingIndexAnnotationException;
import com.owle.hometracker.pages.*;
import fr.owle.hometracker.pages.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class HTModulePageTest {

    private HTModulePage htModulePage;
    private PageManager pageManager;
    private ModuleManager moduleManager;

    private Module module1, module2;
    private ModuleConfig config1, config2;

    @BeforeEach
    public final void init() {
        moduleManager = mock(ModuleManager.class);
        pageManager = mock(PageManager.class);
        htModulePage = new HTModulePage(moduleManager, pageManager);

        module1 = mock(Module.class);
        module2 = mock(Module.class);

        config1 = new ModuleConfig("name1", "version1", new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), "main1", "page1");
        config2 = new ModuleConfig("name2", "version2", new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), "main2", "page2");

        when(module1.getConfig()).thenReturn(config1);
        when(module2.getConfig()).thenReturn(config2);

        when(module1.getName()).thenReturn(config1.getName());
        when(module2.getName()).thenReturn(config2.getName());

        final List<Module> modules = new ArrayList<>(Arrays.asList(module1, module2));
        when(moduleManager.getModulesCopy()).thenReturn(modules);
    }

    @Test
    public final void configurationTest() throws NoSuchMethodException {
        final Index indexAnnotation = HTModulePage.class.getAnnotation(Index.class);

        final Method allModulesConfig = HTModulePage.class.getMethod("allModulesConfig");
        final GetRequest allModulesConfigGetRequest = allModulesConfig.getAnnotation(GetRequest.class);

        assertNotNull(indexAnnotation);
        assertNotNull(allModulesConfigGetRequest);
        assertEquals("modules", indexAnnotation.value());
        assertEquals("", allModulesConfigGetRequest.value());

        final Method modulesConfigByName = HTModulePage.class.getMethod("modulesConfigByName", String.class);
        final GetRequest modulesConfigByNameGetRequest = modulesConfigByName.getAnnotation(GetRequest.class);
        final PathParam modulesConfigByNamePathParam = (PathParam) modulesConfigByName.getParameterAnnotations()[0][0];

        assertNotNull(modulesConfigByName);
        assertNotNull(modulesConfigByNameGetRequest);
        assertNotNull(modulesConfigByNamePathParam);
        assertEquals("/{name}", modulesConfigByNameGetRequest.value());
        assertEquals("name", modulesConfigByNamePathParam.value());

        final Method modulePageVersion = HTModulePage.class.getMethod("modulePageVersion", String.class);
        final GetRequest modulePageVersionGetRequest = modulePageVersion.getAnnotation(GetRequest.class);
        final PathParam modulePageVersionPathParam = (PathParam) modulePageVersion.getParameterAnnotations()[0][0];

        assertNotNull(modulePageVersion);
        assertNotNull(modulePageVersionGetRequest);
        assertNotNull(modulePageVersionPathParam);
        assertEquals("/{name}/version", modulePageVersionGetRequest.value());
        assertEquals("name", modulePageVersionPathParam.value());

        final Method modulePageMain = HTModulePage.class.getMethod("modulePageMain", String.class);
        final GetRequest modulePageMainGetRequest = modulePageMain.getAnnotation(GetRequest.class);
        final PathParam modulePageMainPathParam = (PathParam) modulePageMain.getParameterAnnotations()[0][0];

        assertNotNull(modulePageMain);
        assertNotNull(modulePageMainGetRequest);
        assertNotNull(modulePageMainPathParam);
        assertEquals("/{name}/main", modulePageMainGetRequest.value());
        assertEquals("name", modulePageMainPathParam.value());

        final Method modulePageAuthors = HTModulePage.class.getMethod("modulePageAuthors", String.class);
        final GetRequest modulePageAuthorsGetRequest = modulePageAuthors.getAnnotation(GetRequest.class);
        final PathParam modulePageAuthorsPathParam = (PathParam) modulePageAuthors.getParameterAnnotations()[0][0];

        assertNotNull(modulePageAuthors);
        assertNotNull(modulePageAuthorsGetRequest);
        assertNotNull(modulePageAuthorsPathParam);
        assertEquals("/{name}/authors", modulePageAuthorsGetRequest.value());
        assertEquals("name", modulePageAuthorsPathParam.value());

        final Method modulePageDependencies = HTModulePage.class.getMethod("modulePageDependencies", String.class);
        final GetRequest modulePageDependenciesGetRequest = modulePageDependencies.getAnnotation(GetRequest.class);
        final PathParam modulePageDependenciesPathParam = (PathParam) modulePageDependencies.getParameterAnnotations()[0][0];

        assertNotNull(modulePageDependencies);
        assertNotNull(modulePageDependenciesGetRequest);
        assertNotNull(modulePageDependenciesPathParam);
        assertEquals("/{name}/dependencies", modulePageDependenciesGetRequest.value());
        assertEquals("name", modulePageDependenciesPathParam.value());

        final Method modulePageSoftDependencies = HTModulePage.class.getMethod("modulePageSoftDependencies", String.class);
        final GetRequest modulePageSoftDependenciesGetRequest = modulePageSoftDependencies.getAnnotation(GetRequest.class);
        final PathParam modulePageSoftDependenciesPathParam = (PathParam) modulePageSoftDependencies.getParameterAnnotations()[0][0];

        assertNotNull(modulePageSoftDependencies);
        assertNotNull(modulePageSoftDependenciesGetRequest);
        assertNotNull(modulePageSoftDependenciesPathParam);
        assertEquals("/{name}/soft-dependencies", modulePageSoftDependenciesGetRequest.value());
        assertEquals("name", modulePageSoftDependenciesPathParam.value());

        final Method modulePageMainPageName = HTModulePage.class.getMethod("modulePageMainPageName", String.class);
        final GetRequest modulePageMainPageNameGetRequest = modulePageMainPageName.getAnnotation(GetRequest.class);
        final PathParam modulePageMainPageNamePathParam = (PathParam) modulePageMainPageName.getParameterAnnotations()[0][0];

        assertNotNull(modulePageMainPageName);
        assertNotNull(modulePageMainPageNameGetRequest);
        assertNotNull(modulePageMainPageNamePathParam);
        assertEquals("/{name}/main-page-name", modulePageMainPageNameGetRequest.value());
        assertEquals("name", modulePageMainPageNamePathParam.value());

        final Method modulePages = HTModulePage.class.getMethod("modulePages", String.class);
        final GetRequest modulePagesGetRequest = modulePages.getAnnotation(GetRequest.class);
        final PathParam modulePagesPathParam = (PathParam) modulePages.getParameterAnnotations()[0][0];

        assertNotNull(modulePages);
        assertNotNull(modulePagesGetRequest);
        assertNotNull(modulePagesPathParam);
        assertEquals("/{name}/pages", modulePagesGetRequest.value());
        assertEquals("name", modulePagesPathParam.value());
    }

    @Test
    public final void allModulesConfigTest() {
        final List<ModuleConfig> configs = htModulePage.allModulesConfig();
        assertTrue(configs.contains(config1));
        assertTrue(configs.contains(config2));
    }

    @Test
    public final void modulesConfigByNameTest() {
        assertEquals(config1, htModulePage.modulesConfigByName(config1.getName()));
        assertEquals(config2, htModulePage.modulesConfigByName(config2.getName()));
    }

    @Test
    public final void modulePageVersionTest() {
        assertEquals(config1.getVersion(), htModulePage.modulePageVersion(config1.getName()));
        assertEquals(config2.getVersion(), htModulePage.modulePageVersion(config2.getName()));
    }

    @Test
    public final void modulePageMainTest() {
        assertEquals(config1.getMain(), htModulePage.modulePageMain(config1.getName()));
        assertEquals(config2.getMain(), htModulePage.modulePageMain(config2.getName()));
    }

    @Test
    public final void modulePageAuthorsTest() {
        assertEquals(config1.getAuthors(), htModulePage.modulePageAuthors(config1.getName()));
        assertEquals(config2.getAuthors(), htModulePage.modulePageAuthors(config2.getName()));
    }

    @Test
    public final void modulePageDependenciesTest() {
        assertEquals(config1.getDependencies(), htModulePage.modulePageDependencies(config1.getName()));
        assertEquals(config2.getDependencies(), htModulePage.modulePageDependencies(config2.getName()));
    }

    @Test
    public final void modulePageSoftDependenciesTest() {
        assertEquals(config1.getSoftDependencies(), htModulePage.modulePageSoftDependencies(config1.getName()));
        assertEquals(config2.getSoftDependencies(), htModulePage.modulePageSoftDependencies(config2.getName()));
    }

    @Test
    public final void modulePageMainPageNameTest() {
        assertEquals(config1.getMainPageName(), htModulePage.modulePageMainPageName(config1.getName()));
        assertEquals(config2.getMainPageName(), htModulePage.modulePageMainPageName(config2.getName()));
    }

    @Test
    public final void getModulePagesTest() throws PageMissingIndexAnnotationException, HTModuleNotFoundException {
        final Page page1 = mock(Page.class);
        final Page page2 = mock(Page.class);
        when(moduleManager.getModule(anyString())).thenReturn(module1);
        when(pageManager.getModulePages(module1)).thenReturn(Arrays.asList(page1, page2));
        when(pageManager.getIndexByPage(page1)).thenReturn("index1");
        when(pageManager.getIndexByPage(page2)).thenReturn("index2");
        assertEquals(Arrays.asList("index1", "index2"), htModulePage.modulePages(config1.getName()));
    }

    @Test
    public final void defaultModuleManagerAndDefaultPageManagerTest() {
        final HTModulePage htModulePage = new HTModulePage();
        assertEquals(ModuloAPI.getModule().getModuleManager(), htModulePage.getModuleManager());
        assertEquals(ModuloAPI.getPage().getPageManager(), htModulePage.getPageManager());
    }

}
