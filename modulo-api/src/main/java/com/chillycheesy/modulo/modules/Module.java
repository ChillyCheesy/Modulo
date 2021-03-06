package com.chillycheesy.modulo.modules;

import com.chillycheesy.modulo.ModuloAPI;
import com.chillycheesy.modulo.event.OnLoadEvent;
import com.chillycheesy.modulo.event.OnStartEvent;
import com.chillycheesy.modulo.event.OnStopEvent;

import java.util.List;
import java.util.Objects;
import java.util.jar.JarFile;

/**
 * HTModule is the superclass of all the <i>HomeTracker</i> module.<br>
 * A module is a functionality of HomeTracker that has its own management and behaviour.<br>
 *
 * To create your own HTModule you just have to extend HTModule<br>
 *
 * @author Geoffrey Vaniscotte
 */
public abstract class Module {

    private ModuleConfig config;
    private JarFile jarFile;

    /**
     * @param name             it's how your HTModule will be call by <i>HomeTracker</i><br>
     *                         note that two HTModules can't have the same name (you won't be able to add it to the {@link ModuleManager}
     * @param version          the actual version of your module.
     * @param authors          the list of persons that works on your module.
     * @param dependencies     the list of modules that your module name need to work, the dependencies will be load before your module get load by the {@link ModuleLoader} (your module <b>cannot</b> load without them).
     * @param softDependencies the list of modules that your module name can use to work with but without being an obligation (your module <b>can</b> load without them).
     * @param main             the main file of your jar (example: com.dev.MyAwesomeModule).
     */
    public Module(String name, String version, List<String> authors, List<String> dependencies, List<String> softDependencies, String main) {
        this(new ModuleConfig(name, version, authors, dependencies, softDependencies, main, null));
    }

    /**
     * @param name             it's how your HTModule will be call by <i>HomeTracker</i><br>
     *                         note that two HTModules can't have the same name (you won't be able to add it to the {@link ModuleManager}
     * @param version          the actual version of your module.
     * @param authors          the list of persons that works on your module.
     * @param dependencies     the list of modules that your module name need to work, the dependencies will be loaded before your module get load by the {@link ModuleLoader} (your module <b>cannot</b> load without them).
     * @param softDependencies the list of modules that your module name can use to work with but without being an obligation (your module <b>can</b> load without them).
     * @param main             the main file of your jar (example: com.dev.MyAwesomeModule).
     * @param mainPageName     the name of the main page by default it is "index".
     */
    public Module(String name, String version, List<String> authors, List<String> dependencies, List<String> softDependencies, String main, String mainPageName) {
        this(new ModuleConfig(name, version, authors, dependencies, softDependencies, main, mainPageName));
    }

    /**
     * Init your module by passing the {@link ModuleConfig} object
     * @param config the configuration of your module
     */
    public Module(ModuleConfig config) {
        this.config = config;
    }

    public Module() {
        config = new ModuleConfig();
    }

    /**
     * Method called when the {@link #load()} method is called
     */
    protected abstract <E extends Throwable> void onLoad() throws E;

    /**
     * Method called when the {@link #start()} method is called
     */
    protected abstract <E extends Throwable> void onStart() throws E;

    /**
     * Method called when the {@link #stop()} method is called
     */
    protected abstract <E extends Throwable> void onStop() throws E;

    /**
     * Method calls to load the module<br>
     * First method call by the {@link ModuleLoader}<br>
     * Call the {@link #onLoad()} method
     */
    public void load() {
        final OnLoadEvent onLoadEvent = (OnLoadEvent) new OnLoadEvent(this).setCancelableAction(() -> {
            try {
                ModuloAPI.getLogger().info(this, "Module \"" + getName() + "\" version \"" + getVersion() + "\" is loading.");
                this.onLoad();
            } catch (Exception e) {
                ModuloAPI.getLogger().error(this, e.getMessage());
                e.printStackTrace();
            }
        });
        ModuloAPI.getEvent().getEventManager().emitEvent(this, onLoadEvent);
    }

    /**
     * Method calls to start the module<br>
     * Call the {@link #onStart()} method
     */
    public void start() {
        final OnStartEvent onStartEvent = (OnStartEvent) new OnStartEvent(this).setCancelableAction(() -> {
            try {
                ModuloAPI.getLogger().info(this, "Module \"" + getName() + "\" version \"" + getVersion() + "\" is starting.");
                this.onStart();
            } catch (Exception e) {
                ModuloAPI.getLogger().error(this, e.getMessage());
                e.printStackTrace();
            }
        });
        ModuloAPI.getEvent().getEventManager().emitEvent(this, onStartEvent);

    }

    /**
     * Method calls to stop the module<br>
     * Also called when an error occurs and the module can't keep working, and when <i>HomeTracker shut down</i>
     * Call the {@link #onStop()} method
     */
    public void stop() {
        final OnStopEvent onStopEvent = (OnStopEvent) new OnStopEvent(this).setCancelableAction(() -> {
            try {
                ModuloAPI.getLogger().info(this, "Module \"" + getName() + "\" version \"" + getVersion() + "\" is stopping.");
                this.onStop();
            } catch (Exception e) {
                ModuloAPI.getLogger().error(this, e.getMessage());
                e.printStackTrace();
            }
        });
        ModuloAPI.getEvent().getEventManager().emitEvent(this, onStopEvent);

    }

    public String getName() {
        return config.getName();
    }

    public void setName(String name) {
        config.setName(name);
    }

    public String getVersion() {
        return config.getVersion();
    }

    public void setVersion(String version) {
        config.setVersion(version);
    }

    public List<String> getAuthors() {
        return config.getAuthors();
    }

    public void setAuthors(List<String> authors) {
        config.setAuthors(authors);
    }

    public List<String> getDependencies() {
        return config.getDependencies();
    }

    public void setDependencies(List<String> dependencies) {
        config.setDependencies(dependencies);
    }

    public List<String> getSoftDependencies() {
        return config.getSoftDependencies();
    }

    public void setSoftDependencies(List<String> softDependencies) {
        config.setSoftDependencies(softDependencies);
    }

    public String getMain() {
        return config.getMain();
    }

    public void setMain(String main) {
        config.setMain(main);
    }

    public String getMainPage() {
        return config.getMainPageName();
    }

    public void setMainPage(String mainPage) {
        config.setMainPageName(mainPage);
    }

    public ModuleConfig getConfig() {
        return config;
    }

    public void setConfig(ModuleConfig config) {
        this.config = config;
    }

    public JarFile getJarFile() {
        return jarFile;
    }

    public void setJarFile(JarFile jarFile) {
        this.jarFile = jarFile;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Module module = (Module) o;
        return Objects.equals(config, module.config);
    }

}
