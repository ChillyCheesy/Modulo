package com.chillycheesy.modulo.commands.operator;

import com.chillycheesy.modulo.commands.CommandFlow;
import com.chillycheesy.modulo.commands.builder.CommandFlowBuilder;
import com.chillycheesy.modulo.utils.Function2;

public abstract class NumberOperator implements OperatorListener {

    protected final String NUMBER_REGEX = "-?\\d+\\.?\\d*";

    protected CommandFlow applyOperation(CommandFlow left, CommandFlow center, CommandFlow right, String operator, Function2<Double, Double, Double> operation) {
        final double leftNumber = Double.parseDouble(CommandFlowBuilder.extractFromFlux(left, NUMBER_REGEX + "$", "0"));
        final double rightNumber = Double.parseDouble(CommandFlowBuilder.extractFromFlux(right, "^" + NUMBER_REGEX, "0"));
        final CommandFlow flux = CommandFlowBuilder.combine(left.getAliasManager(), left, center, right);
        flux.setContent(flux.getContent().replaceFirst(NUMBER_REGEX + "\\s*" + operator + "\\s*" + NUMBER_REGEX,
                operation.apply(leftNumber, rightNumber) + ""));
        return flux;
    }

}
