package com.chillycheesy.hometracker.commands;

import com.chillycheesy.hometracker.commands.operator.OperatorListener;
import com.chillycheesy.hometracker.utils.Function2;

public abstract class NumberOperator implements OperatorListener {

    protected final String NUMBER_REGEX = "-?\\d+\\.?\\d*";

    protected CommandFlux applyOperation(CommandFlux left, CommandFlux center, CommandFlux right, String operator, Function2<Double, Double, Double> operation) {
        final double leftNumber = Double.parseDouble(FluxBuilder.extractFromFlux(left, NUMBER_REGEX + "$", "0"));
        final double rightNumber = Double.parseDouble(FluxBuilder.extractFromFlux(right, "^" + NUMBER_REGEX));
        final CommandFlux flux = FluxBuilder.combine(left, center, right);
        flux.setContent(flux.getContent().replaceFirst(NUMBER_REGEX + "\\s*" + operator + "\\s*" + NUMBER_REGEX,
                operation.apply(leftNumber, rightNumber) + ""));
        return flux;
    }

}
