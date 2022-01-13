package com.owle.hometracker.utils;

import com.owle.hometracker.modules.Module;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Manager<T> {

    protected final Map<Module, List<T>> managedItems;

    public Manager() {
        this.managedItems = new HashMap<>();
    }

    public boolean registerItem(Module module, T item) {
        if (item == null) return false;
        if (!managedItems.containsKey(module))
            managedItems.put(module, new ArrayList<>());
        final List<T> moduleListeners = managedItems.get(module);
        moduleListeners.add(item);
        return true;
    }

    public boolean registerItems(Module module, T...items) {
        boolean success = true;
        for (T item : items)
            if (!registerItem(module, item))
                success = false;
        return success;
    }

    public boolean removeItem(Module module, T item) {
        if (!managedItems.isEmpty()) {
            final List<T> items = managedItems.get(module);
            return items.remove(item);
        }
        return false;
    }

    public boolean removeItems(Module module, T...items) {
        boolean success = true;
        for (T item : items)
            if (!removeItem(module, item))
                success = false;
        return success;
    }

    public void removeAllItems(Module module){
        managedItems.remove(module);
    }

    public List<T> getItemByModule(Module module) {
        return this.managedItems.get(module);
    }

    public List<T> getAllItems() {
        List<T> list = new ArrayList<>();
        managedItems.forEach((module, item) -> list.addAll(item));
        return list;
    }

}
