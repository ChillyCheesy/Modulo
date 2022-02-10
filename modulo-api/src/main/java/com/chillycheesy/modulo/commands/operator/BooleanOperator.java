package com.chillycheesy.modulo.commands.operator;

import com.chillycheesy.modulo.commands.CommandFlow;
import com.chillycheesy.modulo.commands.builder.CommandFlowBuilder;
import com.chillycheesy.modulo.utils.Function2;

/**
 * Utils class to the creation of boolean operator that takes two boolean values and returns a boolean value.
 */
public abstract class BooleanOperator implements OperatorListener {

    protected final String BOOLEAN_REGEX = "(true|false)";

    /**
     * Applies the operator to the two boolean values.
     * @param left The left boolean value.
     * @param center The center boolean value.
     * @param right The right boolean value.
     * @param operator The operator regex.
     * @param operation The operation to perform.
     * @return The result of the operation.
     */
    protected CommandFlow applyOperation(CommandFlow left, CommandFlow center, CommandFlow right, String operator, Function2<Boolean, Boolean, Boolean> operation) {
        final boolean leftBoolean = Boolean.parseBoolean(CommandFlowBuilder.extractFromFlux(left, BOOLEAN_REGEX + "$"));
        final boolean rightBoolean = Boolean.parseBoolean(CommandFlowBuilder.extractFromFlux(right, "^" + BOOLEAN_REGEX));
        final CommandFlow flux = CommandFlowBuilder.combine(left.getAliasManager(), left, center, right);
        flux.setContent(flux.getContent().replaceFirst(BOOLEAN_REGEX + "\\s*" + operator + "\\s*" + BOOLEAN_REGEX,
                operation.apply(leftBoolean, rightBoolean) + ""));
        return flux;
    }

}
