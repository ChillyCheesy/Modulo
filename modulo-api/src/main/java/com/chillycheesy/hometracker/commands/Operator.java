package com.chillycheesy.hometracker.commands;

public class Operator {

    public static final int DIVINE = Integer.MAX_VALUE;
    public static final int MAJOR = 2;
    public static final int IMPORTANT = 1;
    public static final int NEUTRAL = 0;
    public static final int MISERABLE = -1;

    private int priority;
    private OperatorListener listener;

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

    public OperatorListener getListener() {
        return listener;
    }

    public void setListener(OperatorListener listener) {
        this.listener = listener;
    }
}
