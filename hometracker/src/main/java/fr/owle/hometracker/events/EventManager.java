package fr.owle.hometracker.events;

import fr.owle.hometracker.HTAPI;
import fr.owle.hometracker.event.LogEvent;
import fr.owle.hometracker.modules.HTModule;
import fr.owle.hometracker.utils.Listener;
import fr.owle.hometracker.utils.exception.InvalidParameterEventHandlerException;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

/**
 * EventManager make the gesture between {@link Listener} and {@link Event}.
 * The EventManager instance is accessible from the {@link EventContainer}.
 * @author henouille
 */
public class EventManager {

    private final Map<HTModule, List<Listener>> listeners;

    /**
     * Create a new EventManager.
     */
    public EventManager() {
        listeners = new HashMap<>();
    }

    /**
     * Emit a event and log potential error in the {@link fr.owle.hometracker.utils.Log}.
     * @param module The emitter module.
     * @param event The emitted event.
     */
    public void emitEvent(HTModule module, Event event) {
        try {
            emit(module, event);
        } catch (InvocationTargetException | IllegalAccessException e) {
            e.printStackTrace();
            if (!(event instanceof LogEvent))
                HTAPI.getLogger().error(module, e.getMessage());
        }
    }

    /**
     * Emit an event.
     * @param module The emitter module.
     * @param event The emitted event.
     */
    public void emit(HTModule module, Event event) throws InvocationTargetException, IllegalAccessException {
        event.setEmitter(module);
        final List<Listener> moduleListeners = getAllListener();
        for (Listener listener : moduleListeners) {
            final Set<Method> methods = getCompatibleMethods(module, event, listener);
            for (Method method : methods) {
                method.invoke(listener, event);
            }
        }
    }

    /**
     * Register a listener in the manager.
     * @param module The module registerer.
     * @param listener The listener to register.
     * @return True the registration was a success.
     */
    public boolean registerEventListener(HTModule module, Listener listener) {
        try {
            if (listener == null) return false;
            if (!listeners.containsKey(module))
                listeners.put(module, new ArrayList<>());
            final List<Listener> moduleListeners = listeners.get(module);
            if (!testListener(module, listener)) return false;
            moduleListeners.add(listener);
            return true;
        } catch (InvalidParameterEventHandlerException e) {
            HTAPI.getLogger().error(HTAPI.getHTAPI(), e.getMessage());
            return false;
        }
    }

    /**
     * Register listeners in the manager.
     * @param module The module registerer.
     * @param listeners The listeners to register.
     * @return True the registration was a success.
     */
    public boolean registerEventListener(HTModule module, Listener...listeners) {
        boolean success = true;
        for (Listener listener : listeners)
            if (!registerEventListener(module, listener))
                success = false;
        return success;
    }

    /**
     * Remove listener from the manager.
     * @param module The module registerer.
     * @param listener The listener to register.
     * @return True the registration was a success.
     */
    public boolean removeEventListener(HTModule module, Listener listener) {
        if (!listeners.isEmpty()) {
            final List<Listener> moduleListeners = listeners.get(module);
            return moduleListeners.remove(listener);
        }
        return false;
    }

    /**
     * Remove listeners from the manager.
     * @param module The module registerer.
     * @param listeners The listeners to register.
     * @return True the registration was a success.
     */
    public boolean removeEventListener(HTModule module, Listener...listeners) {
        for (Listener listener : listeners)
            if (!removeEventListener(module, listener))
                return false;
        return true;
    }

    /**
     * Clear all {@link Listener} for a module.
     * @param module The target module.
     */
    public void removeAllEventListener(HTModule module){
        listeners.remove(module);
    }

    /**
     * Getter for all listeners.
     * @return All listener.
     */
    public List<Listener> getAllListener() {
        List<Listener> list = new ArrayList<>();
        listeners.forEach((module,listeners) -> list.addAll(listeners));
        return list;
    }

    private Set<Method> getCompatibleMethods(HTModule module, Event event, Listener listener) {
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
        return sortByDescendingValue(compatibleMethods).keySet();
    }

    private Map<Method, Integer> sortByDescendingValue(Map<Method, Integer> map) {
        final List<Map.Entry<Method, Integer>> sortedEntries = new ArrayList<>(map.entrySet());
        sortedEntries.sort((e1, e2) -> e2.getValue().compareTo(e1.getValue()));
        final Map<Method, Integer> result = new LinkedHashMap<>();
        for (Map.Entry<Method, Integer> entry : sortedEntries) {
            result.put(entry.getKey(), entry.getValue());
        }
        return result;
    }

    private boolean testListener(HTModule module, Listener listener) throws InvalidParameterEventHandlerException {
        final Method[] methods = listener.getClass().getMethods();
        for (Method method : methods) {
            final EventHandler eventHandler = method.getDeclaredAnnotation(EventHandler.class);
            if (eventHandler != null) {
                if (method.getParameterCount() != 1) {
                    throw new InvalidParameterEventHandlerException(module);
                }
            }
        }
        return true;
    }

}
