package com.chillycheesy.hometracker.commands;

public class Operation {

    private OperatorListener listener;
    private CommandFlux left, center, right;

    public Operation() { }

    public Operation(CommandFlux left, CommandFlux center, CommandFlux right, OperatorListener listener) {
        this.left = left;
        this.center = center;
        this.right = right;
        this.listener = listener;
    }

    public CommandFlux apply() {
        return listener.onOperate(left, center, right);
    }

    public void setCenter(CommandFlux center) {
        this.center = center;
    }

    public void setLeft(CommandFlux left) {
        this.left = left;
    }

    public void setRight(CommandFlux right) {
        this.right = right;
    }

    public void setListener(OperatorListener listener) {
        this.listener = listener;
    }

    public CommandFlux getCenter() {
        return center;
    }

    public CommandFlux getLeft() {
        return left;
    }

    public CommandFlux getRight() {
        return right;
    }

    public OperatorListener getListener() {
        return listener;
    }
}
