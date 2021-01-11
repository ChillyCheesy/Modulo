package fr.owle.hometracker.modules;

import fr.owle.hometracker.HTAPI;
import fr.owle.hometracker.utils.exception.HTModuleNotFoundException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Contains and manages all the {@link fr.owle.hometracker.modules.HTModule} of <i>HomeTracker</i>
 */
public class ModuleManager {
    private final List<HTModule> modules;

    public ModuleManager(){
        this.modules = new ArrayList<>();
    }

    private boolean addModule(HTModule module) {
        if (!containsModule(module.getName())) {
            modules.add(module);
            module.start();
            return true;
        }
        return false;
    }

    private boolean removeModule(HTModule module) {
        return modules.remove(module);
    }

    private boolean removeModule(String name) {
        try {
            final HTModule module = getModule(name);
            return removeModule(module);
        } catch (HTModuleNotFoundException e) {
            return false;
        }
    }

    /**
     * Add modules to the modules manager
     * @param modules modules you want to add
     */
    void addModule(HTModule...modules) {
        Arrays.stream(modules).forEach(this::addModule);
    }

    /**
     * Remove modules from the modules manager
     * @param modules modules you want to remove
     */
    void removeModule(HTModule...modules) {
        Arrays.stream(modules).forEach(this::removeModule);
    }

    /**
     * Works like {@link #removeModule(HTModule...)} but by passing the names of the modules
     * @param names names of the module you want to remove
     */
    void removeModule(String... names) {
        for (String name : names)
            removeModule(name);
    }

    /**
     * Get a module using the name of the module
     * @param name name of the module you want to get
     * @return the module having the name passed in parameters
     * @throws HTModuleNotFoundException if the module you want is not int the modules manager
     */
    public HTModule getModule(String name) throws HTModuleNotFoundException {
        for(HTModule m : modules)
            if(m.getName().equals(name))
                return m;
        throw new HTModuleNotFoundException(name);
    }

    /**
     * Works like {@link #getModule(String)} but by passing the class of the module
     * @param moduleClass class of the wanted module
     * @return the module being from the class passed in parameter
     * @throws HTModuleNotFoundException if the module you want is not int the modules manager
     */
    public HTModule getModule(Class<? extends HTModule> moduleClass) throws HTModuleNotFoundException {
        for(HTModule m : modules)
            if(m.getClass().equals(moduleClass))
                return m;
        throw new HTModuleNotFoundException("of class " + moduleClass.getName());
    }

    /**
     * Check if a module is currently in the manager
     * @param module module you want to check
     * @return true if the manager contains the module, false if it doesn't
     */
    public boolean containsModule(HTModule module) {
        return containsModule(module.getName());
    }

    /**
     * Works like {@link #containsModule(HTModule)} but by passing the name of the module
     * @param name name of the module you want to check
     * @return true if the manager contains a module with this name, false if it doesn't
     */
    public boolean containsModule(String name) {
        try {
            getModule(name);
        } catch (HTModuleNotFoundException e) {
            return false;
        }
        return true;
    }

    /**
     * Works like {@link #containsModule(HTModule)} and {@link #containsModule(String)} but by passing the class of the module
     * @param moduleClass class of the module
     * @return true if the manager contains a module of this class, false if it doesn't
     */
    public boolean containsModule(Class<? extends HTModule> moduleClass) {
        HTModule module = null;
        try {
            module = getModule(moduleClass);
        } catch (HTModuleNotFoundException e) {
            return false;
        }
        return module != null;
    }

    /**
     * Method that stop all {@link fr.owle.hometracker.modules.HTModule}.<br>
     * It will invoke the {@link #stopModule(HTModule)} method.
     */
    public void stopAllModules(){
        while (modules.size() > 0) {
            stopModule(modules.get(0));
        }
    }

    /**
     * It stops a module, removes it from the {@link fr.owle.hometracker.signals.SignalManager} and the {@link fr.owle.hometracker.events.EventManager} and the {@link ModuleManager}
     * @param module
     */
    public void stopModule(HTModule module){
        module.stop();
        HTAPI.getSignal().getSignalManager().removeAllSignalListener(module);
        HTAPI.getEvent().getEventManager().removeAllEventListener(module);
        HTAPI.getPage().getPageManager().removeAllPages(module);
        removeModule(module);
    }

    /**
     * Get the number of module currently managed by the manager
     * @return the number of module
     */
    public int getModuleCount() {
        return modules.size();
    }

    /**
     * Get the started modules.
     * @return a copy of started modules.
     */
    public List<HTModule> getModulesCopy() {
        return new ArrayList<>(modules);
    }

}
