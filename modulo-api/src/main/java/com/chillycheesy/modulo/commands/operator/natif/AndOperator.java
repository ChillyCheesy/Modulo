package com.chillycheesy.modulo.commands.operator.natif;

import com.chillycheesy.modulo.commands.CommandFlow;
import com.chillycheesy.modulo.commands.operator.BooleanOperator;
import com.chillycheesy.modulo.commands.operator.builder.Operator;
import com.chillycheesy.modulo.commands.operator.builder.OperatorFindByRegex;
import com.chillycheesy.modulo.modules.Module;
import com.chillycheesy.modulo.utils.Priority;
import com.chillycheesy.modulo.utils.exception.CommandException;

/**
 * Operator that return true if the left and right parameter are true, false in any other case.
 * This operator only work with a boolean before and after the symbol "&&"
 * Exemple :
 *         true && true => true
 *         true && false => false
 *         false && true => false
 *         false && false => false
 */
@Operator(Priority.NEUTRAL)
@OperatorFindByRegex("(\\&\\&)")
public class AndOperator extends BooleanOperator {

    @Override
    public CommandFlow onOperate(Module module, CommandFlow left, CommandFlow center, CommandFlow right) throws CommandException {
        return super.applyOperation(left, center, right, "(\\&\\&)", this::and);
    }

    private boolean and(boolean left, boolean right) {
        return left && right;
    }

}
