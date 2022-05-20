package com.chillycheesy.modulo.commands.operator.natif;

import com.chillycheesy.modulo.commands.CommandFlow;
import com.chillycheesy.modulo.commands.operator.NumberOperator;
import com.chillycheesy.modulo.commands.operator.OperatorListener;
import com.chillycheesy.modulo.commands.operator.builder.Operator;
import com.chillycheesy.modulo.commands.operator.builder.OperatorFindByRegex;
import com.chillycheesy.modulo.modules.Module;
import com.chillycheesy.modulo.utils.Priority;

/**
 * Operator that raise the left parameter to the power of the right parameter.
 * This operator only work with number before and after the symbol "^"
 * Exemple:
 * <ul>
 *     <li>5 ^ 2 = 25</li>
 *     <li>6 ^ -2 = -36</li>
 * </ul>
 */
@Operator(Priority.UNCOMMON)
@OperatorFindByRegex("\\^")
public class PowerOperator extends NumberOperator implements OperatorListener {

    @Override
    public CommandFlow onOperate(Module caller, CommandFlow left, CommandFlow center, CommandFlow right) {
        return super.applyOperation(left, center, right, "\\^", this::pow);
    }

    private double pow(double a, double b) {
        return Math.pow(a, b);
    }
}