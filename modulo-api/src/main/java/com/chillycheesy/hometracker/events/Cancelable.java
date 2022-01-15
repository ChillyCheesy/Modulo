package com.chillycheesy.hometracker.events;

/**
 * It can be applied on a event.
 * If the was cancel the event cause was cancel.
 * @author henouille
 */
public interface Cancelable {

    /**
     * Check if the event was cancel.
     * @return The cancel status.
     */
    boolean isCanceled();

    /**
     * Setter for the cancel status.
     * @param cancel The new cancel status.
     */
    void setCanceled(boolean cancel);

    /**
     * Setter for the cancelable action.
     * @param action The action to execute.
     */
    void setAction(CancelableAction action);

    /**
     * Getter for the cancelable action.
     * @return The action to execute.
     */
    CancelableAction getAction();

}
