package com.owle.hometracker.events;

import com.owle.hometracker.ModuloAPI;
import com.owle.hometracker.event.LogEvent;
import com.owle.hometracker.listener.ListenerManager;
import com.owle.hometracker.utils.Log;
import com.owle.hometracker.modules.Module;
import com.owle.hometracker.listener.Listener;
import com.owle.hometracker.utils.exception.InvalidParameterEventHandlerException;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

/**
 * EventManager make the gesture between {@link Listener} and {@link Event}.
 * The EventManager instance is accessible from the {@link EventContainer}.
 * @author henouille
 */
public class EventManager extends ListenerManager {

    /**
     * Emit a event and log potential error in the {@link Log}.
     * @param module The emitter module.
     * @param event The emitted event.
     */
    public void emitEvent(Module module, Event event) {
        try {
            emit(module, event);
        } catch (InvocationTargetException | IllegalAccessException e) {
            e.printStackTrace();
            if (!(event instanceof LogEvent))
                ModuloAPI.getLogger().error(module, e.getMessage());
        }
    }

    /**
     * Emit an event.
     * @param module The emitter module.
     * @param event The emitted event.
     */
    private void emit(Module module, Event event) throws InvocationTargetException, IllegalAccessException {
        event.setEmitter(module);
        final List<Listener> moduleListeners = super.getAllItems();
        for (Listener listener : moduleListeners) {
            final Set<Method> methods = getCompatibleMethods(event, listener);
            for (Method method : methods) {
                if (event instanceof Cancelable && ((Cancelable) event).isCanceled())
                    break;
                method.invoke(listener, event);
            }
        }
    }

    @Override
    public boolean registerItem(Module module, Listener item) {
        try {
            final Method[] methods = item.getClass().getMethods();
            for (Method method : methods) {
                final EventHandler eventHandler = method.getDeclaredAnnotation(EventHandler.class);
                if (eventHandler != null && method.getParameterCount() != 1)
                    throw new InvalidParameterEventHandlerException(module);
            }
            return super.registerItem(module, item);
        } catch (InvalidParameterEventHandlerException e) {
            ModuloAPI.getLogger().error(module, e.getMessage());
            return false;
        }

    }

    private Set<Method> getCompatibleMethods(Event event, Listener listener) {
        final Map<Method, Integer> compatibleMethods = new HashMap<>();
        final Method[] methods = listener.getClass().getMethods();
        for (Method method : methods) {
            final EventHandler eventHandler = method.getDeclaredAnnotation(EventHandler.class);
            if (eventHandler != null) {
                if (method.getParameterTypes()[0].equals(event.getClass())) {
                    final int priority = eventHandler.value();
                    compatibleMethods.put(method, priority);
                }
            }
        }
        return super.sortByDescendingValue(compatibleMethods).keySet();
    }

}
