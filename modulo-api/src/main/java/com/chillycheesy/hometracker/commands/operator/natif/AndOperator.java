package com.chillycheesy.hometracker.commands.operator.natif;

import com.chillycheesy.hometracker.commands.CommandFlux;
import com.chillycheesy.hometracker.commands.operator.BooleanOperator;
import com.chillycheesy.hometracker.commands.operator.builder.Operator;
import com.chillycheesy.hometracker.commands.operator.builder.OperatorFindByRegex;
import com.chillycheesy.hometracker.modules.Module;
import com.chillycheesy.hometracker.utils.Priority;
import com.chillycheesy.hometracker.utils.exception.CommandException;

/**
 * Operator that return true if the left and right parameter are true, false in any other case.
 * This operator only work with a boolean before and after the symbol "&&"
 * Exemple :
 *         true && true => true
 *         true && false => false
 *         false && true => false
 *         false && false => false
 */
@Operator(Priority.LOOK_AT_THIS_DUDE)
@OperatorFindByRegex("(\\&\\&)")
public class AndOperator extends BooleanOperator {

    @Override
    public CommandFlux onOperate(Module module, CommandFlux left, CommandFlux center, CommandFlux right) throws CommandException {
        return super.applyOperation(left, center, right, "(\\&\\&)", this::and);
    }

    private boolean and(boolean left, boolean right) {
        return left && right;
    }

}
