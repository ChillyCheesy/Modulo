package com.chillycheesy.hometracker.modules;

import java.util.List;

/**
 * HTModuleAdapter is just a {@link Module} with a default implementation of the {@link #onLoad()} {@link #onStart()} {@link #onStop()} methods.<br>
 * You can extend of this class to create your module but you'll have to override those methods by yourself (override those method if you need them)
 * @author Geoffrey Vaniscotte
 */
public class ModuleAdapter extends Module {

    /**
     * @param name             it's how your HTModule will be call by <i>HomeTracker</i><br>
     *                         note that two HTModules can't have the same name (you won't be able to add it to the {@link ModuleManager}
     * @param version          the actual version of your module
     * @param authors          the list of persons that works on your module
     * @param dependencies     the list of modules that your module need to work, the dependencies will be load before your module get load by the {@link ModuleLoader} (your module <b>cannot</b> load without them)
     * @param softDependencies the list of modules that your module can use to work with but without being an obligation (your module <b>can</b> load without them)
     * @param main             the main file of your jar (example: com.dev.MyAwesomeModule)
     */
    public ModuleAdapter(String name, String version, List<String> authors, List<String> dependencies, List<String> softDependencies, String main) {
        super(name, version, authors, dependencies, softDependencies, main);
    }

    /**
     * Init your module by passing the {@link ModuleConfig} object
     * @param config the configuration of your module
     */
    public ModuleAdapter(ModuleConfig config) {
        super(config);
    }

    public ModuleAdapter() { }

    /**
     * Method called when the {@link Module#load()}  method is called
     */
    protected void onLoad() throws Exception { }

    /**
     * Method called when the {@link Module#start()} method is called
     */
    protected void onStart() throws Exception { }

    /**
     * Method called when the {@link Module#stop()}  method is called
     */
    @Override
    protected void onStop() throws Exception { }
}
