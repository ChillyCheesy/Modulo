package com.chillycheesy.modulo.modules;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Represent the configuration of a module.<br>
 * The attributes are made to be the same you put in your module.xml file (see {@link ModuleBuilder} for details)
 */
public class ModuleConfig {

    /**
     * The default name of the main page, by defaul it's set to "index"
     */
    public static final String DEFAULT_MAIN_PAGE_NAME = "index";

    private String name;
    private String version;
    private List<String> authors;

    private List<String> dependencies = new ArrayList<>();
    private List<String> softDependencies = new ArrayList<>();

    private String main;


    /**
     * @param name             it's how your HTModule will be call by <i>HomeTracker</i><br>
     *                         note that two HTModules can't have the same name (you won't be able to add it to the {@link ModuleManager}
     * @param version          the actual version of your module
     * @param authors          the list of persons that works on your module
     * @param dependencies     the list of modules that your module need to work, the dependencies will be load before your module get load by the {@link ModuleLoader} (your module <b>cannot</b> load without them)
     * @param softDependencies the list of modules that your module can use to work with but without being an obligation (your module <b>can</b> load without them)
     * @param main             the main file of your jar (example: com.dev.MyAwesomeModule)
     */
    public ModuleConfig(String name, String version, List<String> authors, List<String> dependencies, List<String> softDependencies, String main) {
        this.name = name;
        this.version = version;
        this.authors = authors;
        this.dependencies = dependencies;
        this.softDependencies = softDependencies;
        this.main = main;
    }

    public ModuleConfig(ModuleConfig config) {
        this(config.getName(), config.getVersion(), config.getAuthors(), config.getDependencies(), config.getSoftDependencies(), config.getMain());
    }

    public ModuleConfig() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    /**
     *
     * @return the list of persons that work on this module
     */
    public List<String> getAuthors() {
        return authors;
    }

    public void setAuthors(List<String> authors) {
        this.authors = authors;
    }

    public List<String> getDependencies() {
        return dependencies;
    }

    public void setDependencies(List<String> dependencies) {
        this.dependencies = dependencies;
    }

    public List<String> getSoftDependencies() {
        return softDependencies;
    }

    public void setSoftDependencies(List<String> softDependencies) {
        this.softDependencies = softDependencies;
    }

    /**
     *
     * @return the path of the main file in the jar of this module
     */
    public String getMain() {
        return main;
    }

    public void setMain(String main) {
        this.main = main;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ModuleConfig that = (ModuleConfig) o;
        return Objects.equals(name, that.name) &&
                Objects.equals(version, that.version) &&
                Objects.equals(authors, that.authors) &&
                Objects.equals(dependencies, that.dependencies) &&
                Objects.equals(softDependencies, that.softDependencies) &&
                Objects.equals(main, that.main);
    }

    @Override
    public String toString() {
        return "ModuleConfig{" +
                "name='" + name + '\'' +
                ", version='" + version + '\'' +
                ", authors=" + authors +
                ", dependencies=" + dependencies +
                ", softDependencies=" + softDependencies +
                ", main='" + main + '\'' +
                '}';
    }
}
