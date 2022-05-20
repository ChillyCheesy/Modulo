package com.chillycheesy.modulo.modules;

/**
 * Class that contain the different object needed to work with your {@link Module}.
 * It's containing the {@link ModuleManager} and the {@link ModuleLoader}.
 */
public class ModuleContainer {

    private static ModuleManager manager;
    private static ModuleLoader loader;

    /**
     *
     * @return the {@link ModuleManager} which is a singleton
     */
    public ModuleManager getModuleManager() {
        return manager = manager == null ? new ModuleManager() : manager;
    }
    /**
     *
     * @return the {@link ModuleLoader} which is a singleton
     */
    public ModuleLoader getModuleLoader() {
        return loader = loader == null ? new ModuleLoader() : loader;
    }

}
