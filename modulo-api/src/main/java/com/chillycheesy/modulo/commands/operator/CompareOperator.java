package com.chillycheesy.modulo.commands.operator;

import com.chillycheesy.modulo.commands.CommandFlow;
import com.chillycheesy.modulo.commands.builder.CommandFlowBuilder;
import com.chillycheesy.modulo.utils.Function2;

/**
 * Utils class to help the creation of operators that compare two number.
 */
public abstract class CompareOperator implements OperatorListener{

    protected final String NUMBER_REGEX = "-?\\d+\\.?\\d*";

    /**
     * Apply a number operation in a givens {@link CommandFlow}.
     * The method get the operator the {@link CommandFlow} arguments, the number at the left and the number at the right.
     *
     * Then the method will replace the finds elements with the result of the operation inside the {@link CommandFlow}.
     *
     * @param left the number at the left of the operator.
     * @param center the operator.
     * @param right the number at the right of the operator.
     * @param operator the operator regex.
     * @param operation the operation to apply.
     * @return the {@link CommandFlow} with the result of the operation.
     */
    protected CommandFlow applyOperation(CommandFlow left, CommandFlow center, CommandFlow right, String operator, Function2<Double, Double, Boolean> operation) {
        final double leftNumber = Double.parseDouble(CommandFlowBuilder.extractFromFlux(left, NUMBER_REGEX + "$", "0"));
        final double rightNumber = Double.parseDouble(CommandFlowBuilder.extractFromFlux(right, "^" + NUMBER_REGEX));
        final CommandFlow flux = CommandFlowBuilder.combine(left.getAliasManager(), left, center, right);
        flux.setContent(flux.getContent().replaceFirst(NUMBER_REGEX + "\\s*" + operator + "\\s*" + NUMBER_REGEX,
                operation.apply(leftNumber, rightNumber) + ""));
        return flux;
    }
}
