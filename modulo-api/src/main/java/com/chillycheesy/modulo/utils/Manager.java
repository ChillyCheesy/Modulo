package com.chillycheesy.modulo.utils;

import com.chillycheesy.modulo.modules.Module;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Manager<T> {

    protected final Map<Module, List<T>> managedItems;

    public Manager() {
        this.managedItems = new HashMap<>();
    }

    private boolean registerOneItem(Module module, T item) {
        if (item == null) return false;
        if (!managedItems.containsKey(module)) managedItems.put(module, new ArrayList<>());
        final List<T> moduleListeners = managedItems.get(module);
        moduleListeners.add(item);
        return true;
    }

    public boolean registerItem(Module module, T... items) {
        boolean success = true;
        for (T item : items)
            if (!registerOneItem(module, item))
                success = false;
        return success;
    }

    private boolean removeOneItem(Module module, T item) {
        if (!managedItems.isEmpty()) {
            final List<T> items = managedItems.get(module);
            return items.remove(item);
        }
        return false;
    }

    @SafeVarargs
    public final boolean removeItem(Module module, T... items) {
        boolean success = true;
        for (T item : items)
            if (!removeOneItem(module, item))
                success = false;
        return success;
    }

    public void removeAllItems(Module module){
        managedItems.remove(module);
    }

    public List<T> getItemByModule(Module module) {
        return this.managedItems.get(module);
    }

    public Module getModuleByItem(T item) {
        for (Module module : managedItems.keySet()) {
            final List<T> items = managedItems.get(module);
            if (items.contains(item))
                return module;
        }
        return null;
    }

    public List<T> getAllItems() {
        List<T> list = new ArrayList<>();
        managedItems.forEach((module, item) -> list.addAll(item));
        return list;
    }

}
