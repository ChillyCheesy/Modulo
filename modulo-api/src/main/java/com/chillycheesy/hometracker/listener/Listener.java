package com.chillycheesy.hometracker.listener;

import com.chillycheesy.hometracker.events.Event;
import com.chillycheesy.hometracker.events.EventHandler;
import com.chillycheesy.hometracker.modules.Module;
import com.chillycheesy.hometracker.signals.SignalHandler;

/**
 * The class that implement Listener, will be called to catch {@link Event} emit by a {@link Module}.
 *
 * To catch an {@link Event}, you need to create a public void method
 * with one parameter of {@link Event} type.
 * If the method was annotated by the {@link EventHandler} Annotation,
 * it will be invoked automatically when the unique parameter {@link Event}
 * is the same type than the emitted {@link Event}.
 *
 */
public interface Listener {
}
