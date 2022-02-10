package com.chillycheesy.modulo.commands.operator.natif;

import com.chillycheesy.modulo.commands.CommandFlow;
import com.chillycheesy.modulo.commands.operator.BooleanOperator;
import com.chillycheesy.modulo.commands.operator.builder.Operator;
import com.chillycheesy.modulo.commands.operator.builder.OperatorFindByRegex;
import com.chillycheesy.modulo.modules.Module;
import com.chillycheesy.modulo.utils.Priority;
import com.chillycheesy.modulo.utils.exception.CommandException;

/**
 * Operator that return true if at least one of the parameter is true, false if both are false or both are true.
 * This operator only work with a boolean before and after the symbol "|&amp;" or "&amp;|"
 * Exemple :
 * <ul>
 *     <li>true &amp;| true = false</li>
 *     <li>true |&amp; false = true</li>
 *     <li>false &amp;| true = true</li>
 *     <li>false |&amp; false = false</li>
 * </ul>
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
