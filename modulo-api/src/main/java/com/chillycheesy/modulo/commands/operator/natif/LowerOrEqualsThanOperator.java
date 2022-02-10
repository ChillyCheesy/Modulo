package com.chillycheesy.modulo.commands.operator.natif;

import com.chillycheesy.modulo.commands.CommandFlow;
import com.chillycheesy.modulo.commands.operator.CompareOperator;
import com.chillycheesy.modulo.commands.operator.builder.Operator;
import com.chillycheesy.modulo.commands.operator.builder.OperatorFindByRegex;
import com.chillycheesy.modulo.modules.Module;
import com.chillycheesy.modulo.utils.Priority;
import com.chillycheesy.modulo.utils.exception.CommandException;

/**
 * Operator that return true if the left parameter is lower or equals than the right parameter, false if it is not.
 * This operator need number before and after the symbol "&lt;=".
 * Exemple:
 * <ul>
 *     <li>5 &lt;= 2 = false</li>
 *     <li>5 &lt;= 10 = true</li>
 *     <li>5 &lt;= 5 = true</li>
 * </ul>
 */
@Operator(Priority.COMMON)
@OperatorFindByRegex("<=")
public class LowerOrEqualsThanOperator extends CompareOperator {

    @Override
    public CommandFlow onOperate(Module module, CommandFlow left, CommandFlow center, CommandFlow right) throws CommandException {
        return super.applyOperation(left, center, right, "<=", this::lowerOrEqualsThan);
    }

    private boolean lowerOrEqualsThan(double a, double b){
        return a <= b;
    }
}
