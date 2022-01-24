package com.chillycheesy.hometracker.commands;

import com.chillycheesy.hometracker.commands.operator.OperatorFinder;
import com.chillycheesy.hometracker.commands.operator.OperatorListener;

public class Operator {

    private int priority;
    private OperatorListener listener;
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
