package com.chillycheesy.modulo.commands.operator;

import com.chillycheesy.modulo.commands.CommandFlow;

/**
 * An Operator is represented by a priority level a two methods.
 * The first is find the operator in a {@link CommandFlow}.
 * The second is to execute the operator.
 */
public class Operator {

    /**
     * The priority level of the operator.
     */
    private int priority;
    /**
     * The method to execute the operator.
     */
    private OperatorListener listener;
    /**
     * The method to find the operator in a {@link CommandFlow}.
     */
    private OperatorFinder finder;

    public Operator() { }

    public Operator(int priority, OperatorListener listener) {
        this.priority = priority;
        this.listener = listener;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public OperatorFinder getFinder() {
        return finder;
    }

    public void setFinder(OperatorFinder finder) {
        this.finder = finder;
    }

    public OperatorListener getListener() {
        return listener;
    }

    public void setListener(OperatorListener listener) {
        this.listener = listener;
    }
}
