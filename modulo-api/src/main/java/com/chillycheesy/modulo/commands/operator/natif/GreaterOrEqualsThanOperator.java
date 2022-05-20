package com.chillycheesy.modulo.commands.operator.natif;

import com.chillycheesy.modulo.commands.CommandFlow;
import com.chillycheesy.modulo.commands.operator.CompareOperator;
import com.chillycheesy.modulo.commands.operator.builder.Operator;
import com.chillycheesy.modulo.commands.operator.builder.OperatorFindByRegex;
import com.chillycheesy.modulo.modules.Module;
import com.chillycheesy.modulo.utils.Priority;
import com.chillycheesy.modulo.utils.exception.CommandException;

/**
 * Operator that return true if the left parameter is greater or equals than the right parameter, false if it is not.
 * This operator need number before and after the symbol "&gt;=".
 * Exemple:
 * <ul>
 *     <li>5 &gt;= 2 = true</li>
 *     <li>5 &gt;= 10 = false</li>
 *     <li>5 &gt;= 5 = true</li>
 * </ul>
 */
@Operator(Priority.COMMON)
@OperatorFindByRegex(">=")
public class GreaterOrEqualsThanOperator extends CompareOperator {

    @Override
    public CommandFlow onOperate(Module module, CommandFlow left, CommandFlow center, CommandFlow right) throws CommandException {
        return super.applyOperation(left, center, right, ">=", this::greaterOrEqualsThan);
    }

    private boolean greaterOrEqualsThan(double a, double b){
        return a >= b;
    }
}
