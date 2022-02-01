package com.chillycheesy.hometracker.commands.operator;

import com.chillycheesy.hometracker.commands.CommandFlux;
import com.chillycheesy.hometracker.commands.FluxBuilder;
import com.chillycheesy.hometracker.commands.operator.OperatorListener;
import com.chillycheesy.hometracker.utils.Function2;

public abstract class BooleanOperator implements OperatorListener {

    protected final String BOOLEAN_REGEX = "(true|false)";

    protected CommandFlux applyOperation(CommandFlux left, CommandFlux center, CommandFlux right, String operator, Function2<Boolean, Boolean, Boolean> operation) {
        final boolean leftBoolean = Boolean.parseBoolean(FluxBuilder.extractFromFlux(left, BOOLEAN_REGEX + "$"));
        final boolean rightBoolean = Boolean.parseBoolean(FluxBuilder.extractFromFlux(right, "^" + BOOLEAN_REGEX));
        final CommandFlux flux = FluxBuilder.combine(left.getAliasManager(), left, center, right);
        flux.setContent(flux.getContent().replaceFirst(BOOLEAN_REGEX + "\\s*" + operator + "\\s*" + BOOLEAN_REGEX,
                operation.apply(leftBoolean, rightBoolean) + ""));
        return flux;
    }

}
