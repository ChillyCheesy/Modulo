package fr.owle.hometracker.modules;

import fr.owle.hometracker.HTAPI;
import fr.owle.hometracker.event.OnLoadEvent;
import fr.owle.hometracker.event.OnStartEvent;
import fr.owle.hometracker.event.OnStopEvent;

import java.util.List;
import java.util.Objects;
import java.util.jar.JarFile;

/**
 * HTModule is the superclass of all the <i>HomeTracker</i> module.<br>
 * A module is a functionality of HomeTracker that has it's own management and behaviour.<br>
 *
 * To create your own HTModule you just have to extends HTModule<br>
 *
 * @author Geoffrey Vaniscotte
 */
public abstract class HTModule {

    private HTModuleConfig config;
    private JarFile jarFile;

    /**
     * @param name             it's how your HTModule will be call by <i>HomeTracker</i><br>
     *                         note that two HTModules can't have the same name (you won't be able to add it to the {@link fr.owle.hometracker.modules.ModuleManager}
     * @param version          the actual version of your module.
     * @param authors          the list of persons that works on your module.
     * @param dependencies     the list of modules that your module name need to work, the dependencies will be load before your module get load by the {@link fr.owle.hometracker.modules.ModuleLoader} (your module <b>cannot</b> load without them).
     * @param softDependencies the list of modules that your module name can use to work with but without being an obligation (your module <b>can</b> load without them).
     * @param main             the main file of your jar (example: com.dev.MyAwesomeModule).
     */
    public HTModule(String name, String version, List<String> authors, List<String> dependencies, List<String> softDependencies, String main) {
        this(new HTModuleConfig(name, version, authors, dependencies, softDependencies, main, null));
    }

    /**
     * @param name             it's how your HTModule will be call by <i>HomeTracker</i><br>
     *                         note that two HTModules can't have the same name (you won't be able to add it to the {@link fr.owle.hometracker.modules.ModuleManager}
     * @param version          the actual version of your module.
     * @param authors          the list of persons that works on your module.
     * @param dependencies     the list of modules that your module name need to work, the dependencies will be load before your module get load by the {@link fr.owle.hometracker.modules.ModuleLoader} (your module <b>cannot</b> load without them).
     * @param softDependencies the list of modules that your module name can use to work with but without being an obligation (your module <b>can</b> load without them).
     * @param main             the main file of your jar (example: com.dev.MyAwesomeModule).
     * @param mainPageName     the name of the main page by default it is "index".
     */
    public HTModule(String name, String version, List<String> authors, List<String> dependencies, List<String> softDependencies, String main, String mainPageName) {
        this(new HTModuleConfig(name, version, authors, dependencies, softDependencies, main, mainPageName));
    }

    /**
     * Init your module by passing the {@link fr.owle.hometracker.modules.HTModuleConfig} object
     * @param config the configuration of your module
     */
    public HTModule(HTModuleConfig config) {
        this.config = config;
    }

    public HTModule() {
        config = new HTModuleConfig();
    }

    /**
     * Method called when the {@link #load()} method is called
     */
    protected abstract void onLoad() throws Exception;

    /**
     * Method called when the {@link #start()} method is called
     */
    protected abstract void onStart() throws Exception;

    /**
     * Method called when the {@link #stop()} method is called
     */
    protected abstract void onStop() throws Exception;

    /**
     * Method calls to load the module<br>
     * First method call by the {@link fr.owle.hometracker.modules.ModuleLoader}<br>
     * Call the {@link #onLoad()} method
     */
    public void load() {
        HTAPI.getEvent().getEventManager().emitEvent(HTAPI.getHTAPI(), new OnLoadEvent(this));
        try {
            this.onLoad();
        } catch (Exception e) {
            HTAPI.getLogger().error(this, e.getMessage());
        }
    }

    /**
     * Method calls to start the module<br>
     * Call the {@link #onStart()} ()} method
     */
    public void start() {
        HTAPI.getEvent().getEventManager().emitEvent(HTAPI.getHTAPI(), new OnStartEvent(this));
        try {
            this.onStart();
        } catch (Exception e) {
            HTAPI.getLogger().error(this, e.getMessage());
        }
    }

    /**
     * Method calls to stop the module<br>
     * Also called when an error occurs and the module can't keep working, and when <i>HomeTracker shut down</i>
     * Call the {@link #onStop()} ()} method
     */
    public void stop() {
        HTAPI.getEvent().getEventManager().emitEvent(HTAPI.getHTAPI(), new OnStopEvent(this));
        try {
            this.onStop();
        } catch (Exception e) {
            HTAPI.getLogger().error(this, e.getMessage());
        }
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

    public HTModuleConfig getConfig() {
        return config;
    }

    public void setConfig(HTModuleConfig config) {
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
        HTModule module = (HTModule) o;
        return Objects.equals(config, module.config);
    }

}
