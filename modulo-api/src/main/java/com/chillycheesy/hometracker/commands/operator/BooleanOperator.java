package com.chillycheesy.hometracker.commands.operator;

import com.chillycheesy.hometracker.commands.CommandFlow;
import com.chillycheesy.hometracker.commands.builder.CommandFlowBuilder;
import com.chillycheesy.hometracker.utils.Function2;

public abstract class BooleanOperator implements OperatorListener {

    protected final String BOOLEAN_REGEX = "(true|false)";

    protected CommandFlow applyOperation(CommandFlow left, CommandFlow center, CommandFlow right, String operator, Function2<Boolean, Boolean, Boolean> operation) {
        final boolean leftBoolean = Boolean.parseBoolean(CommandFlowBuilder.extractFromFlux(left, BOOLEAN_REGEX + "$"));
        final boolean rightBoolean = Boolean.parseBoolean(CommandFlowBuilder.extractFromFlux(right, "^" + BOOLEAN_REGEX));
        final CommandFlow flux = CommandFlowBuilder.combine(left.getAliasManager(), left, center, right);
        flux.setContent(flux.getContent().replaceFirst(BOOLEAN_REGEX + "\\s*" + operator + "\\s*" + BOOLEAN_REGEX,
                operation.apply(leftBoolean, rightBoolean) + ""));
        return flux;
    }

}
