package com.chillycheesy.modulo.commands.operator.natif;

import com.chillycheesy.modulo.commands.CommandFlow;
import com.chillycheesy.modulo.commands.operator.NumberOperator;
import com.chillycheesy.modulo.commands.operator.builder.Operator;
import com.chillycheesy.modulo.commands.operator.builder.OperatorFindByRegex;
import com.chillycheesy.modulo.modules.Module;
import com.chillycheesy.modulo.utils.Priority;

/**
 * Operator that execute an addition with the two parameter
 * This operator need number before and after the symbol "+"
 * Exemple :
 * <ul>
 *     <li>5 + 6 = 11</li>
 *     <li> -3 + 2 = -1</li>
 * </ul>
 */
@Operator(Priority.COMMON)
@OperatorFindByRegex("\\+")
public class PlusOperator extends NumberOperator {

    @Override
    public CommandFlow onOperate(Module caller, CommandFlow left, CommandFlow center, CommandFlow right) {
        return super.applyOperation(left, center, right, "\\+", this::plus);
    }

    private double plus(double a, double b) {
        return a + b;
    }

}
