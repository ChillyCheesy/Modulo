package com.owle.hometracker.listener;

import com.owle.hometracker.events.Event;
import com.owle.hometracker.events.EventHandler;
import com.owle.hometracker.modules.Module;
import com.owle.hometracker.signals.SignalHandler;

/**
 * The class that implement Listener, will be called to catch {@link Event} or
 * {@link fr.owle.hometracker.signals.Signal} emit by a {@link Module}.
 *
 * To catch an {@link Event}, you need to create a public void method
 * with one parameter of {@link Event} type.
 * If the method was annotated by the {@link EventHandler} Annotation,
 * it will be invoked automatically when the unique parameter {@link Event}
 * is the same type than the emitted {@link Event}.
 *
 * To catch an {@link fr.owle.hometracker.signals.Signal}, you need to create a public void method
 * with a String array parameter.
 * If the method was annotated by the {@link SignalHandler} Annotation,
 * it will be invoked automatically when an signal with the same will be emitted.
 *
 * @author hénouille
 */
public interface Listener {
}
