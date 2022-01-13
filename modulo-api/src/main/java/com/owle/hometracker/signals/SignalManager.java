package com.owle.hometracker.signals;

import com.owle.hometracker.ModuloAPI;
import com.owle.hometracker.event.SignalEmitEvent;
import com.owle.hometracker.events.EventHandler;
import com.owle.hometracker.events.EventManager;
import com.owle.hometracker.listener.ListenerManager;
import com.owle.hometracker.modules.Module;
import com.owle.hometracker.listener.Listener;
import com.owle.hometracker.utils.exception.InvalidParameterSignalHandlerException;

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
public class SignalManager extends ListenerManager {

    /**
     * Method that emits a signal from a {@link Module}
     * @param module the {@link Module} that emits the signal
     * @param signalName the name of the signal
     * @param args the arguments of the signal (OPTIONAL)
     */
    public void emitSignal(Module module, String signalName, String...args) {
        final EventManager eventManager = ModuloAPI.getEvent().getEventManager();
        final SignalEmitEvent emitEvent = new SignalEmitEvent(module, signalName, args);
        final List<Module> receptors = new ArrayList<>();
        emit(module, signalName, receptors, module, args);
        final Module[] receptorsArray = new Module[receptors.size()];
        emitEvent.setReceptors(receptors.toArray(receptorsArray));
        eventManager.emitEvent(module, emitEvent);
    }

    private void emit(Module module, String signalName, List<Module> receptors, Object...args) {
        super.managedItems.forEach((receptorModule, listeners) -> {
            try {
                for (Listener listener : listeners) {
                    for (Method method : getCompatibleMethods(signalName, listener)) {
                        method.invoke(listener, args);
                        receptors.add(receptorModule);
                    }
                }
            } catch (InvalidParameterSignalHandlerException | IllegalAccessException | InvocationTargetException e) {
                ModuloAPI.getLogger().error(module, e.getMessage());
            }
        });
    }

    private Set<Method> getCompatibleMethods(String signalName, Listener listener) throws InvalidParameterSignalHandlerException {
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

    /**
     * Register a signal listener which contains method decorated with {@link SignalHandler} decorator.
     * @param module which module is registering the listener.
     * @param listener the listeners you want to register.
     * @return true if the listener has been correctly register.
     */
    @Override
    public boolean registerItem(Module module, Listener listener) {
        try {
            final Method[] methods = listener.getClass().getMethods();
            for (Method method : methods) {
                final SignalHandler eventHandler = method.getDeclaredAnnotation(SignalHandler.class);
                if (eventHandler != null) {
                    if (method.getParameterCount() != 2 || !method.getParameterTypes()[0].equals(Module.class) || !method.getParameterTypes()[1].equals(String[].class))
                        throw new InvalidParameterSignalHandlerException(module);
                }
            }
            return super.registerItem(module, listener);
        } catch (InvalidParameterSignalHandlerException e) {
            ModuloAPI.getLogger().error(module, e.getMessage());
            return false;
        }
    }

}
