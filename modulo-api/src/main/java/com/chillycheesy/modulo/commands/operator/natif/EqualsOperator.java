package com.chillycheesy.modulo.commands.operator.natif;

import com.chillycheesy.modulo.commands.CommandFlow;
import com.chillycheesy.modulo.commands.builder.CommandFlowBuilder;
import com.chillycheesy.modulo.commands.operator.OperatorListener;
import com.chillycheesy.modulo.commands.operator.builder.Operator;
import com.chillycheesy.modulo.commands.operator.builder.OperatorFindByRegex;
import com.chillycheesy.modulo.modules.Module;
import com.chillycheesy.modulo.utils.Priority;
import com.chillycheesy.modulo.utils.exception.CommandException;

/**
 * Operator that check if the right parameter is equals to the left parameter
 * This operator work with number, strings or boolean before and after the symbol "=="
 * Exemple:
 * <ul>
 *     <li>6 == 6 = true</li>
 *     <li>6 == 5 = false</li>
 *     <li>true == false = false</li>
 *     <li>"I love ewoks" == "I hate ewoks" = false</li>
 * </ul>
 */
@Operator(Priority.COMMON)
@OperatorFindByRegex("(==)")
public class EqualsOperator implements OperatorListener {

    protected final String STRING_REGEX = "\\w+";

    @Override
    public CommandFlow onOperate(Module module, CommandFlow left, CommandFlow center, CommandFlow right) throws CommandException {
        final String leftString = CommandFlowBuilder.extractFromFlux(left, STRING_REGEX + "$", "");
        final String rightString = CommandFlowBuilder.extractFromFlux(right, "^" + STRING_REGEX, "");
        final CommandFlow flux = CommandFlowBuilder.combine(left.getAliasManager(), left, center, right);
        flux.setContent(flux.getContent().replaceFirst(STRING_REGEX + "\\s*" + "==" + "\\s*" + STRING_REGEX, "" + this.equals(leftString, rightString)));
        return flux;
    }

    private boolean equals(Object left, Object right) {
        return left.equals(right);
    }
}