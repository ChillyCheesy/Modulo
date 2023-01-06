package com.chillycheesy.modulo.events;

import com.chillycheesy.modulo.listener.Listener;
import com.chillycheesy.modulo.listener.ListenerManager;
import com.chillycheesy.modulo.modules.Module;
import com.chillycheesy.modulo.ModuloAPI;
import com.chillycheesy.modulo.event.LogEvent;
import com.chillycheesy.modulo.utils.Logger;
import com.chillycheesy.modulo.utils.exception.InvalidParameterEventHandlerException;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * EventManager make the gesture between {@link Listener} and {@link Event}.
 * The EventManager instance is accessible from the {@link EventContainer}.
 * @author henouille
 */
public class EventManager extends ListenerManager {

    /**
     * Emit a event and log potential error in the {@link Logger}.
     * @param module The emitter module.
     * @param event The emitted event.
     */
    public void emitEvent(Module module, Event event) {
        try {
            emit(module, event);
        } catch (InvocationTargetException | IllegalAccessException | IOException e) {
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
    private void emit(Module module, Event event) throws InvocationTargetException, IllegalAccessException, IOException {
        event.setEmitter(module);
        final List<Listener> moduleListeners = super.getAllItems();
        for (Listener listener : moduleListeners) {
            final Set<Method> methods = getCompatibleMethods(event, listener);
            for (Method method : methods)
                method.invoke(listener, event);
        }
        if (Cancelable.class.isAssignableFrom(event.getClass()) && !((Cancelable) event).isCanceled() && ((Cancelable) event).getCancelableAction() != null)
            ((Cancelable) event).getCancelableAction().action();

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
