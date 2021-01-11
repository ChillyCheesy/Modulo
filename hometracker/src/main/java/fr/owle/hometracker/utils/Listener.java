package fr.owle.hometracker.utils;

/**
 * The class that implement Listener, will be called to catch {@link fr.owle.hometracker.events.Event} or
 * {@link fr.owle.hometracker.signals.Signal} emit by a {@link fr.owle.hometracker.modules.HTModule}.
 *
 * To catch an {@link fr.owle.hometracker.events.Event}, you need to create a public void method
 * with one parameter of {@link fr.owle.hometracker.events.Event} type.
 * If the method was annotated by the {@link fr.owle.hometracker.events.EventHandler} Annotation,
 * it will be invoked automatically when the unique parameter {@link fr.owle.hometracker.events.Event}
 * is the same type than the emitted {@link fr.owle.hometracker.events.Event}.
 *
 * To catch an {@link fr.owle.hometracker.signals.Signal}, you need to create a public void method
 * with a String array parameter.
 * If the method was annotated by the {@link fr.owle.hometracker.signals.SignalHandler} Annotation,
 * it will be invoked automatically when an signal with the same will be emitted.
 *
 * @author hénouille
 */
public interface Listener {
}
