package com.chillycheesy.hometracker.commands.operator;

import com.chillycheesy.hometracker.commands.CommandFlow;
import com.chillycheesy.hometracker.commands.builder.CommandFlowBuilder;
import com.chillycheesy.hometracker.utils.Function2;

public abstract class CompareOperator implements OperatorListener{

    protected final String NUMBER_REGEX = "-?\\d+\\.?\\d*";

    protected CommandFlow applyOperation(CommandFlow left, CommandFlow center, CommandFlow right, String operator, Function2<Double, Double, Boolean> operation) {
        final double leftNumber = Double.parseDouble(CommandFlowBuilder.extractFromFlux(left, NUMBER_REGEX + "$", "0"));
        final double rightNumber = Double.parseDouble(CommandFlowBuilder.extractFromFlux(right, "^" + NUMBER_REGEX));
        final CommandFlow flux = CommandFlowBuilder.combine(left.getAliasManager(), left, center, right);
        flux.setContent(flux.getContent().replaceFirst(NUMBER_REGEX + "\\s*" + operator + "\\s*" + NUMBER_REGEX,
                operation.apply(leftNumber, rightNumber) + ""));
        return flux;
    }
}
