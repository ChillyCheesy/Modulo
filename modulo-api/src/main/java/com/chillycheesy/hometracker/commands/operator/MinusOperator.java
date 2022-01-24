package com.chillycheesy.hometracker.commands.operator;

import com.chillycheesy.hometracker.commands.CommandFlux;
import com.chillycheesy.hometracker.commands.NumberOperator;
import com.chillycheesy.hometracker.utils.Priority;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Operator(Priority.IMPORTANT)
public class MinusOperator extends NumberOperator implements OperatorFinder {

    @Override
    public CommandFlux onOperate(CommandFlux left, CommandFlux center, CommandFlux right) {
        return super.applyOperation(left, center, right, "-", this::minus);
    }

    private double minus(double a, double b) {
        return a - b;
    }

    @Override
    public Operation findOperatorMatch(CommandFlux flux) {
        final Pattern pattern = Pattern.compile("\\d+\\.?\\d*\\s*-");
        final Matcher matcher = pattern.matcher(flux.getContent());
        if (matcher.find()) {
            final String content = flux.getContent();
            final int startContent = 0, start = matcher.end() - 1, end = matcher.end(), endContent = content.length();
            final CommandFlux left = new CommandFlux(content.substring(startContent, start).trim());
            final CommandFlux center = new CommandFlux(content.substring(start, end).trim());
            final CommandFlux right = new CommandFlux(content.substring(end, endContent).trim());
            return new Operation(left, center, right, this);
        }
        return null;
    }
}
