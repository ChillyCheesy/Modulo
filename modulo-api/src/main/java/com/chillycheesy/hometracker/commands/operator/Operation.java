package com.chillycheesy.hometracker.commands.operator;

import com.chillycheesy.hometracker.commands.CommandFlux;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Operation {

    private OperatorListener listener;
    private CommandFlux left, center, right;

    public Operation() { }

    public Operation(CommandFlux left, CommandFlux center, CommandFlux right) {
        this(left, center, right, null);
    }

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

    public static Operation buildFormRegex(CommandFlux flux, String regex, OperatorListener listener) {
        final Pattern pattern = Pattern.compile(regex);
        final Matcher matcher = pattern.matcher(flux.getContent());
        if (matcher.find()) {
            final String content = flux.getContent();
            final int startContent = 0, start = matcher.start(), end = matcher.end(), endContent = content.length();
            final CommandFlux left = new CommandFlux(content.substring(startContent, start).trim());
            final CommandFlux center = new CommandFlux(content.substring(start, end).trim());
            final CommandFlux right = new CommandFlux(content.substring(end, endContent).trim());
            return new Operation(left, center, right, listener);
        }
        return null;
    }
}
