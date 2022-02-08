package com.chillycheesy.hometracker.commands.operator.natif;

import com.chillycheesy.hometracker.commands.CommandFlow;
import com.chillycheesy.hometracker.commands.operator.BooleanOperator;
import com.chillycheesy.hometracker.commands.operator.builder.Operator;
import com.chillycheesy.hometracker.commands.operator.builder.OperatorFindByRegex;
import com.chillycheesy.hometracker.modules.Module;
import com.chillycheesy.hometracker.utils.Priority;
import com.chillycheesy.hometracker.utils.exception.CommandException;

/**
 * Operator that return true if at least one of the parameter is true, false if both are false or both are true.
 * This operator only work with a boolean before and after the symbol "|&" or "&|"
 * Exemple :
 *         true &| true => false
 *         true |& false => true
 *         false &| true => true
 *         false |& false => false
 */
@Operator(Priority.NEUTRAL)
@OperatorFindByRegex("((\\&\\|)|(\\|\\&))")
public class XorOperator extends BooleanOperator {

    @Override
    public CommandFlow onOperate(Module module, CommandFlow left, CommandFlow center, CommandFlow right) throws CommandException {
        return super.applyOperation(left, center, right, "((\\&\\|)|(\\|\\&))", this::xor);
    }

    private boolean xor(boolean left, boolean right){
        return left ^ right;
    }
}
