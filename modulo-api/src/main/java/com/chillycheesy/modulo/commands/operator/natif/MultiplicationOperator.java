package com.chillycheesy.modulo.commands.operator.natif;

import com.chillycheesy.modulo.commands.CommandFlow;
import com.chillycheesy.modulo.commands.operator.NumberOperator;
import com.chillycheesy.modulo.commands.operator.builder.Operator;
import com.chillycheesy.modulo.commands.operator.builder.OperatorFindByRegex;
import com.chillycheesy.modulo.modules.Module;
import com.chillycheesy.modulo.utils.Priority;

/**
 * Operator that execute a multiplication with the two parameter
 * This operator need number before and after the symbol "*"
 * Exemple :
 * <ul>
 *     <li>5 * 6 = 30</li>
 *     <li>-3 * 2 = -6</li>
 * </ul>
 */
@Operator(Priority.UNCOMMON)
@OperatorFindByRegex("\\*")
public class MultiplicationOperator extends NumberOperator {

    @Override
    public CommandFlow onOperate(Module caller, CommandFlow left, CommandFlow center, CommandFlow right) {
        return super.applyOperation(left, center, right, "\\*", this::multiple);
    }

    private double multiple(double a, double b) {
        return a * b;
    }

}
