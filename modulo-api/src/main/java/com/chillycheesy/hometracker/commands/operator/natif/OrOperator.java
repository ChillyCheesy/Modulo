package com.chillycheesy.hometracker.commands.operator.natif;

import com.chillycheesy.hometracker.commands.CommandFlow;
import com.chillycheesy.hometracker.commands.operator.BooleanOperator;
import com.chillycheesy.hometracker.commands.operator.builder.Operator;
import com.chillycheesy.hometracker.commands.operator.builder.OperatorFindByRegex;
import com.chillycheesy.hometracker.modules.Module;
import com.chillycheesy.hometracker.utils.Priority;
import com.chillycheesy.hometracker.utils.exception.CommandException;

/**
 * Operator that return true if the left or right parameter are true, false if both are false.
 * This operator only work with a boolean before and after the symbol "||"
 * Exemple :
 *         true || true => true
 *         true || false => true
 *         false || true => true
 *         false || false => false
 */
@Operator(Priority.NEUTRAL)
@OperatorFindByRegex("(\\|\\|)")
public class OrOperator extends BooleanOperator {

    @Override
    public CommandFlow onOperate(Module module, CommandFlow left, CommandFlow center, CommandFlow right) throws CommandException {
        return super.applyOperation(left, center, right, "(\\|\\|)", this::or);
    }

    private boolean or(boolean left, boolean right) {
        return left || right;
    }
}
