package com.owle.hometracker.modules;

import com.owle.hometracker.ModuloAPI;
import com.owle.hometracker.utils.exception.FileIsNotAModuleDirectoryException;
import com.owle.hometracker.utils.exception.MissingDependenciesModuleException;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.jar.JarFile;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Class that build and load all the {@link Module} in the modules folder.
 * @author henouille
 */
public class ModuleLoader {

    private final List<Module> loadedModules;
    private final List<Module> startedModule;

    /**
     * Crate a new ModuleLoader.
     */
    public ModuleLoader() {
        loadedModules = new ArrayList<>();
        startedModule = new ArrayList<>();
    }

    /**
     * Load jar inside a folder if the jar was a module.
     * It will call the load method of the module.
     *
     * @param file folder to load.
     * @throws FileIsNotAModuleDirectoryException If the target path was not a module directory.
     * @throws IOException If the file was not a JarFile.
     */
    public List<Module> loadModules(File file) throws FileIsNotAModuleDirectoryException, IOException {
        if (!file.isDirectory()) throw new FileIsNotAModuleDirectoryException(file);
        final List<Module> loadedModules = new ArrayList<>();
        for (File jarFile : Objects.requireNonNull(file.listFiles())) {
            loadedModules.add(this.loadModule(jarFile));
        }
        return loadedModules;
    }


    /**
     * Method call to load the jar files in a folder path.
     * It will call the load method of the module.
     *
     * @param filepath path of the folder file.
     * @throws FileIsNotAModuleDirectoryException If the target path was not a module directory.
     * @throws IOException If the file was not a JarFile.
     */
    public List<Module> loadModules(String filepath) throws FileIsNotAModuleDirectoryException, IOException {
        return loadModules(new File(filepath));
    }

    /**
     * Load a module already built<br>
     * It will call the load method of the module.
     *
     * @param module The module you want to load.
     */
    public Module loadModule(Module module) {
        if (!loadedModules.contains(module)) {
            module.load();
            loadedModules.add(module);
            return module;
        }
        return null;
    }

    /**
     * Load a module.
     *
     * @param file The jar file. It should be a module.
     * @throws IOException If the file was not a JarFile.
     */
    public Module loadModule(File file) throws IOException {
        return loadModule(file, URLClassLoader.newInstance(new URL[]{file.toURI().toURL()}));
    }

    /**
     * Load a module.
     *
     * @param file The jar file. It should be a module.
     * @param urlClassLoader The used classLoader.
     * @throws IOException If the file was not a JarFile.
     */
    public Module loadModule(File file, URLClassLoader urlClassLoader) throws IOException {
        if (isValid(file)) {
            final Module module = ModuleBuilder.build(file, urlClassLoader);
            return loadModule(module);
        }
        return null;
    }

    /**
     * Put and start all loaded modules in the {@link ModuleManager}.
     */
    public List<Module> startModules() throws MissingDependenciesModuleException {
        return startModules(loadedModules);
    }

    public List<Module> startModules(List<Module> modules) throws MissingDependenciesModuleException {
        for (Module module : modules)
            module = startModule(module);
        return modules;
    }

    private Module startModule(Module module) throws MissingDependenciesModuleException {
        if (!startedModule.contains(module)) {
            final ModuleManager moduleManager = ModuloAPI.getModule().getModuleManager();
            final List<String> dependencies = module.getDependencies();
            final List<String> softDependencies = module.getSoftDependencies();
            final List<Module> allDependencies = Stream.concat(dependencies.stream(), softDependencies.stream())
                    .filter(this::containLoadedModuleWithName)
                    .map(this::getLoadedModuleByName)
                    .filter(Objects::nonNull)
                    .collect(Collectors.toList());
            for (Module dependency : allDependencies) this.startModule(dependency);
            if (!startedModule.containsAll(dependencies.stream().map(this::getLoadedModuleByName).collect(Collectors.toList())))
                throw new MissingDependenciesModuleException(module, getMissingDependenciesList(dependencies));
            moduleManager.addModule(module);
            startedModule.add(module);
            return module;
        }
        return module;
    }

    private List<String> getMissingDependenciesList(List<String> dependencies) {
        dependencies.removeAll(startedModule.stream().map(Module::getName).collect(Collectors.toList()));
        return new ArrayList<>(dependencies);
    }

    private Module getLoadedModuleByName(String name) {
        for (Module module : loadedModules)
            if (module.getName().equals(name))
                return module;
        return null;
    }

    private boolean containLoadedModuleWithName(String name) {
        return getLoadedModuleByName(name) != null;
    }

    /**
     * Load a module from a jar file path
     *
     * @param filepath jar file path you want to load
     * @throws IOException If the file was not a JarFile.
     */
    public void loadModule(String filepath) throws IOException {
        loadModule(new File(filepath));
    }

    /**
     * Check if a jar file is in the good format<br>
     * see {@link ModuleBuilder} for details.
     *
     * @param file Jar file you want to check.
     * @return True if it's valid, false if it isn't.
     */
    public boolean isValid(JarFile file) {
        if (file == null) throw new NullPointerException();
        return file.getJarEntry("module.yml") != null;
    }

    /**
     * Check if a file is in the good format<br>
     * see {@link ModuleBuilder} for details.
     *
     * @param file File you want to check
     * @return True if it's valid, false if it isn't
     * @throws IOException If the file was not a JarFile.
     */
    public boolean isValid(File file) throws IOException {
        return isValid(new JarFile(file));
    }

    /**
     * Check if a jar file is in the good format<br>
     * see {@link ModuleBuilder} for details.
     *
     * @param filepath jar file path you want to check
     * @return true if it's valid, false if it isn't
     * @throws IOException If the file was not a JarFile.
     */
    public boolean isValid(String filepath) throws IOException {
        return isValid(new File(filepath));
    }

    /**
     * Get a list of loaded {@link Module}.
     * @return The loaded {@link Module} list.
     */
    public List<Module> getLoadedModules() {
        return new ArrayList<>(loadedModules);
    }

    /**
     * Get a list of started {@link Module}.
     * @return The started {@link Module} list.
     */
    public List<Module> getStartedModule() {
        return new ArrayList<>(startedModule);
    }
}
