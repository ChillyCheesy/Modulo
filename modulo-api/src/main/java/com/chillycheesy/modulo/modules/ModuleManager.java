package com.chillycheesy.modulo.modules;

import com.chillycheesy.modulo.signals.SignalManager;
import com.chillycheesy.modulo.ModuloAPI;
import com.chillycheesy.modulo.events.EventManager;
import com.chillycheesy.modulo.utils.exception.HTModuleNotFoundException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Contains and manages all the {@link Module} of <i>HomeTracker</i>
 */
public class ModuleManager {

    private final List<Module> modules;

    public ModuleManager(){
        this.modules = new ArrayList<>();
    }

    private boolean addModule(Module module) {
        if (!containsModule(module.getName())) {
            modules.add(module);
            module.start();
            return true;
        }
        return false;
    }

    private boolean removeModule(Module module) {
        return modules.remove(module);
    }

    private boolean removeModule(String name) {
        try {
            final Module module = getModule(name);
            return removeModule(module);
        } catch (HTModuleNotFoundException e) {
            return false;
        }
    }

    /**
     * Add modules to the modules manager
     * @param modules modules you want to add
     */
    void addModule(Module...modules) {
        Arrays.stream(modules).forEach(this::addModule);
    }

    /**
     * Remove modules from the modules manager
     * @param modules modules you want to remove
     */
    void removeModule(Module...modules) {
        Arrays.stream(modules).forEach(this::removeModule);
    }

    /**
     * Works like {@link #removeModule(Module...)} but by passing the names of the modules
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
    public Module getModule(String name) throws HTModuleNotFoundException {
        for (Module m : modules)
            if (m.getName().equals(name))
                return m;
        throw new HTModuleNotFoundException(name);
    }

    /**
     * Works like {@link #getModule(String)} but by passing the class of the module
     * @param moduleClass class of the wanted module
     * @return the module being from the class passed in parameter
     * @throws HTModuleNotFoundException if the module you want is not int the modules manager
     */
    public Module getModule(Class<? extends Module> moduleClass) throws HTModuleNotFoundException {
        for(Module m : modules)
            if(m.getClass().equals(moduleClass))
                return m;
        throw new HTModuleNotFoundException("of class " + moduleClass.getName());
    }

    /**
     * Check if a module is currently in the manager
     * @param module module you want to check
     * @return true if the manager contains the module, false if it doesn't
     */
    public boolean containsModule(Module module) {
        return containsModule(module.getName());
    }

    /**
     * Works like {@link #containsModule(Module)} but by passing the name of the module
     * @param name name of the module you want to check
     * @return true if the manager contains a module with this name, false if it doesn't
     */
    public boolean containsModule(String name) {
        try {
            return getModule(name) != null;
        } catch (HTModuleNotFoundException e) {
            return false;
        }
    }

    /**
     * Works like {@link #containsModule(Module)} and {@link #containsModule(String)} but by passing the class of the module
     * @param moduleClass class of the module
     * @return true if the manager contains a module of this class, false if it doesn't
     */
    public boolean containsModule(Class<? extends Module> moduleClass) {
        Module module = null;
        try {
            module = getModule(moduleClass);
        } catch (HTModuleNotFoundException e) {
            return false;
        }
        return module != null;
    }

    /**
     * Method that stop all {@link Module}.<br>
     * It will invoke the {@link #stopModule(Module)} method.
     */
    public void stopAllModules(){
        stopModules(modules);
    }

    /**
     * Method that stop all {@link Module}.<br>
     * It will invoke the {@link #stopModule(Module)} method.
     * @param modules modules you want to stop
     */
    public void stopModules(List<Module> modules){
        while (modules.size() > 0) {
            stopModule(modules.get(0));
        }
    }

    /**
     * It stops a module, removes it from the {@link SignalManager} and the {@link EventManager} and the {@link ModuleManager}
     * @param module module you want to stop
     */
    public void stopModule(Module module){
        module.stop();
        ModuloAPI.getSignal().getSignalManager().removeAllItems(module);
        ModuloAPI.getEvent().getEventManager().removeAllItems(module);
        ModuloAPI.getPage().getPageManager().removeAllItems(module);
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
    public List<Module> getModulesCopy() {
        return new ArrayList<>(modules);
    }

}
