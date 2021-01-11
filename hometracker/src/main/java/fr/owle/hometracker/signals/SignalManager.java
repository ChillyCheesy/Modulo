package fr.owle.hometracker.signals;

import fr.owle.hometracker.HTAPI;
import fr.owle.hometracker.event.SignalEmitEvent;
import fr.owle.hometracker.events.EventHandler;
import fr.owle.hometracker.events.EventManager;
import fr.owle.hometracker.modules.HTModule;
import fr.owle.hometracker.utils.Listener;
import fr.owle.hometracker.utils.exception.InvalidParameterEventHandlerException;
import fr.owle.hometracker.utils.exception.InvalidParameterSignalHandlerException;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

/**
 * Class that manage all the signal of HomeTracker.
 *
 * @see EventHandler
 *
 * @author Geoffrey Vaniscotte
 */
public class SignalManager {

    private final HTModule htapi;

    private final Map<HTModule, List<Listener>> listeners;

    public SignalManager() {
        this.listeners = new HashMap<>();
        this.htapi = HTAPI.getHTAPI();
    }

    /**
     * Method that emits a signal from a {@link HTModule}
     * @param module the {@link HTModule} that emits the signal
     * @param signalName the name of the signal
     * @param args the arguments of the signal (OPTIONAL)
     */
    public void emitSignal(HTModule module, String signalName, String...args) {
        final EventManager eventManager = HTAPI.getEvent().getEventManager();
        final SignalEmitEvent emitEvent = new SignalEmitEvent(module, signalName, args);
        final List<HTModule> receptors = new ArrayList<>();
        emit(module, signalName, receptors, module, args);
        final HTModule[] receptorsArray = new HTModule[receptors.size()];
        emitEvent.setReceptors(receptors.toArray(receptorsArray));
        eventManager.emitEvent(module, emitEvent);
    }

    private void emit(HTModule module, String signalName, List<HTModule> receptors, Object...args) {
        listeners.forEach((receptorModule, listeners) -> {
            try {
                for (Listener listener : listeners) {
                    final Set<Method> methods;
                    methods = getCompatibleMethods(module, signalName, listener);
                    for (Method method : methods) {
                        method.invoke(listener, args);
                        receptors.add(receptorModule);
                    }
                }
            } catch (InvalidParameterSignalHandlerException | IllegalAccessException | InvocationTargetException e) {
                HTAPI.getLogger().error(htapi, e.getMessage());
            }
        });
    }

    private Set<Method> getCompatibleMethods(HTModule module, String signalName, Listener listener) throws InvalidParameterSignalHandlerException {
        final Map<Method, Integer> compatibleMethods = new HashMap<>();
        final Method[] methods = listener.getClass().getMethods();
        for (Method method : methods) {
            final SignalHandler eventHandler = method.getDeclaredAnnotation(SignalHandler.class);
            if (eventHandler != null) {
                if (eventHandler.value().equals(signalName)) {
                    final int priority = eventHandler.priority();
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

    private boolean testListener(HTModule module, Listener listener) throws InvalidParameterSignalHandlerException {
        final Method[] methods = listener.getClass().getMethods();
        for (Method method : methods) {
            final EventHandler eventHandler = method.getDeclaredAnnotation(EventHandler.class);
            if (eventHandler != null) {
                if (method.getParameterCount() != 2 || !method.getParameterTypes()[0].equals(HTModule.class) || !method.getParameterTypes()[1].equals(String[].class))
                    throw new InvalidParameterSignalHandlerException(module);
            }
        }
        return true;
    }

    /**
     * Register a signal listener which contains method decorated with {@link SignalHandler} decorator.
     * @param module which module is registering the listener.
     * @param listener the listeners you want to register.
     * @return true if the listener has been correctly register.
     */
    public boolean registerSignalListener(HTModule module, Listener listener) {
        try {
            if (listener == null) return false;
            if (!listeners.containsKey(module))
                listeners.put(module, new ArrayList<>());
            final List<Listener> moduleListeners = listeners.get(module);
            if (!testListener(module, listener)) return false;
            moduleListeners.add(listener);
            return true;
        } catch (InvalidParameterSignalHandlerException e) {
            HTAPI.getLogger().error(HTAPI.getHTAPI(), e.getMessage());
            return false;
        }
    }

    public boolean registerSignalListener(HTModule module, Listener...listeners) {
        for (Listener listener : listeners)
            if (!registerSignalListener(module, listener))
                return false;
        return true;
    }

    /**
     * Remove a listener from a {@link HTModule}.
     * @param module the module you want to remove listener from.
     * @param listener the listener you want to remove.
     * @return true if the listener has been correctly removed.
     */
    public boolean removeSignalListener(HTModule module, Listener listener) {
        if (!listeners.isEmpty()) {
            final List<Listener> moduleListeners = listeners.get(module);
            return moduleListeners.remove(listener);
        }
        return false;
    }

    public boolean removeSignalListener(HTModule module, Listener...listeners) {
        for (Listener listener : listeners)
            if (!removeSignalListener(module, listener))
                return false;
        return true;
    }

    /**
     * Clear all the signal listener of a {@link HTModule}
     * @param module the module you want to clear
     */
    public void removeAllSignalListener(HTModule module){
        listeners.remove(module);
    }

}
