package com.chillycheesy.hometracker.commands.operator;

import com.chillycheesy.hometracker.commands.CommandFlow;
import com.chillycheesy.hometracker.modules.Module;
import com.chillycheesy.hometracker.utils.exception.CommandException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Operation {

    private OperatorListener listener;
    private CommandFlow left, center, right;

    public Operation() { }

    public Operation(CommandFlow left, CommandFlow center, CommandFlow right) {
        this(left, center, right, null);
    }

    public Operation(CommandFlow left, CommandFlow center, CommandFlow right, OperatorListener listener) {
        this.left = left;
        this.center = center;
        this.right = right;
        this.listener = listener;
    }

    public CommandFlow apply(Module caller) throws CommandException {
        return listener.onOperate(caller, left, center, right);
    }

    public void setCenter(CommandFlow center) {
        this.center = center;
    }

    public void setLeft(CommandFlow left) {
        this.left = left;
    }

    public void setRight(CommandFlow right) {
        this.right = right;
    }

    public void setListener(OperatorListener listener) {
        this.listener = listener;
    }

    public CommandFlow getCenter() {
        return center;
    }

    public CommandFlow getLeft() {
        return left;
    }

    public CommandFlow getRight() {
        return right;
    }

    public OperatorListener getListener() {
        return listener;
    }

    public static Operation buildFormRegex(CommandFlow flux, String regex, OperatorListener listener) {
        final Pattern pattern = Pattern.compile("(?!([(\\[{].*))((?<!\\\\)" + regex + ")(?!(.*[)\\]}]))");
        final Matcher matcher = pattern.matcher(flux.getContent());
        if (matcher.find()) {
            final String content = flux.getContent();
            final int startContent = 0, start = matcher.start(), end = matcher.end(), endContent = content.length();
            final CommandFlow left = new CommandFlow(content.substring(startContent, start).trim(), flux.getAliasManager());
            final CommandFlow center = new CommandFlow(content.substring(start, end).trim(), flux.getAliasManager());
            final CommandFlow right = new CommandFlow(content.substring(end, endContent).trim(), flux.getAliasManager());
            return new Operation(left, center, right, listener);
        }
        return null;
    }
}
